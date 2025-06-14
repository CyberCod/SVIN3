package com.smiths;

import com.smiths.R;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;

public class Preferences extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	SharedPreferences preferences;
	//ListPreference listpref0;
/** Called when the activity is first created. */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    addPreferencesFromResource(R.xml.preferences);
	    this.initSummaries(this.getPreferenceScreen());
	   
	}
	
	
	
	@Override
    protected void onResume() {
        super.onResume();
     // Set up a listener whenever a key changes            
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        
        
        this.initSummaries(this.getPreferenceScreen());
        
    }
	
	
	@Override
    protected void onPause() {
        super.onPause();
        
        // Unregister the listener whenever a key changes            
       getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);    
    }
	
	

	
	
	/**
	    * Set the summaries of all preferences
	    */
	  private void initSummaries(PreferenceGroup pg) {
	    for (int i = 0; i < pg.getPreferenceCount(); ++i) 
	    {
	    	Preference p = pg.getPreference(i);
	    	if (p instanceof PreferenceGroup)
	    		this.initSummaries((PreferenceGroup) p); // recursion
	    else
	      this.setSummary(p);
	    }
	  }
	
	
	/**
	    * Set the summaries of the given preference
	    */
	  private void setSummary(Preference pref) {
	    // react on type or key
	      if (pref instanceof ListPreference) {
	      ListPreference listPref = (ListPreference) pref;
	      pref.setSummary(listPref.getEntry());
	      }
	  }

	  /**
	    * used to change the summary of a preference
	    */
	  @Override
	  public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
		  
	    Preference pref = findPreference(key);
	    this.setSummary(pref);
	  
	  }
}