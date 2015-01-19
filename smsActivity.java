package com.fyp.mountainRescue2;



import android.app.Activity;
import android.os.Bundle;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//This application is used to see if your are able to send a message within the application
public class smsActivity extends Activity{
	Button SMS;
	IntentFilter messageIntent;
	
	private BroadcastReceiver intentReciever = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent){
			TextView smsR = (TextView) findViewById(R.id.textView1);
			smsR.setText(intent.getExtras().getString("sms"));
		}
	};
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send);
		
		SMS = (Button) findViewById(R.id.sendSMS);
		SMS .setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//sendSMS("123456789","Testing");
				Intent i = new Intent(android.content.Intent.ACTION_VIEW);
				i.putExtra("address","123456789; 987654321;010101010");
				i.putExtra("sms_body", "This is a test!");
				i.setType("vnd.android-dir/mms-sms");
				startActivity(i);
			}
		});
		
	}
}
