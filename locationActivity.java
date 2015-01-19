package com.fyp.mountainRescue2;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.*;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class locationActivity extends MapActivity{
	
	private MapView mapView;
	private MapController mapController;
	private MyLocationOverlay locationOverlay;
	private LocationManager locationManager;
	private LinearLayout searching;
	private Button send;
	private GeoPoint lastlocation;

	private Boolean satelliteMode;
	
	
	
	
	

@Override
public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//this suppresses the title bar in the application
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		String[] situation = getResources().getStringArray(R.array.situation);
		
		//If GPS has not been turned on by the User
		//this piece of code directs them to my location in the phone settings to turn on GPS
		//taken from http://www.vogella.de/articles/AndroidLocationAPI/article.html#locationapi_criteria		
		LocationManager service =(LocationManager)getSystemService(Context.LOCATION_SERVICE);
		boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if(!enabled)
		{
			Toast.makeText(this, "GPS is OFF", Toast.LENGTH_LONG).show();
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(intent);
			
			
		}
		if(enabled)
		{
			Toast.makeText(this
					, "GPS is turned ON", Toast.LENGTH_LONG).show();
		}
		 Toast.makeText(locationActivity.this, "Looking for YOU", Toast.LENGTH_SHORT).show();
		 //this allows preferences/settings access to this activity
		SharedPreferences settings = getPreferences(MODE_WORLD_WRITEABLE);
		satelliteMode = settings.getBoolean("satelliteMode",true);
		
		searching = (LinearLayout) findViewById(R.id.searching);
		send = (Button) findViewById(R.id.send_location);
		mapView = (MapView) findViewById(R.id.mapView);
		
		//Progress bar disappears when location has been found
		final ProgressDialog loading = ProgressDialog.show(locationActivity.this,"Please Wait....","Looking for YOU!!!");
		new Thread(new Runnable(){
			public void run(){
				try{
					Thread.sleep(5000);
					loading.dismiss();
					
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}).start();
		
		//map controls are set up here
		mapController = mapView.getController();
		mapView.setBuiltInZoomControls(true);
		if(satelliteMode){
			mapView.setSatellite(true);
		
		}else{
			mapView.setSatellite(false);
		
		}
		
		if(Preferences.getMyLocation(getApplicationContext()))
		{
			locationOverlay = new MyLocationOverlay(this,mapView);
		}
		
		//Add if statements to for settings to enable/disable map and compass
		if(Preferences.getMyLocation(getApplicationContext()))
		{
			locationOverlay.enableMyLocation();
		}
		if(Preferences.getCompass(getApplicationContext()))
		{	
			locationOverlay.enableCompass();
		}
		
			mapView.getOverlays().add(locationOverlay);
		
		/*Geocoder coder = new Geocoder(getBaseContext(), Locale.getDefault());
		try
		{
			//To address from latitude and longitude
			List<Address> addresses = coder.getFromLocation(lastlocation.getLatitudeE6() /1E6 , lastlocation.getLongitudeE6() / 1E6, 1 );
			String add = "";
			if(addresses.size()>0)
			{
				
				
				for(int i = 0; i <addresses.get(3).getMaxAddressLineIndex(); i ++)
				{
					
						add += addresses.get(3).getAddressLine(i) + "\n";
					
					
				}
				Toast.makeText(getBaseContext(), add, Toast.LENGTH_SHORT);
			}
				
		}catch (IOException e)
		{
			Log.e("Location Address","Failed to get location address",e);
		}*/
	
		

		//This draws the devices current location on map as soon as it gets a fix on the gps location
		locationOverlay.runOnFirstFix(new Runnable(){
			public void run(){
				if(searching.getHandler() != null){
					searching.getHandler().post(new Runnable(){
						public void run(){
							lastlocation = locationOverlay.getMyLocation();
							if(lastlocation != null){
								//Once location is found several controls are enabled
								mapController.animateTo(lastlocation);
								mapController.setZoom(19);
								searching.setVisibility(View.GONE);
								send.setVisibility(View.VISIBLE);
								//a toast is displayed showing the latitude and longitude tuor position
								Toast.makeText(getBaseContext(),lastlocation.getLatitudeE6()/ 1E6 + "," 
								+ lastlocation.getLongitudeE6() /1E6, Toast.LENGTH_SHORT).show();
								
							}
						}
						
					});
				}
			}
		});
		
		send.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Context context = getApplicationContext();
				SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
				
				if(locationOverlay.getMyLocation() != null)
				{
					lastlocation =locationOverlay.getMyLocation();
					
				}
				//This is an intent that uses ACTION_SEND to share the content of the message with other applications on the phone
				Intent i = new Intent(android.content.Intent.ACTION_SEND);  
				
				//getLatitudeE6 and getLongitudeE6 returns the latitude and longitude in microdegrees
				CharSequence location = lastlocation.getLatitudeE6()/ 1E6 + "," + lastlocation.getLongitudeE6() / 1E6;
				i.setType("text/plain");
				
				//EXTRA_SUBJECT is a constant that gets it value from the value that is declared in settings 
				i.putExtra(Intent.EXTRA_SUBJECT, settings.getString("SOS Message", getString(R.string.location_subject)));
				
				//Text is added to the message by using EXTRA_TEXT which gets it value from  a string that can be changed in settings
				i.putExtra(Intent.EXTRA_TEXT,  settings.getString("Situation",  getString(R.string.location_body)) + "http://maps.google.com/maps?q=loc:" + location);
				
				//This brings up a list of options where you can share  your the message
				try
				{
					startActivity(Intent.createChooser(i, getString(R.string.share)));
					
				}catch(android.content.ActivityNotFoundException ex)
				{
					Toast.makeText(context, getString(R.string.no_share),Toast.LENGTH_LONG).show();
				}
				
			}
		});
	}
	


	protected Object getStringArray(int situations) {
	// TODO Auto-generated method stub
	return null;
}



	@Override
	protected void onStop()
	{
		super.onStop();
		SharedPreferences settings = getPreferences(MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("satelliteMode", satelliteMode);
	
		editor.commit();
		
		
	}
	@Override
	protected void onPause()
	{
		//if the application is paused the compass location updates are turned off.
		super.onPause();
		if(Preferences.getMyLocation(getApplicationContext()))
		{
			locationOverlay.disableMyLocation();
		}
		  if(Preferences.getCompass(getApplicationContext()))
		  {
        	locationOverlay.disableCompass();
		  }
		
	}
	@Override
	protected void onResume()
	{
		//If the application is started again is checks to see if compass and location updates were enabled in settings
		super.onResume();
		if(Preferences.getMyLocation(getApplicationContext()))
		{
			locationOverlay.enableMyLocation();
		}
		if(Preferences.getCompass(getApplicationContext()))
		{
			locationOverlay.enableCompass();
		}
		
	}
	
		
	
	public void onProviderDisabled(String s)
	{
		//if GPS is turned off a message displays
			Toast.makeText(getApplicationContext(),"GPS is turned OFF", Toast.LENGTH_LONG).show();
		
	}
	
	public void onProviderEnabled(String s)
	{
		//if gps is turned on a message displays
			Toast.makeText(getApplicationContext()
					, "GPS is turned ON", Toast.LENGTH_LONG).show();
		
		
		}

		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
	


	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
