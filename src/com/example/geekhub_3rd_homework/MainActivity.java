package com.example.geekhub_3rd_homework;

import java.util.ArrayList;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.content.BroadcastReceiver;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {
	FragmentTransaction fragmentTransaction;
	TitlesFragment titlesFragment;
	ArrayList<Article> array = null;
	private static Context context;
	private static MainActivity Instance;
	static String message = null;
	final public static String CONNECTION_CHECK_UPDATER = "com.example.geekhub_3rd_homework.CONNECTION_CHECK_UPDATER";
	private static final String LOG_TAG = "MyLog";
	
	private boolean isMyServiceRunning() {
	    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if (ConnectionCheckUpdateServise.class.getName().equals(service.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}
	
	public static boolean isTablet(Context context) {
	    boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
	    boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
	    return (xlarge || large);
	}
	
	public static boolean isLandscape(Context context){
	
	return (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
	}
	
	public static Context getAppContext() {
        return MainActivity.context;
    }
	
    @SuppressWarnings("static-access")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.context = getApplicationContext();
        Instance = this;
        ((TextView) Instance.findViewById(R.id.textView1)).setText(message);
        
       if (!isMyServiceRunning()){ 
    	  startService(new Intent(this, ConnectionCheckUpdateServise.class));
    	  Log.d(LOG_TAG, "Servise is started");
    	  }else
    	  { Log.d(LOG_TAG, "Servise not restarted");}
        
        
        
     
       if (DataProvider.isOnline()){

    	   
           titlesFragment = new TitlesFragment();
           fragmentTransaction = getSupportFragmentManager().beginTransaction();
           //Tablet land
           if ((isTablet(this))&&(isLandscape(this))) {
           	fragmentTransaction.replace(R.id.frgmCont3, titlesFragment);
           }else //Tablet port
           	if ((isTablet(this))&&(!isLandscape(this))){
           		fragmentTransaction.replace(R.id.frgmCont5, titlesFragment);
           	}else //phone
           {
           	fragmentTransaction.replace(R.id.frgmCont, titlesFragment);
           }
           
           fragmentTransaction.commit();  
          
    	   
       }else
       {
    	   AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	   builder.setMessage("No internet connection")
    	                           .setCancelable(false)
    	           .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
    	        	   public void onClick(DialogInterface dialog, int id) {
    	           		   finish();
    	        		   startActivity(getIntent());
    	               }
    	           }).setNegativeButton("Cancel",
    	                   new DialogInterface.OnClickListener() {
    	                       public void onClick(DialogInterface dialog, int id) {
    	                         
    	                    	   finish();
    	                       }
    	                   });
    	   builder.create().show();
       }
       

       
    }
    
   
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }





public static class BroadcastListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Instance == null || intent == null) {
            return;
        }
        
        if (DataProvider.isOnline()){message = "Connection is UP";} else {message = "Connection is DOWN";}

        String action = intent.getAction();
        if (action.equals(CONNECTION_CHECK_UPDATER)) {
            ((TextView) Instance.findViewById(R.id.textView1)).setText(message);
        }
    }
}

}