package com.ira.shopper.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ira.shopper.R;
import com.ira.shopper.model.Shop;
import com.ira.shopper.provider.ShopperContract.Shops;
import com.ira.shopper.settings.AppSettings;
import com.ira.shopper.ui.ItemDetailsActivity.ItemUpdateEvent;
import com.uwetrottmann.shopr.algorithm.model.Item;

import de.greenrobot.event.EventBus;

public class ItemMapFragment extends SupportMapFragment{
	public static ItemMapFragment newInstance() {
        return new ItemMapFragment();
    }
	
	private Marker mItemMarker;
	private Marker myLocationMarker;
	private boolean mIsInitialized;
	
	public static final String TAG = "Items Map";
	private static final int ZOOM_LEVEL_INITIAL = 14;
	private static final int RADIUS_METERS = 2000;
	
	private Item mItem;
	private Shop mShop;
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // enable my location feature
        getMap().setMyLocationEnabled(!AppSettings.isUsingFakeLocation(getActivity()));
        mIsInitialized = false;
        
        mItem = ((ItemDetailsActivity)this.getActivity()).getCurrentItem();
        mShop = ((ItemDetailsActivity)this.getActivity()).getShop(mItem.shopId());
    }
	
	@Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault()
                .registerSticky(this, ItemUpdateEvent.class);
    }
	
	@Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
	
	private void onInitializeMap() {
		if (!mIsInitialized) {
            Log.d(TAG, "Initializing map.");

            LatLng userPosition = MainActivity.getLastLocation();
            if (userPosition == null) {
            	Log.d(TAG, "User position is null.");
                return;
            }

            
            if(myLocationMarker != null)
            	myLocationMarker.remove();
            myLocationMarker = getMap().addMarker(new MarkerOptions()
                            .position(userPosition)
                            .title(getString(R.string.my_location))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            
            // draw a circle around it
            getMap().addCircle(new CircleOptions()
                    .center(userPosition)
                    .radius(RADIUS_METERS)
                    .strokeColor(getResources().getColor(R.color.lilac))
                    .strokeWidth(4)
                    .fillColor(getResources().getColor(R.color.lilac_transparent)));
            
           // move camera to current item
            getMap().moveCamera(
                    CameraUpdateFactory.newLatLngZoom(mShop.location(), ZOOM_LEVEL_INITIAL));
            
            float color;
            color = BitmapDescriptorFactory.HUE_VIOLET;

            if(mItemMarker != null)
            	mItemMarker.remove();
            // place marker
            mItemMarker = getMap().addMarker(
                    new MarkerOptions()
                            .position(mShop.location())
                            .title(mShop.name())
                            .snippet(mItem.name())
                            .icon(BitmapDescriptorFactory.defaultMarker(color)));
            
            
            
            mIsInitialized = true;
        }
	}
	
	
	
	public void onEvent(ItemUpdateEvent event) {
        onInitializeMap();
    }

}
