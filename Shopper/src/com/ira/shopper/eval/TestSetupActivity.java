package com.ira.shopper.eval;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.ira.shopper.R;
import com.ira.shopper.settings.AppSettings;
import com.ira.shopper.ui.MainActivity;

public class TestSetupActivity extends Activity{
	private static final String TAG = "Test Setup";

    private EditText mNameEditText;
    private CheckBox mContextCheckBox;
    private CheckBox mUseRealContextCheckBox;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_setup);
        setupViews();
    }
    
    private void setupViews() {
        View startButton = findViewById(R.id.buttonTestSetupStart);
        startButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onStartTest();
            }
        });

        mNameEditText = (EditText) findViewById(R.id.editTextTestSetupName);
        String prevUserName = Statistics.get().getUserName();
        mNameEditText.setText(TextUtils.isEmpty(prevUserName) ? "thisis"
                + new Random().nextInt(999999) : prevUserName);

        mContextCheckBox = (CheckBox)findViewById(R.id.checkBoxTestSetupContext);
        mContextCheckBox.setChecked(AppSettings.isUsingContext(this));
        
        mUseRealContextCheckBox = (CheckBox)findViewById(R.id.checkBoxTestSetupRealContext);
        mUseRealContextCheckBox.setChecked(ContextInitActivity.USE_REAL_CONTEXT);
    }
    
    protected void onStartTest() {
        if (TextUtils.isEmpty(mNameEditText.getText())) {
            Toast.makeText(this, "Please supply a name or pseudonym.", Toast.LENGTH_LONG).show();
            return;
        }

        // set diversity on or off
        PreferenceManager.getDefaultSharedPreferences(this).edit()
                .putBoolean(AppSettings.KEY_USING_DIVERSITY, true)
                .commit();
        Log.d(TAG, "Setting diversity to : " + "ON");

        // set using context on or off
        PreferenceManager.getDefaultSharedPreferences(this).edit()
	        .putBoolean(AppSettings.KEY_USING_CONTEXT, this.mContextCheckBox.isChecked())
	        .commit();
        Log.d(TAG, "Setting using context to : " + (mContextCheckBox.isChecked() ? "ON" : "OFF"));
        
        ContextInitActivity.USE_REAL_CONTEXT = mUseRealContextCheckBox.isChecked();
        Log.d(TAG, "Setting using real context to: " + (mUseRealContextCheckBox.isChecked() ? "ON" : "OFF"));
        
        // record name, time and type, start task
        Statistics.get().startTask(mNameEditText.getText().toString(),
                true, mContextCheckBox.isChecked());

        /*
    	 * ContextInitActivity is created for evaluation purpose.
    	 * In real use, the app should go directly to MainActivity.
    	 */
    	startActivity(new Intent(this, ContextInitActivity.class));
        
    }
    
    
}
