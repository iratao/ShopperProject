
package com.ira.shopper.utils;

import android.os.Environment;

public class Utils {

    /**
     * Returns the first http url out of the given array in the form of
     * "[http://abc,http://cde]".
     */
    public static String extractFirstUrl(String arrayAsString) {
        arrayAsString = arrayAsString.substring(1, arrayAsString.length() - 1);
        /*
         * Some eBay URLs sadly include commas (,), so really split by ",http".
         * We only want the first one anyhow.
         */
        String url = arrayAsString.split(",http")[0];
        return url;
    }
    
    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    
    
    

}
