package com.fyp.mountainRescue2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

//This java file is used to display the help document that is written in txt format.
public class AboutActivity extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
		TextView help = (TextView)findViewById(R.id.about);
		help.setText(readText());
		
		
	}
	private String readText(){
		InputStream inputStream = getResources().openRawResource(R.raw.about);
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		int i ;
		try{
			i = inputStream.read();
			while(i != -1)
			{
				byteArrayOutputStream.write(i);
				i = inputStream.read();
				
			}
			inputStream.close();
			
		}catch(IOException e){
			e.printStackTrace();
			
		}
		
		return byteArrayOutputStream.toString();
	}
}
