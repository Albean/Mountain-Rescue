package com.fyp.mountainRescue2;


import android.os.Bundle;
import android.view.MotionEvent;
import android.app.Activity;
import android.content.Intent;

public class splashActivity extends Activity
{
	
	

	@Override 
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		Thread splashTimer = new Thread(){
		
		public void run(){
			try{
					int splashTimer = 0;
					//splash screen is displayed for 5000 m/s
					while(splashTimer < 5000)
					{
						sleep(100);
						splashTimer = splashTimer +100;
					}
					//Activity is called that clears the splash screen
					startActivity(new Intent ("com.fyp.mountainRescue2.CLEARSCREEN"));
				}catch(InterruptedException e)
				{
					e.printStackTrace();
				}	
				finally
				{
					finish();
				}
			
			}
		};
		splashTimer.start();
		
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	
}
	
	
