
package com.ira.shopper.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ira.shopper.provider.ShopperContract.ContextCases;
import com.ira.shopper.provider.ShopperContract.ContextFactor;
import com.ira.shopper.provider.ShopperContract.ContextValue;
import com.ira.shopper.provider.ShopperContract.Items;
import com.ira.shopper.provider.ShopperContract.Shops;
import com.ira.shopper.provider.ShopperContract.Stats;

public class ShopperDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "shopper.db";

    public static final int DBVER_INITIAL = 1;

    public static final int DBVER_ITEM_COLUMNS = 2;

    public static final int DBVER_STATS = 3;

    public static final int DATABASE_VERSION = DBVER_STATS;

    public interface Tables {
        String ITEMS = "items";

        String SHOPS = "shops";

        String STATS = "stats";
        
        String CONTEXT_FACTOR = "context_factor";
        
        String CONTEXT_VALUE = "context_value";
        
        String CONTEXT_CASES = "context_cases";
    }

    public interface References {
        String SHOP_ID = "REFERENCES " + Tables.SHOPS + "(" + Shops._ID + ")";
        String ITEM_ID = "REFERENCES " + Tables.ITEMS + "(" + Items._ID + ")";
        String FACTOR_ID = "REFERENCES " + Tables.CONTEXT_FACTOR 
        		+ "(" + ContextFactor._ID + ")";
        String VALUE_ID = "REFERENCES " + Tables.CONTEXT_VALUE 
        		+ "(" + ContextValue._ID + ")";
    }
    
    private static final String CREATE_CONTEXT_CASES_TABLE = "CREATE TABLE "
    		+ Tables.CONTEXT_CASES + " ("
    		+ ContextCases._ID + " INTEGER PRIMARY KEY,"
    		+ ContextCases.REF_ITEM_ID + " INTEGER " + References.ITEM_ID + ","
    		+ ContextCases.CONTEXT_ID + " INTEGER"
    		+ ");";

    private static final String CREATE_CONTEXT_FACTOR_TABLE = "CREATE TABLE "
    		+ Tables.CONTEXT_FACTOR + " ("
    		+ ContextFactor._ID + " INTEGER PRIMARY KEY,"
    		+ ContextFactor.FACTOR + " TEXT"
    		+ ");";
        
    private static final String CREATE_CONTEXT_VALUE_TABLE = "CREATE TABLE "
    		+ Tables.CONTEXT_VALUE + " ("
    		+ ContextValue._ID + " INTEGER PRIMARY KEY,"
    		+ ContextValue.REF_FACTOR_ID + " INTEGER " + References.FACTOR_ID + ","
    		+ ContextValue.VALUE + " TEXT"
    		+ ");";

    private static final String CREATE_ITEMS_TABLE = "CREATE TABLE "
            + Tables.ITEMS + " ("

            + Items._ID + " INTEGER PRIMARY KEY,"

            + Shops.REF_SHOP_ID + " INTEGER " + References.SHOP_ID + ","

            + Items.BRAND + " TEXT,"

            + Items.CLOTHING_TYPE + " TEXT,"

            + Items.COLOR + " TEXT,"

            + Items.PRICE + " REAL,"

            + Items.SEX + " TEXT,"

            + Items.IMAGE_URL + " TEXT"

            + ");";

    private static final String CREATE_SHOPS_TABLE = "CREATE TABLE "
            + Tables.SHOPS + " ("

            + Shops._ID + " INTEGER PRIMARY KEY,"

            + Shops.NAME + " TEXT NOT NULL,"

            + Shops.OPENING_HOURS + " TEXT,"

            + Shops.LAT + " REAL,"

            + Shops.LONG + " REAL"

            + ");";

    private static final String CREATE_STATS_TABLE = "CREATE TABLE "
            + Tables.STATS + " ("

            + Stats._ID + " INTEGER PRIMARY KEY,"

            + Stats.USERNAME + " TEXT,"

            + Stats.DURATION + " INTEGER,"

            + Stats.CYCLE_COUNT + " INTEGER,"

            + Stats.TASK_TYPE + " TEXT"

            + ");";

    private static final String TAG = "ShopperDatabase";

    public ShopperDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    	db.execSQL(CREATE_CONTEXT_FACTOR_TABLE);
    	db.execSQL(CREATE_CONTEXT_VALUE_TABLE);
    	
        db.execSQL(CREATE_SHOPS_TABLE);

        // items refs shop ids, so create shops table first
        db.execSQL(CREATE_ITEMS_TABLE);

        db.execSQL(CREATE_STATS_TABLE);
        
        db.execSQL(CREATE_CONTEXT_CASES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // always start from scratch
        onResetDatabase(db);
    }

    /**
     * Drops all tables and creates an empty database.
     */
    private void onResetDatabase(SQLiteDatabase db) {
        Log.w(TAG, "Database has incompatible version, starting from scratch");
        
        db.execSQL("DROP TABLE IF EXISTS " + Tables.CONTEXT_CASES);

        db.execSQL("DROP TABLE IF EXISTS " + Tables.ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.SHOPS);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.STATS);
        
        db.execSQL("DROP TABLE IF EXISTS " + Tables.CONTEXT_VALUE);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.CONTEXT_FACTOR);

        onCreate(db);
    }

}
