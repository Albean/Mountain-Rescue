package com.fyp.mountainRescue2;








import android.app.Activity;
import android.app.ListActivity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class Options extends ListActivity {
	
	String[] Option = {"Activate","Message","Facebook And Twitter","About","Settings","Test"};
	private Dialog dialog;
	private static final String DEBUG_TAG = "Menu_Options";

	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		setContentView(R.layout.menu);
		setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Option));
	}
	
	@Override
	
	public void onListItemClick(ListView l,View v,int position,long id ){
		
		String selection = l.getItemAtPosition(position).toString();
    	Toast.makeText(this, selection, Toast.LENGTH_LONG).show();
		Log.d (DEBUG_TAG, "pos: "+position + " , id: "+ id);
		
		switch(position){
		case 0:
			//Activate menu option
			Intent intent = new Intent(this,
					locationActivity.class);
			startActivity(intent);
			break;
		case 1:
			//Message menu option
			Intent intent1 = new Intent(this,
					smsSendActivity.class);
			startActivity(intent1);
			break;
		case 2:
			//FB and Twitter menu option
			Intent intent2 = new Intent(this,
					faceTweetActivity.class);
			startActivity(intent2);
			break;
		case 3:
			//About menu option
			Intent intent3 = new Intent(this,
				AboutActivity.class);
			startActivity(intent3);
			break;
		case 4:
			//Settings menu option
			Intent intent4 = new Intent(this,
					Preferences.class);
			startActivity(intent4);
			break;
		case 5:
			//Test menu option
			Intent intent5 = new Intent(this,
					smsActivity.class);
			startActivity(intent5);
			break;
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.optionsmenu, menu);
		return true;
		
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		super.onOptionsItemSelected(item);
		switch(item.getItemId()){
		case R.id.help:
			dialog = new Dialog(Options.this);
			dialog.setContentView(R.layout.help);
			dialog.setTitle(R.string.helpTitle);
			dialog.setCancelable(true);
			
			TextView help = (TextView) dialog.findViewById(R.id.helpText);
			help.setText(getString(R.string.help));
			ImageView image = (ImageView)
			
					dialog.findViewById(R.id.help_image);
			image.setImageResource(R.drawable.ic_launcher);
			Button closeButton = (Button) dialog.findViewById(R.id.close);
			
			closeButton.setOnClickListener(new OnClickListener(){
				public void onClick(View v){
					dialog.hide();
					
				}
			});
			dialog.show();
			
		default:
			return super.onOptionsItemSelected(item);
			
		}
	}
	
    
}
