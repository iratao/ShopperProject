
package com.ira.shopper.importer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.ira.shopper.R;
import com.ira.shopper.provider.ShopperContract.Stats;
import com.ira.shopper.utils.Utils;

/**
 * Allows importing of a data set of items and shops from CSV files into the
 * database.
 */
public class ImporterActivity extends ActionBarActivity {
	private static final String TAG = "ImporterActivity";

    private static final int CODE_IMPORT_SHOPS = 100;
    private static final int CODE_IMPORT_ITEMS = 200;
    private static final int CODE_IMPORT_CONTEXT_CASES = 300;
    
    private static final String FILENAME_STATISTICS = "statistics.csv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_importer);

        setupActionBar();
        setupViews();
    }

    /**
     * Set up the {@link android.app.ActionBar}.
     */
    private void setupActionBar() {

    	getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void setupViews() {
        View buttonImportShops = findViewById(R.id.buttonImporterShops);
        buttonImportShops.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onDisplayChooser(CODE_IMPORT_SHOPS);
            }
        });
        View buttonImportItems = findViewById(R.id.buttonImporterItems);
        buttonImportItems.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onDisplayChooser(CODE_IMPORT_ITEMS);
            }
        });
        
        View buttonImportContext = findViewById(R.id.buttonImporterContext);
        buttonImportContext.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v) {
                onDisplayChooser(CODE_IMPORT_CONTEXT_CASES);
            }
        });
        
        View buttonExportStatistics = findViewById(R.id.buttonExportStatistics);
        buttonExportStatistics.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v) {
                exportStatistics();
            }
        });
    }
    
    private void exportStatistics(){
    	Cursor query = getBaseContext().getContentResolver().query(
    			Stats.CONTENT_URI,
                new String[] {
                		Stats.USERNAME, Stats.TASK_TYPE, Stats.CYCLE_COUNT, Stats.DURATION}, 
                		null, null, null);
    	if (query != null) {
    		if(Utils.isExternalStorageWritable()){
            	try {
            		String separator = System.getProperty("line.separator");
            		File file = new File(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOWNLOADS), FILENAME_STATISTICS);
            		FileOutputStream fos = new FileOutputStream(file);
            		OutputStreamWriter out = new OutputStreamWriter(fos);
            		BufferedWriter bwriter = new BufferedWriter(out);
            		
        			bwriter.write(("username,task_type,cycle_count,duration"));
        			bwriter.write(separator);
            		
            		while (query.moveToNext()) {
            			bwriter.write((query.getString(0) + "," + query.getString(1) + "," + 
                		    query.getInt(2) + "," + query.getInt(3)));
                		bwriter.write(separator);
        			}
            		
            		bwriter.close();
            		out.close();
            		fos.close();
            		
            		Toast.makeText(
                            this, "Export successful",
                            Toast.LENGTH_SHORT).show();
    				
    			} catch (FileNotFoundException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		}
    	}
    }

    private void onDisplayChooser(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("text/csv");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent,
                    getString(R.string.import_a_csv_file)), requestCode);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, R.string.import_no_file_manager,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CODE_IMPORT_ITEMS || requestCode == CODE_IMPORT_SHOPS 
            		|| requestCode == CODE_IMPORT_CONTEXT_CASES) {
                if (requestCode == CODE_IMPORT_SHOPS) {
                    onImportShops(data.getData());
                } else if(requestCode == CODE_IMPORT_CONTEXT_CASES){
                	onImportContextCases(data.getData());
                }else {
                    onImportItems(data.getData());
                }
            }
        }
    }
    
    protected void onImportContextCases(Uri uri){
    	CsvImporter.importContextCasesToDatabse(this, uri);
    }

    
//    private void initContextDatabase(){
//		copyAssets();
//		File factorFile = new File(getExternalFilesDir(null), "context_factor.csv");
//		String factorUriPath = "file://" + factorFile.getAbsolutePath();
//		Uri factorUri = Uri.parse(factorUriPath);
//		CsvImporter.importContextFactorToDatabase(this, factorUri);
//		
//		File valueFile = new File(getExternalFilesDir(null), "context_value.csv");
//		String valueUriPath = "file://" + valueFile.getAbsolutePath();
//		Uri valueUri = Uri.parse(valueUriPath);
//		CsvImporter.importContextValueToDatabase(this, valueUri);
//	}

    protected void onImportShops(Uri uri) {
        CsvImporter.importShopsCsvToDatabase(this, uri);
    }

    protected void onImportItems(Uri uri) {
    	CsvImporter.importItemsCsvToDatabase(this, uri);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.importer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
//    private void copyAssets() {
//        AssetManager assetManager = getAssets();
//        String[] files = null;
//        try {
//            files = assetManager.list("");
//        } catch (IOException e) {
//            Log.e("tag", "Failed to get asset file list.", e);
//        }
//        for(String filename : files) {
//            InputStream in = null;
//            OutputStream out = null;
//            try {
//              in = assetManager.open(filename);
//              Log.d(TAG, "copyAssets to: " + getExternalFilesDir(null).getAbsolutePath());
//              File outFile = new File(getExternalFilesDir(null), filename);
//              out = new FileOutputStream(outFile);
//              copyFile(in, out);
//              in.close();
//              in = null;
//              out.flush();
//              out.close();
//              out = null;
//            } catch(IOException e) {
//                Log.e("tag", "Failed to copy asset file: " + filename, e);
//            }       
//        }
//    }
    
//    private void copyFile(InputStream in, OutputStream out) throws IOException {
//        byte[] buffer = new byte[1024];
//        int read;
//        while((read = in.read(buffer)) != -1){
//          out.write(buffer, 0, read);
//        }
//    }

}
