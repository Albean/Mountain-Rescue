package com.fyp.mountainRescue2;






import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.*;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
//This file is used to store configured details for locationActivity
public class Preferences extends PreferenceActivity implements OnSharedPreferenceChangeListener{

	public static final String situation ="list_preference";
	public static final String compass = "checkbox_preference";
	
	private CheckBoxPreference checkPreference;
	private ListPreference listPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.setting);
        
        Context context = getApplicationContext();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.registerOnSharedPreferenceChangeListener(this);
        checkPreference =(CheckBoxPreference)getPreferenceScreen().findPreference(compass);
        listPreference =(ListPreference)getPreferenceScreen().findPreference(situation);
    }
    
    @Override
    protected void onResume()
    {
    	
    	//checkPreference.setSummary(sharedPreferences.getBoolean("compass",true));
    }
    @Override
    protected void onPause()
    {
    	
    }
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
    	Log.i("Preferences", "Preferences changed, key ="+key);
    	if(key.equals(compass))
    	{
    		//checkPreference.setSummary(sharedPreferences.getBoolean(compass,false))
    		
    	}
    }
    public static boolean getCompass(Context context)
    {
    	//This sees whether the compass has been enable or disabled
    	return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("compass",true);
    	
    }
    
    public static boolean getMyLocation(Context context)
    {
    	//This sees whether the map view has been enable or disabled
    	return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("map",true);
    }

}
