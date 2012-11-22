package com.example.geekhub_3rd_homework;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	FragmentTransaction fragmentTransaction;
	TitlesFragment titlesFragment;
	ArrayList<Article> array = null;
	

	
	public static boolean isTablet(Context context) {
	    boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
	    boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
	    return (xlarge || large);
	}
	
	public static boolean isLandscape(Context context){
	//if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {}
	return (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
	}
	
    @SuppressWarnings("static-access")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
      //  Toast.makeText(this.getBaseContext(), "Landscape is " + Boolean.toString(isLandscape(this)), Toast.LENGTH_LONG).show();
       if (DataProvider.isOnline(this)){
    	 // Toast.makeText(this.getBaseContext(), "Connection is UP" , Toast.LENGTH_LONG).show();
    	   
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



}
