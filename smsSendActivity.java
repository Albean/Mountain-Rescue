package com.fyp.mountainRescue2;



import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class smsSendActivity extends Activity{
	Button sendButton;
	EditText smsNumber;
	EditText smsMessage;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sms);
		
		sendButton = (Button) findViewById(R.id.sendButton);
		smsMessage = (EditText) findViewById(R.id.smsMessage);
		smsNumber = (EditText) findViewById(R.id.smsNumber);
		
		sendButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v)
			{
				String phone = smsNumber.getText().toString();
				String sms = smsMessage.getText().toString();
				if(phone.length()>0 && sms.length() > 0){
					send(phone,sms);
					
				}
				else{
					Toast.makeText(getBaseContext(),
							"You must enter both fields",Toast.LENGTH_SHORT).show();
				}
			}
			
		});
		
	}
	
	private void send(String phone, String sms)
	{
		String SENT = "SMS_SENT";
		String DELIVERED = "SMS_DELIVERED";
		
		PendingIntent send = PendingIntent.getBroadcast(this,0,new Intent(SENT),0);
		PendingIntent delivered =PendingIntent.getBroadcast(this, 0,new Intent(DELIVERED), 0);
		
		//PendingIntent pi = PendingIntent.getActivity(this,0, new Intent(this,smsSendActivity.class), 0);
		//SmsManager mSms = SmsManager.getDefault();
		//mSms.sendTextMessage(phone, null, sms, pi, null);
		
		registerReceiver(new BroadcastReceiver(){
		
			public void onReceive(Context arg0, Intent arg1){
				switch(getResultCode())
				{
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "SENT", Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					Toast.makeText(getBaseContext(), "FAILURE", Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_NO_SERVICE:
					Toast.makeText(getBaseContext(), "NO SERVICE", Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU:
					Toast.makeText(getBaseContext(), "NULL PDU", Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF:
					Toast.makeText(getBaseContext(), "RADIO OFF", Toast.LENGTH_SHORT).show();
					break;
					
				}
			}
		}, new IntentFilter(SENT));
		
		
		registerReceiver(new BroadcastReceiver(){
		public void onReceive(Context arg0, Intent arg1){
			switch(getResultCode())
			{
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "DELIVERED", Toast.LENGTH_SHORT).show();
					break;
				case Activity.RESULT_CANCELED:
					Toast.makeText(getBaseContext(), "NOT DELIVERED", Toast.LENGTH_SHORT).show();
					break;
			}
		}
		},new IntentFilter(DELIVERED));
		SmsManager mSms1 = SmsManager.getDefault();
		mSms1.sendTextMessage(phone, null, sms,send,delivered);
		
	}

	

	
	
	
}
