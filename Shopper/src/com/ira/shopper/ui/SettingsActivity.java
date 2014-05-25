
package com.ira.shopper.ui;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;

import com.ira.shopper.R;
import com.ira.shopper.settings.AppSettings;
import com.uwetrottmann.shopr.algorithm.AdaptiveSelection;

public class SettingsActivity extends PreferenceActivity implements
	OnSharedPreferenceChangeListener {

    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
        
    }

    @SuppressWarnings("deprecation")
	@Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (AppSettings.KEY_USING_DIVERSITY.equals(key)) {
            // toggle diversity live while using the app
            CheckBoxPreference pref = (CheckBoxPreference) findPreference(key);
            AdaptiveSelection.get().setIsUsingDiversity(pref.isChecked());
        }
		
	}
}
