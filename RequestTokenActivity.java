package com.fyp.mountainRescue2;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import oauth.signpost.OAuth;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;


/*
public class RequestTokenActivity extends Activity{
	final String TAG = getClass().getName();
	private OAuthConsumer consumer;
	private OAuthProvider provider;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		try{
			this.consumer = new CommonsHttpOAuthConsumer(Constants.CONSUMER_KEY,Constants.CONSUMER_SECRET);
			this.provider = new CommonsHttpOAuthProvider(Constants.REQUEST_URL, Constants.ACCESS_URL, Constants.AUTHORIZE_URL);
		}catch(Exception e){
			Log.e(TAG,"Error creating keys",e);
			
		}
		
		Log.i(TAG,"Retrieving Token");
		new OAuthRequestTokenTask(this,consumer,provider).execute();
		
	}
	
	@Override
	public void onNewIntent(Intent intent)
	{
		super.onNewIntent(intent);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		final Uri uri = intent.getData();
		if(uri != null && uri.getScheme().equals(Constants.OAuth_CALLBACK_SCHEME)){
			
			new RetrieveAccessTokenTask(this,consumer.provider,prefs).execute(uri);
			finish();
			
		}
	}
	
}*/
