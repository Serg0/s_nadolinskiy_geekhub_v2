package com.example.geekhub_3rd_homework;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

public class SecondActivity extends SherlockFragmentActivity {

	FragmentTransaction fragmentTransaction;
	DetailsFragment detailsFragment;
	final String LOG_TAG = "myLogs";
		com.actionbarsherlock.app.ActionBar actionBar;
	private static SecondActivity Instance;

	@Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	      case android.R.id.home:
	    	  super.onBackPressed();
	        return true;
	    }
	    return false;
	  } 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		setContentView(R.layout.second_activity);
		 getWindow().setTitle("");
	    Instance = this;
	    ((TextView) Instance.findViewById(R.id.textView1)).setText(MainActivity.message);
	    
	    actionBar=getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
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
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Instance = null;
	}

	
}
