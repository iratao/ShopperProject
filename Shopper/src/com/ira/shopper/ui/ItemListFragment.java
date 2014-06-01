package com.ira.shopper.ui;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.ira.shopper.R;
import com.ira.shopper.adapters.ItemAdapter;
import com.ira.shopper.adapters.ItemAdapter.OnItemCritiqueListener;
import com.ira.shopper.adapters.ItemAdapter.OnItemDisplayListener;
import com.ira.shopper.loaders.ItemLoader;
import com.ira.shopper.ui.MainActivity.ContextUpdateEvent;
import com.ira.shopper.ui.MainActivity.LocationUpdateEvent;
import com.ira.shopper.utils.Maps;
import com.uwetrottmann.shopr.algorithm.AdaptiveSelection;
import com.uwetrottmann.shopr.algorithm.Query;
import com.uwetrottmann.shopr.algorithm.model.Item;

import de.greenrobot.event.EventBus;

public class ItemListFragment extends Fragment implements LoaderCallbacks<List<Item>>,
OnItemCritiqueListener, OnItemDisplayListener{
	public static final String TAG = "Item List";
	
	// I = 9, T = 20
    private static final int LOADER_ID = 920;
    private static final int REQUEST_CODE = 12;
    private TextView mTextViewReason;
    private GridView mGridView;
    private ItemAdapter mAdapter;
    
    private boolean mIsInitialized;
    
	public static ItemListFragment newInstance() {
        return new ItemListFragment();
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item_list, container, false);

        mTextViewReason = (TextView) v.findViewById(R.id.textViewItemListReason);
        mGridView = (GridView) v.findViewById(R.id.gridViewItemList);
        View emtpyView = v.findViewById(R.id.textViewItemListEmpty);
        mGridView.setEmptyView(emtpyView);

        return v;
    }
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new ItemAdapter(getActivity(), this, this);

        mGridView.setAdapter(mAdapter);

        /*
         * Comment this part out and move it to onResume so that the 
         * loader will not be called twice.
         */
//        Bundle args = new Bundle();
//        args.putBoolean("isinit", true);
//        getLoaderManager().initLoader(LOADER_ID, args, this);

        setHasOptionsMenu(true);
    }
		
	@Override
	public void onResume() {
		super.onResume();
		Bundle args = new Bundle();
        args.putBoolean("isinit", true);
        getLoaderManager().initLoader(LOADER_ID, args, this);
	}

	@Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().registerSticky(this, LocationUpdateEvent.class);
        EventBus.getDefault().register(this, ContextUpdateEvent.class);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
	
	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.item_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_restart:
                onInitializeItems();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

	@Override
	public void onItemDisplay(Item item) {
		// display details
        Intent intent = new Intent(getActivity(), ItemDetailsActivity.class);
        intent.putExtra(ItemDetailsActivity.InitBundle.ITEM_ID, item.id());
        startActivity(intent);
		
	}

	@Override
	public void onItemCritique(Item item, boolean isLike) {
		startActivityForResult(new Intent(getActivity(), CritiqueActivity.class).putExtra(
                CritiqueActivity.InitBundle.IS_POSITIVE_CRITIQUE, isLike)
                .putExtra(CritiqueActivity.InitBundle.ITEM_ID, item.id()), REQUEST_CODE);
		
	}

	@Override
	public Loader<List<Item>> onCreateLoader(int loaderId, Bundle args) {
		boolean isInit = false;
        if (args != null) {
            isInit = args.getBoolean("isinit");
        }
        LatLng location = MainActivity.getLastLocation();
        Log.d(TAG, "onCreateLoader " + location.toString());
        return new ItemLoader(getActivity(), location, isInit);
	}

	@Override
	public void onLoadFinished(Loader<List<Item>> loader, List<Item> data) {
		mAdapter.clear();
        for (Item item : data) {
        	mAdapter.add(item);
        }
        onUpdateReason();
        onUpdateShops(data);
        Log.d(TAG, "onLoadFinished");
		
	}

	@Override
	public void onLoaderReset(Loader<List<Item>> arg0) {
		mAdapter.clear();
		
	}
	
	public class ShopUpdateEvent {
        /**
         * Holds a list of shop ids and how many recommendations are shown for
         * each shop.
         */
        Map<Integer, Integer> shopMap;
    }

	
	/**
     * Post {@link ShopUpdateEvent} based on current list of recommendations.
     * 
     * @param data
     */
    private void onUpdateShops(List<Item> data) {
        ShopUpdateEvent event = new ShopUpdateEvent();

        // get shops and number of items per shop
        event.shopMap = Maps.newHashMap();
        for (Item item : data) {
            int shopId = item.shopId();
            int count = 1;

            if (event.shopMap.containsKey(shopId)) {
                count = event.shopMap.get(shopId);
                count++;
            }

            event.shopMap.put(shopId, count);
        }

        EventBus.getDefault().postSticky(event);
    }
	
	private void onUpdateReason() {
        Query currentQuery = AdaptiveSelection.get().getCurrentQuery();
        // Display current reason as explanatory text
        String reasonString = currentQuery.attributes().getReasonString();
        if (TextUtils.isEmpty(reasonString)) {
            mTextViewReason.setText(R.string.reason_empty);
        } else {
            mTextViewReason.setText(reasonString);
        }
    }
	
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE ) {
            Log.d(TAG, "Received recommendation update, requerying");
            getLoaderManager().restartLoader(LOADER_ID, null, this);
        }
    }
	
	public void onEvent(LocationUpdateEvent event) {
        if (!mIsInitialized) {
            Log.d(TAG, "Received location update, requerying");
            mIsInitialized = true;
            onInitializeItems();
        }

    }
	
	public void onEvent(ContextUpdateEvent event){
		Log.d(TAG, "Received context update, requering");
		onInitializeItems();
	}

    private void onInitializeItems() {
        Bundle args = new Bundle();
        args.putBoolean("isinit", true);
        getLoaderManager().restartLoader(LOADER_ID, args, this);
    }
}
