package com.example.geekhub_3rd_homework;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuInflater;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends SherlockFragmentActivity {

	FragmentTransaction fragmentTransaction;
	DetailsFragment detailsFragment;
	//static boolean active = false;
	final String LOG_TAG = "myLogs";
		com.actionbarsherlock.app.ActionBar actionBar;
	private static SecondActivity Instance;
	
	
//	final public static boolean isActive() {
//		return active;
//	}
//	@Override
//    public void onStart() {
//       super.onStart();
//       active = true;
//    } 
//	 @Override
//     public void onStop() {
//        super.onStop();
//        active = false;
//     }	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case R.id.addLike:
			Toast.makeText(getApplicationContext(), "Like added!!", Toast.LENGTH_LONG).show();
			return true;
		case R.id.showAllLikes:
			Toast.makeText(getApplicationContext(), "All likes showed!!", Toast.LENGTH_LONG).show();
			return true;
			
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 super.onCreate(savedInstanceState);
		setContentView(R.layout.second_activity);
	    Instance = this;
	    ((TextView) Instance.findViewById(R.id.textView1)).setText(MainActivity.message);
	    
	    actionBar=getSupportActionBar();
		detailsFragment = new DetailsFragment();
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.frgmCont2, detailsFragment);
		fragmentTransaction.commit();  
                 
	   
	}
	
	public static class BroadcastListener extends BroadcastReceiver {

	    @Override
	    public void onReceive(Context context, Intent intent) {
	        if (Instance == null || intent == null) {
	            return;
	        }
	        
	      //  if (DataProvider.isOnline()){MainActivity.message = "Connection is UP";} else {MainActivity.message = "Connection is DOWN";}

	        String action = intent.getAction();
	        if (action.equals(MainActivity.CONNECTION_CHECK_UPDATER)) {
	            ((TextView) Instance.findViewById(R.id.textView1)).setText(MainActivity.message);
	        }
	    }
	}

	
}
