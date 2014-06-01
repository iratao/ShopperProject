package com.ira.shopper.ui;


import java.util.Locale;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.model.LatLng;
import com.ira.shopper.R;
import com.ira.shopper.eval.TestSetupActivity;
import com.ira.shopper.importer.ImporterActivity;
import com.ira.shopper.settings.AppSettings;

import de.greenrobot.event.EventBus;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener,
	GooglePlayServicesClient.ConnectionCallbacks,
	GooglePlayServicesClient.OnConnectionFailedListener{
	
	private static final String TAG = "Shopper";
	
	/*
     * Define a request code to send to Google Play services. This code is
     * returned in Activity.onActivityResult.
     */
    public static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
     * will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;
    
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    
    private LocationClient mLocationClient;
    private static LatLng mLastLocation;
    public static boolean CONTEXT_CHANGED = false;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		// Check if Google Play services is installed
//        if (!servicesConnected()) {
//            return;
//        }

        /*
         * Create a new location client, using the enclosing class to handle
         * callbacks.
         */
        mLocationClient = new LocationClient(this, this, this);

		
		// Create the adapter that will return a fragment for each of the three
        // primary sections of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
       
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        
       // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
        
//        initContextDatabase();

	}
	
	
	
	@Override
	protected void onStop() {
		if (!AppSettings.isUsingFakeLocation(this)) {
            mLocationClient.disconnect();
        }
		super.onStop();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.e(TAG, "isUsingFakeLocation? " + AppSettings.isUsingFakeLocation(this));
		// use fake location (Marienplatz, Munich)
        mLastLocation = new LatLng(48.137314, 11.575253);
        // send out location update event immediately
        EventBus.getDefault().postSticky(new LocationUpdateEvent());
        if(CONTEXT_CHANGED){
        	 EventBus.getDefault().post(new ContextUpdateEvent());
        	 CONTEXT_CHANGED = false;
        }
//		if (AppSettings.isUsingFakeLocation(this)) {
//            // use fake location (Marienplatz, Munich)
//            mLastLocation = new LatLng(48.137314, 11.575253);
//            // send out location update event immediately
//            EventBus.getDefault().postSticky(new LocationUpdateEvent());
//        } else {
//            mLocationClient.connect();
//        }
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
		if(AppSettings.isUsingContext(getBaseContext())){
			getMenuInflater().inflate(R.menu.main, menu);
		}else{
			getMenuInflater().inflate(R.menu.main_no_context, menu);
		}
        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_restart_test:
                startActivity(new Intent(this, TestSetupActivity.class));
                // clean this activity up
                finish();
                return true;
            case R.id.action_import:
                startActivity(new Intent(this, ImporterActivity.class));
                return true;
            case R.id.action_context_settings:
            	startActivity(new Intent(this, ContextSettingsActivity.class));
            	CONTEXT_CHANGED = true;
            	return true;
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    


	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		/*
         * Google Play services can resolve some errors it detects. If the error
         * has a resolution, try sending an Intent to start a Google Play
         * services activity that can resolve error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        this,
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the user with
             * the error.
             */
            showErrorDialog(connectionResult.getErrorCode());
        }
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		getLocation();
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
	
	public void getLocation() {
        // If Google Play Services is available
        if (servicesConnected()) {
            // Get the current location
            Location lastLocation = mLocationClient.getLastLocation();
            mLastLocation = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            EventBus.getDefault().postSticky(new LocationUpdateEvent());
        }
    }
	
	/**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 1) {
                return ShopMapFragment.newInstance();
            } else {
                return ItemListFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_list).toUpperCase(l);
                case 1:
                    return getString(R.string.title_map).toUpperCase(l);
            }
            return null;
        }
    }
    
    /**
     * Verify that Google Play services is available before making a request.
     * 
     * @return true if Google Play services is available, otherwise false
     */
    private boolean servicesConnected() {

        // Check that Google Play services is available
        int resultCode =
                GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            Log.d(TAG, "Google Play services available");

            // Continue
            return true;
            // Google Play services was not available for some reason
        } else {
            // Display an error dialog
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, 0);
            if (dialog != null) {
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                errorFragment.setDialog(dialog);
                errorFragment.show(getSupportFragmentManager(), TAG);
            }
            return false;
        }
    }

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction) {
		// TODO Auto-generated method stub
		
	}
	
	public static LatLng getLastLocation() {
        return mLastLocation;
    }

	
	/**
     * Show a dialog returned by Google Play services for the connection error
     * code
     * 
     * @param errorCode An error code returned from onConnectionFailed
     */
    private void showErrorDialog(int errorCode) {

        // Get the error dialog from Google Play services
        Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
                errorCode,
                this,
                CONNECTION_FAILURE_RESOLUTION_REQUEST);

        // If Google Play services can provide an error dialog
        if (errorDialog != null) {

            // Create a new DialogFragment in which to show the error dialog
            ErrorDialogFragment errorFragment = new ErrorDialogFragment();

            // Set the dialog in the DialogFragment
            errorFragment.setDialog(errorDialog);

            // Show the error dialog in the DialogFragment
            errorFragment.show(getSupportFragmentManager(), TAG);
        }
    }
	
	/**
     * Define a DialogFragment to display the error dialog generated in
     * showErrorDialog.
     */
    public static class ErrorDialogFragment extends DialogFragment {

        // Global field to contain the error dialog
        private Dialog mDialog;

        /**
         * Default constructor. Sets the dialog field to null
         */
        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }

        /**
         * Set the dialog to display
         * 
         * @param dialog An error dialog
         */
        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }

        /*
         * This method must return a Dialog to the DialogFragment.
         */
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }
    
    public class LocationUpdateEvent {
    }
    
    public class ContextUpdateEvent {
    }
    
    

}
