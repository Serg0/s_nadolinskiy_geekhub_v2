package com.example.geekhub_3rd_homework;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.app.PendingIntent.OnFinished;
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
import android.database.SQLException;
import android.widget.TextView;

public class MainActivity extends SherlockFragmentActivity {
	FragmentTransaction fragmentTransaction;
	TitlesFragment titlesFragment;
	ArrayList<Article> array = null;
	private static Context context;
	private static  MainActivity Instance;
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
	
	public   MainActivity getThis(){ return this;}
	
	public void onRestartPublic(){ 
		
//		Intent refresh = new Intent(this, SecondActivity.class);
//		startActivity(refresh);
//		this.finish();
		 Log.d(LOG_TAG, " Trying to Restart");
		this.invalidateOptionsMenu();
		Log.d(LOG_TAG, " After to Restart");
		
	}
    @SuppressWarnings("static-access")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        MainActivity.context = getApplicationContext();
        Instance = this;
        ((TextView) Instance.findViewById(R.id.textView1)).setText(message);
       // HelperFactory.SetHelper(getApplicationContext());
       
        
    
       if (!isMyServiceRunning()){ 
    	  startService(new Intent(this, ConnectionCheckUpdateServise.class));
    	 // DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
           HelperFactory.SetHelper(getApplicationContext());
    	  Log.d(LOG_TAG, "Servise is started");
    	  }else
    	  { Log.d(LOG_TAG, "Servise not restarted");}
        
        
       DataProvider.getFeed();
     
       if (DataProvider.isOnline()){

    	   Log.d(LOG_TAG, " titlesFragment");
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
    	           }).setNegativeButton("Try from DB",
    	                   new DialogInterface.OnClickListener() {
    	                       public void onClick(DialogInterface dialog, int id) {
    	                         
    	                    	   //finish();
    	                       }
    	                   });
    	   builder.create().show();
       }
       
       Log.d(LOG_TAG, " builder.create().show();");
       
    }
    
   
//	@Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_main, menu);
//        return true;
//    }





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

private void OnFinished() throws SQLException, java.sql.SQLException {
	// TODO Auto-generated method stub
	HelperFactory.GetHelper().getArticleDAO().getAllArticle().clear();
	HelperFactory.ReleaseHelper();
}


}