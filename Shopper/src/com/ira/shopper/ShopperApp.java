package com.ira.shopper;


import java.util.HashMap;

import android.app.Application;
import android.preference.PreferenceManager;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

public class ShopperApp extends Application{
	public static final String PROPERTY_ID = "UA-50990611-1";
	
	public enum TrackerName {
		APP_TRACKER // Tracker used only in this app.
	}

	HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();
	  
	@Override
	public void onCreate() {
		super.onCreate();
		PreferenceManager.setDefaultValues(this, R.xml.prefs, true);
//		EasyTracker.getInstance().setContext(this);
	}
	
	public synchronized Tracker getTracker(TrackerName trackerId) {
		if (!mTrackers.containsKey(trackerId)) {
			GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
			Tracker t = analytics.newTracker(R.xml.analytics);
	      mTrackers.put(trackerId, t);

	    }
	    return mTrackers.get(trackerId);
	  }
	
	
	
}
