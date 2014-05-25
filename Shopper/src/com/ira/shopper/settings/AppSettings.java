
package com.ira.shopper.settings;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Quick access to some app settings.
 */
public class AppSettings {

    public static final String KEY_MAX_RECOMMENDATIONS = "com.uwetrottmann.shopr.maxrecommendations";
    public static final String KEY_FAKE_LOCATION = "com.uwetrottmann.shopr.fakelocation";
    public static final String KEY_USING_DIVERSITY = "com.uwetrottmann.shopr.usingdiversity";
    public static final String KEY_USING_CONTEXT = "com.ira.shopper.usingcontext";

    public static int getMaxRecommendations(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(
                KEY_MAX_RECOMMENDATIONS, 9);
    }

    public static boolean isUsingFakeLocation(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(KEY_FAKE_LOCATION,
                true);
    }

    public static boolean isUsingDiversity(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(
                KEY_USING_DIVERSITY, true);
    }
    
    public static boolean isUsingContext(Context context){
    	return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(
    			KEY_USING_CONTEXT, true);
    }
}
