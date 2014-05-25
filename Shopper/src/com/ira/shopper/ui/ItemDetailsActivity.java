package com.ira.shopper.ui;

import java.util.List;
import java.util.Locale;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;

import com.google.android.gms.maps.model.LatLng;
import com.ira.shopper.R;
import com.ira.shopper.eval.Statistics;
import com.ira.shopper.model.Shop;
import com.ira.shopper.provider.ShopperContract.Shops;
import com.ira.shopper.ui.MainActivity.LocationUpdateEvent;
import com.uwetrottmann.shopr.algorithm.AdaptiveSelection;
import com.uwetrottmann.shopr.algorithm.model.Item;

import de.greenrobot.event.EventBus;

public class ItemDetailsActivity extends ActionBarActivity implements ActionBar.TabListener{
	
	private ViewPager viewPager;
	private SectionsPagerAdapter mAdapter;
	private ActionBar actionBar;
	
	private Item mItem;
	
	private static final String TAG = "Item Details";
	
	public interface InitBundle {
		String ITEM_ID = "item_id";
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_details);
		
		// Initilization
		viewPager = (ViewPager) findViewById(R.id.item_details_pager);
		actionBar = getSupportActionBar();
		mAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
		
		viewPager.setAdapter(mAdapter);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });
		
		Bundle extras = this.getIntent().getExtras();
	      if (extras == null) {
	          this.finish();
	          return;
	      }

	      int itemId = extras.getInt(InitBundle.ITEM_ID);
	      Statistics.get().setSelectedItemId(itemId);
	      List<Item> currentCaseBase = AdaptiveSelection.get().getCurrentRecommendations();
	      for (Item item : currentCaseBase) {
	          if (item.id() == itemId) {
	              mItem = item;
	              break;
	          }
	      }

	      if (mItem == null) {
	    	  this.finish();
	    	  return;
	      }
	      
	      EventBus.getDefault().postSticky(new ItemUpdateEvent());
		
		// For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
	}
	
	public Item getCurrentItem(){
		return this.mItem;
	}
	
	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
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
                return ItemMapFragment.newInstance();
            } else {
                return ItemDetailFragment.newInstance();
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
                    return getString(R.string.title_item_detail).toUpperCase(l);
                case 1:
                    return getString(R.string.title_item_map).toUpperCase(l);
            }
            return null;
        }
    }
    
    public Shop getShop(int shopid){
		Shop shop = new Shop();
		final Cursor query = this.getContentResolver().query(Shops.CONTENT_URI,
                new String[] {
                        Shops._ID, Shops.NAME, Shops.LAT, Shops.LONG
                }, Shops._ID + "=" + shopid, null,
                null);
		if (query != null) {
			 if (query.moveToNext()) {
				 shop.id(query.getInt(0));
	             shop.name(query.getString(1));
	             shop.location(new LatLng(query.getDouble(2), query.getDouble(3)));
			 }
		}
		return shop;
	}
    
    public class ItemUpdateEvent {
    }
	
}

//public class ItemDetailsActivity extends Activity{
//	public interface InitBundle {
//        String ITEM_ID = "item_id";
//    }
//
//    private Item mItem;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_item_details);
//
//        Bundle extras = getIntent().getExtras();
//        if (extras == null) {
//            finish();
//            return;
//        }
//
//        int itemId = extras.getInt(InitBundle.ITEM_ID);
//        Statistics.get().setSelectedItemId(itemId);
//        List<Item> currentCaseBase = AdaptiveSelection.get().getCurrentRecommendations();
//        for (Item item : currentCaseBase) {
//            if (item.id() == itemId) {
//                mItem = item;
//                break;
//            }
//        }
//
//        if (mItem == null) {
//            finish();
//            return;
//        }
//
//        setupViews();
//    }
//    
//    private void setupViews() {
//        ImageView image = (ImageView) findViewById(R.id.imageViewItemDetails);
//        // load picture
//        Picasso.with(this)
//                .load(mItem.image())
//                .placeholder(null)
//                .error(R.drawable.ic_action_tshirt)
//                .resizeDimen(R.dimen.default_image_width, R.dimen.default_image_height)
//                .centerCrop()
//                .into(image);
//
//        findViewById(R.id.buttonItemDetailsFinish).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onFinishTask();
//            }
//        });
//
//        // title
//        TextView itemTitle = (TextView) findViewById(R.id.textViewItemDetailsTitle);
//        itemTitle.setText(getString(R.string.choice_confirmation, mItem.name()));
//
//        // item attributes
//        StringBuilder description = new StringBuilder();
//        description
//                .append(ValueConverter.getLocalizedStringForValue(this, mItem.attributes()
//                        .getAttributeById(ClothingType.ID).currentValue().descriptor()))
//                .append("\n")
//                .append(ValueConverter.getLocalizedStringForValue(this, mItem.attributes()
//                        .getAttributeById(Sex.ID).currentValue().descriptor()))
//                .append("\n")
//                .append(ValueConverter.getLocalizedStringForValue(this, mItem.attributes()
//                        .getAttributeById(Color.ID).currentValue().descriptor()))
//                .append("\n")
//                .append(NumberFormat.getCurrencyInstance(Locale.GERMANY).format(
//                        mItem.price().doubleValue()));
//        TextView itemDescription = (TextView) findViewById(R.id.textViewItemDetailsAttributes);
//        itemDescription.setText(description);
//        
//        if(AppSettings.isUsingContext(getBaseContext())){
//        	TextView recommendReason = (TextView) findViewById(R.id.textViewRecommendReason);
//            recommendReason.setText(mItem.getContextAwareReason());
//        }else{
//        	TextView recommendReason = (TextView) findViewById(R.id.textViewRecommendReason);
//            recommendReason.setText("");
//        }
//        
//    }
//    
////    @Override
////    public void onStart() {
////        super.onStart();
////        EasyTracker.getInstance().activityStart(this);
////    }
////
////    @Override
////    public void onStop() {
////        super.onStop();
////        EasyTracker.getInstance().activityStop(this);
////    }
//
//    protected void onFinishTask() {
//        // finish task, store stats to database
//    	Tracker tracker = ((ShopperApp)this.getApplication()).getTracker(
//                TrackerName.APP_TRACKER);
//    	
//    	// Set screen name.
//        // Where path is a String representing the screen name.
//    	tracker.setScreenName("ItemDetailsActivity");
//
//        // Send a screen view.
//    	tracker.send(new HitBuilders.AppViewBuilder().build());
//        
//        Uri statUri = Statistics.get().finishTask(this,tracker);
//        if (statUri == null) {
//            Toast.makeText(this, "Task was not started.", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        // display results
//        Intent intent = new Intent(this, ResultsActivity.class);
//        intent.putExtra(ResultsActivity.InitBundle.STATS_ID,
//                Integer.valueOf(Stats.getStatId(statUri)));
//        intent.putExtra(ResultsActivity.InitBundle.ITEM_ID, mItem.id());
//        startActivity(intent);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.item_details, menu);
//        return true;
//    }
//
//}
