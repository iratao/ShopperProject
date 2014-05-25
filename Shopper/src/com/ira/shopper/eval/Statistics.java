
package com.ira.shopper.eval;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.ira.shopper.provider.ShopperContract.Items;
import com.ira.shopper.provider.ShopperContract.Shops;
import com.ira.shopper.provider.ShopperContract.Stats;
import com.ira.shopper.utils.Utils;

/**
 * Stores data about the current task.
 */
public class Statistics {

    private static Statistics _instance;

    private long mStartTime;
    private int mCycleCount;
    private int mCyclePositiveCount;
    private String mUserName;
    private boolean mIsDiversity;

    private boolean mIsStarted;
    
    private boolean mIsContext;
    private int itemId;
    private int contextId;
    
    public static final String FILENAME = "context_case.csv";
    public static final String TAG = "Statistics";

    private Statistics() {

    }

    public synchronized static Statistics get() {
        if (_instance == null) {
            _instance = new Statistics();
        }
        return _instance;
    }

    /**
     * Saves the current time, user name and task type until
     * {@link #finishTask(Context)} is called.
     */
    public synchronized void startTask(String username, boolean isDiversity, boolean isContext) {
        mIsStarted = true;
        mUserName = username;
        mIsDiversity = isDiversity;
        mStartTime = System.currentTimeMillis();
        mCycleCount = 0;
        mCyclePositiveCount = 0;
        
        mIsContext = isContext;
        itemId = -1;
        contextId = -1;
    }
    
    public synchronized void setSelectedItemId(int id){
    	this.itemId = id;
    }
    
    public synchronized void setContextId(int id){
    	this.contextId = id;
    }

    /**
     * Increases the recommendation cycle count by 1. Also the positive cycle
     * count if isPositive is true.
     */
    public synchronized void incrementCycleCount(boolean isPositive) {
        mCycleCount++;
        if (isPositive) {
            mCyclePositiveCount++;
        }
    }
    

    /**
     * Stops the task and writes all data to the database.
     * 
     * @return The {@link Uri} pointing to the new data set or {@code null} if
     *         {@link #startTask(String, boolean)} was not called before.
     */
    public synchronized Uri finishTask(Context context, Tracker tracker) {
        if (!mIsStarted) {
            return null;
        }

        mIsStarted = false;
        long duration = System.currentTimeMillis() - mStartTime;

        // Write to database
        ContentValues statValues = new ContentValues();
        statValues.put(Stats.USERNAME, mUserName);
        statValues.put(Stats.TASK_TYPE, mIsContext ? "context" : "div");
        statValues.put(Stats.CYCLE_COUNT, mCycleCount + "(" + mCyclePositiveCount + "+)");
        statValues.put(Stats.DURATION, duration);
        final Uri inserted = context.getContentResolver().insert(Stats.CONTENT_URI, statValues);
        
//        EasyTracker.getTracker().sendEvent("Results", "Type",
//                mIsDiversity ? "Diversity" : "Similarity",
//                (long) 0);
//        // 1 - use context, 0 - not use context
//        EasyTracker.getTracker().sendEvent("Results", "Type","IsUseContext", 
//        		mIsContext? ((long)1):((long)0)); 
//        EasyTracker.getTracker().sendEvent("Results", "Value", "Cycles", (long) mCycleCount);
//        EasyTracker.getTracker().sendEvent("Results", "Value", "Cycles (positive)",
//                (long) mCyclePositiveCount);
//        EasyTracker.getTracker().sendEvent("Results", "Value", "Duration", duration);
//        
//        EasyTracker.getTracker().sendEvent("Results", "Value", "ContextIndex", (long)contextId);
//        EasyTracker.getTracker().sendEvent("Results", "Value", "ItemId", (long)itemId);
        
        tracker.send(new HitBuilders.EventBuilder().setCategory("Results").setAction("Type")
        		.setLabel(mIsDiversity ? "Diversity" : "Similarity").setValue((long) 0)
        		.build());
        tracker.send(new HitBuilders.EventBuilder().setCategory("Results").setAction("Type")
        		.setLabel("IsUseContext").setValue(mIsContext? ((long)1):((long)0))
        		.build());
        tracker.send(new HitBuilders.EventBuilder().setCategory("Results").setAction("Value")
        		.setLabel("Cycles").setValue((long) mCycleCount)
        		.build());
        tracker.send(new HitBuilders.EventBuilder().setCategory("Results").setAction("Value")
        		.setLabel("Cycles (positive)").setValue((long) mCyclePositiveCount)
        		.build());
        tracker.send(new HitBuilders.EventBuilder().setCategory("Results").setAction("Value")
        		.setLabel("Duration").setValue(duration)
        		.build());
        tracker.send(new HitBuilders.EventBuilder().setCategory("Results").setAction("Value")
        		.setLabel("ContextIndex").setValue((long)contextId)
        		.build());
        tracker.send(new HitBuilders.EventBuilder().setCategory("Results").setAction("Value")
        		.setLabel("ItemId").setValue((long)itemId)
        		.build());
        
        if(Utils.isExternalStorageWritable()){
        	try {
        		String separator = System.getProperty("line.separator");
        		boolean isNewFile = false;
        		File file = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS), FILENAME);
        		if(!file.exists()){
        			file.createNewFile();
        			isNewFile = true;
        		}
        		FileOutputStream fos = new FileOutputStream(file, true);
        		OutputStreamWriter out = new OutputStreamWriter(fos);
        		BufferedWriter bwriter = new BufferedWriter(out);
        		if(isNewFile){
        			bwriter.write(("is_context,item_id,context_id"));
        			bwriter.write(separator);
        		}
        		bwriter.write((mIsContext + "," + itemId + "," + contextId));
        		bwriter.write(separator);
        		bwriter.close();
        		out.close();
        		fos.close();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
        
        
        return inserted;
    }

    public synchronized String getUserName() {
        return mUserName;
    }
    
    

}
