
package com.ira.shopper.importer;

import android.content.Context;
import android.net.Uri;

public class CsvImporter {

    public static void importShopsCsvToDatabase(Context context, Uri uri) {
        new CsvImportTask(context, uri, CsvImportTask.Type.IMPORT_SHOPS).execute();
    }

    public static void importItemsCsvToDatabase(Context context, Uri uri) {
        new CsvImportTask(context, uri, CsvImportTask.Type.IMPORT_ITEMS).execute();
    }
    
    public static void importContextFactorToDatabase(Context context, Uri uri){
    	new CsvImportTask(context, uri, CsvImportTask.Type.IMPORT_CONTEXT_FACTOR).execute();
    }
    
    public static void importContextValueToDatabase(Context context, Uri uri){
    	new CsvImportTask(context, uri, CsvImportTask.Type.IMPORT_CONTEXT_VALUE).execute();
    }
    
    public static void importContextCasesToDatabse(Context context, Uri uri){
    	new CsvImportTask(context, uri, CsvImportTask.Type.IMPORT_CONTEXT_CASES).execute();
    }

}
