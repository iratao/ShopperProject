package com.ira.shopper.loaders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.ira.shopper.eval.ContextInitActivity;
import com.ira.shopper.model.Shop;
import com.ira.shopper.provider.ShopperContract.ContextCases;
import com.ira.shopper.provider.ShopperContract.Items;
import com.ira.shopper.provider.ShopperContract.Shops;
import com.ira.shopper.settings.AppSettings;
import com.ira.shopper.settings.ContextSettings;
import com.ira.shopper.ui.MainActivity;
import com.ira.shopper.utils.Lists;
import com.ira.shopper.utils.ShoprLocalizer;
import com.ira.shopper.utils.ValueConverter;
import com.uwetrottmann.shopr.algorithm.AdaptiveSelection;
import com.uwetrottmann.shopr.algorithm.model.Attributes;
import com.uwetrottmann.shopr.algorithm.model.ClothingType;
import com.uwetrottmann.shopr.algorithm.model.Color;
import com.uwetrottmann.shopr.algorithm.model.Item;
import com.uwetrottmann.shopr.algorithm.model.Price;
import com.uwetrottmann.shopr.algorithm.model.Sex;
import com.uwetrottmann.shopr.algorithm.shopper.model.Budget;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextCase;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors;

public class ItemLoader extends GenericSimpleLoader<List<Item>>{
	private static final String TAG = "ItemLoader";
    private LatLng mLocation;
    private boolean mIsInit;
    
    // If distance is enabled, get items within NEAR_BY_BOUND meters.
    public static final int NEAR_BY_BOUND = 2000;
    
    public ItemLoader(Context context, LatLng location, boolean isInit) {
        super(context);
        mLocation = location;
        mIsInit = isInit;
    }

    @Override
    public List<Item> loadInBackground() {
        if (mLocation == null) {
        	Log.e(TAG, "mLocation is null.");
            return new ArrayList<Item>();
        }

        AdaptiveSelection manager = AdaptiveSelection.get();

        // get initial case base
        if (mIsInit) {
            manager.setLocalizationModule(new ShoprLocalizer(getContext()));

            Log.d(TAG, "Initializing case base.");
            List<Item> caseBase = getInitialCaseBase();
            manager.setInitialCaseBase(caseBase, AppSettings.isUsingDiversity(getContext()), 
            		AppSettings.isUsingContext(getContext()));
            
            Log.d(TAG, "Is using context: " + AppSettings.isUsingContext(getContext()));
            if(AppSettings.isUsingContext(getContext())){
        		Log.d(TAG, "Initializing context case base.");
        		manager.setInitialContextCaseBase(getInitialContextCaseBase());
        	}

            int maxRecommendations = AppSettings.getMaxRecommendations(getContext());
            Log.d(TAG, "maxRecommendations: " + maxRecommendations);
            AdaptiveSelection.get().setMaxRecommendations(maxRecommendations);
        }
        
        Log.d(TAG, "Fetching recommendations.");
        if(AppSettings.isUsingContext(getContext())){
    		manager.setCurrentContext(getCurrentContext());
    		
    	}

        List<Item> recommendations = manager.getRecommendations();

        return recommendations;
    }
    
    private ContextFactors getCurrentContext(){
    	ContextFactors factors = ContextSettings.getCurrentContext(getContext());
    	Log.e(TAG, factors.getAllContextFactorsString());
    	return factors;
    	
//    	Budget budget1 = new Budget();
//		budget1.setCurrentValue(Budget.Value.BUDGET_BUYER);
//		ContextFactors context1 = new ContextFactors();
//		context1.putContextFactor(budget1);
//		return context1;
    }
    
    private List<ContextCase> getInitialContextCaseBase(){
    	List<ContextCase> casebase = new ArrayList<ContextCase>();
		
		Cursor query1 = getContext().getContentResolver().query(
                ContextCases.CONTENT_URI,
                new String[] {
                        ContextCases.REF_ITEM_ID, ContextCases.CONTEXT_ID
                }, null, null, null);
		
		if(query1 != null){
			while(query1.moveToNext()){
				int contextId = query1.getInt(1);
				int itemId = query1.getInt(0);
				
				// Get context factors
				ContextFactors contextFactors = ContextInitActivity.getContextByIndex(contextId);
				
				// Get the item
				Cursor query = getContext().getContentResolver().query(
		                Items.CONTENT_URI,
		                new String[] {
		                        Items._ID, Items.CLOTHING_TYPE, Items.BRAND, Items.PRICE, Items.IMAGE_URL,
		                        Items.COLOR, Items.SEX, Shops.REF_SHOP_ID
		                }, Items._ID + "=" + itemId, null, null);
				Item item = new Item();
				if (query != null) {
					while (query.moveToNext()) {

		                item.id(query.getInt(0));
		                item.image(query.getString(4));
		                item.shopId(query.getInt(7));
		                // name
		                ClothingType type = new ClothingType(query.getString(1));
		                String brand = query.getString(2);
		                item.name(ValueConverter.getLocalizedStringForValue(getContext(), type
		                        .currentValue().descriptor())
		                        + " " + brand);
		                // price
		                BigDecimal price = new BigDecimal(query.getDouble(3));
		                item.price(price);
		                // critiquable attributes
		                item.attributes(new Attributes()
		                        .putAttribute(type)
		                        .putAttribute(new Color(query.getString(5)))
		                        .putAttribute(new Price(price))
		                        .putAttribute(new Sex(query.getString(6))));
		                
		            }
				}
				
				ContextCase contextCase = new ContextCase();
				contextCase.setContextFactors(contextFactors);
				contextCase.setItem(item);
				casebase.add(contextCase);
			}
		}
		
		
		return casebase;
    }
    
    private List<Item> getInitialCaseBase() {
        List<Item> caseBase = Lists.newArrayList();
        LatLng userPosition = MainActivity.getLastLocation();
        Cursor query = getContext().getContentResolver().query(
                Items.CONTENT_URI,
                new String[] {
                        Items._ID, Items.CLOTHING_TYPE, Items.BRAND, Items.PRICE, Items.IMAGE_URL,
                        Items.COLOR, Items.SEX, Shops.REF_SHOP_ID
                }, null, null, null);
        
        // If the user want to find clothes near them and the system is configured to be context-aware
        if(ContextSettings.useDistance(this.getContext()) && AppSettings.isUsingContext(getContext())){
        	Log.e(TAG, "Use Distance!!!!!");
        	Cursor queryShop = getContext().getContentResolver().query(Shops.CONTENT_URI,
                    new String[] {
                            Shops._ID, Shops.NAME, Shops.LAT, Shops.LONG
                    }, null, null,null);
        	
        	String where = "";
        	
        	if (queryShop != null && queryShop.getCount() > 0) {
        		where = Shops.REF_SHOP_ID + " IN (";
                while (queryShop.moveToNext()) {
                    Shop shop = new Shop();
                    shop.id(queryShop.getInt(0));
                    shop.name(queryShop.getString(1));
                    shop.location(new LatLng(queryShop.getDouble(2), queryShop.getDouble(3)));
                    if(shop.getDistance(userPosition) < NEAR_BY_BOUND)
                    	where += shop.id() + ",";
                }
                where = where.substring(0, where.length() - 1);
                where += ")";

                queryShop.close();
                query = getContext().getContentResolver().query(
                        Items.CONTENT_URI,
                        new String[] {
                                Items._ID, Items.CLOTHING_TYPE, Items.BRAND, Items.PRICE, Items.IMAGE_URL,
                                Items.COLOR, Items.SEX, Shops.REF_SHOP_ID
                        }, where, null, null);
                
                Log.d(TAG, where);
            }
        }

        

        if (query != null) {
            while (query.moveToNext()) {
                Item item = new Item();

                item.id(query.getInt(0));
                item.image(query.getString(4));
                item.shopId(query.getInt(7));
                // name
                ClothingType type = new ClothingType(query.getString(1));
                String brand = query.getString(2);
                item.name(ValueConverter.getLocalizedStringForValue(getContext(), type
                        .currentValue().descriptor())
                        + " " + brand);
                // price
                BigDecimal price = new BigDecimal(query.getDouble(3));
                item.price(price);
                // critiquable attributes
                item.attributes(new Attributes()
                        .putAttribute(type)
                        .putAttribute(new Color(query.getString(5)))
                        .putAttribute(new Price(price))
                        .putAttribute(new Sex(query.getString(6))));

                caseBase.add(item);
            }
            Log.d(TAG, "caseBase : " + caseBase.size());
            query.close();
        }else{
        	Log.d(TAG, "Query is null :(");
        }

        return caseBase;
    }

}
