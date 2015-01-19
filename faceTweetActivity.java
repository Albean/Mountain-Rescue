package com.fyp.mountainRescue2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

//import com.facebook.android.DialogError;
//import com.facebook.android.Facebook;
//import com.facebook.android.Facebook.DialogListener;
//import com.facebook.android.FacebookError;

public class faceTweetActivity extends Activity /*implements View.OnClickListener*/ {
	
	private final static String URL_STATUSES_USER_TIMELINE = "http://api.twitter.com/1/statuses/user_timeline.json";
	public static final String CONSUMER_KEY ="VhwE2GccgRS0R29EZCb8FA";
	public static final String CONSUMER_SECRET = "6HKLL34it2vmziCNdP49927DuH8vEoieiV9KdhcM";
	public static final String REQUEST_URL = "https://api.twitter.com/oauth/request_token";
	public static final String ACCESS_URL = "https://api.twitter.com/oauth/access_token";
	public static final String AUTHORIZE_URL = "https://api.twitter.com/oauth/authorize";
	private final static int TWEET =10;
	final public static String CALLBACK_SCHEME ="http://otweet.com/authenticated";
	final public static String CALLBACK_URL = CALLBACK_SCHEME + "://callback";

	
	
	private TextView status;
	private TextView tweet;
	
	private JSONObject twitter = null;
	private HttpClient client = new DefaultHttpClient();
	
	private OAuthProvider provider = new CommonsHttpOAuthProvider(REQUEST_URL,ACCESS_URL,AUTHORIZE_URL);
	private CommonsHttpOAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY,CONSUMER_SECRET);
	
	private Button TwitterLoginButton;
	private Button TwitterReplyButton;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		//TwitterLoginButton = (Button) findViewById(R.id.TwitterLoginButton);
		//TwitterLoginButton.setOnClickListener(this);
		//TwitterReplyButton =(Button) findViewById(R.id.TwitterReplyButton);
		//TwitterReplyButton.setOnClickListener(this);
		//status =(TextView) findViewById(R.id.status);
		//tweet =(TextView) findViewById(R.id.tweet);
		
		
	}
	
	
	/*public JSONObject readStatus(String screenName)
			throws ClientProtocolException, IOException, JSONException {
		StringBuilder fullUrl = new StringBuilder(URL_STATUSES_USER_TIMELINE);
		fullUrl.append("?screen_name=");
		fullUrl.append(screenName);

		HttpGet get = new HttpGet(fullUrl.toString());
		HttpResponse response = client.execute(get);

		int statusCode = response.getStatusLine().getStatusCode();

		if (statusCode == 200) {
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(entity);
			JSONArray bunchOfTweets = new JSONArray(json);
			JSONObject mostRecentTweet = bunchOfTweets.getJSONObject(0);
			return mostRecentTweet;
		} else {
			String reason = response.getStatusLine().getReasonPhrase();
			throw new RuntimeException("Trouble reading status(code="
					+ statusCode + "):" + reason);
		}
	}

	

	
	public void onClick(View v)
	{
		if(v == TwitterLoginButton)
		{
			twitter();
			
		}else if(v == TwitterReplyButton)
		{	
			showDialog(TWEET);
		}
	}
		
	@Override
	protected void onNewIntent(Intent intent){
		super.onNewIntent(intent);
		Uri uri = intent.getData();
		if(uri != null){
			String uriString = uri.toString();
			if(uriString.startsWith(CALLBACK_URL)){
				try{
					String verifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);
					provider.retrieveAccessToken(consumer, verifier);
					status.setText("Authenticated with Twitter");
				}catch(Exception e){
					throw new RuntimeException(e);
					
				}
			}
		}
		
		
	}
	private void twitter(){
	try{
			String authUrl = provider.retrieveRequestToken(consumer,CALLBACK_URL);
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl)));
		}catch(Exception e){
			Log.e("Error in twitter",e.getMessage());
			throw new RuntimeException(e);
			
			
		}
		
	}
	
	@Override
	protected Dialog onCreateDialog(int id){
		if(id == TWEET){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Create a Tweet")
			.setCancelable(false)
			.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int id)
				{
					status.setText("Tweeting");
					//new status().execute("Nothing");
			
				}
			
			})
			.setNegativeButton("No",
					new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int id){
					dialog.cancel();
					
				}
				
			});
			
			
			
		}
	//	return builder.create();
		
		
	//}else if(){}
	

	//public void Status(){
		try{
			Configuration conf = new ConfigurationBuilder()
			.setOAuthConsumerKey(consumer.getConsumerKey())
			.setOAuthConsumerSecret(consumer.getConsumerSecret())
			.build();
			
			AccessToken accessToken = new AccessToken(consumer.getToken(),
					consumer.getTokenSecret());
			
		//	Twitter twitter = new TwitterFactory(conf).getOAuthAuthorizedInstance(accessToken);
			
			String text ="Testing on Twitter";
			
			//twitter.updateStatus(text);
			
			
		}catch (Exception e){
			throw new RuntimeException(e);
			
		}
	}
	
	


	private class PostOnWall extends AsyncTask<String, Integer, String>
	{
		@Override
		protected String doInBackground(String... nothing)
		{
			try{
				message();
				return "Message tweeted";
				
				
			}catch(Exception e){
				Log.w("Try to post to wall",e);
				return "Error displaying post";
				
			}
			
		}
	

	}
	
	private void message() throws FileNotFoundException, MalformedURLException, IOException {
		Bundle bundle = new Bundle();
		bundle.putString("message","Testing posting a tweet");
		
	}
	
	
			//private class ReadTweet extends AsyncTask<String, Integer, String>
			//{
				//@Override
				//protected String doInBackground(String... nothing)
				//{
					//try{
			//			tweet = readStatus(screenName[0]);
				//		return tweet.getString("text");
						
					//}catch(Exception e){
						//Log.w("error tweeting",e);
						//return "error tweeting";
						
					//}
				//}
		
			
			
				private class Status extends AsyncTask<String, Integer, String>
				{
					@Override
					protected String doInBackground(String... nothing)
					{
						try
						{
					//		updateStatus();
							return "Tweeted";
						
						}catch(Exception e){
							Log.w("Error Creating Tweet",e);
							return "error creating tweet";
						
						}
					}
			
				}
			//}
	//}
	
			protected void onPostExecute(final String result){
				//runOnUiThread(new Runnable(){
				//@Override
				//public void run(){
					//status.setText(result);
					
	///			}
		//		});
			//}
			}
			
		//	private class AuthorizeListener implements DialogListener{
			//	@Override
				//public void onComplete(Bundle values){
					//status.setText("");
				//}
			//}*/
	
	


	
}

