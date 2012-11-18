package com.example.geekhub_3rd_homework;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

public class SecondActivity extends FragmentActivity {

	FragmentTransaction fragmentTransaction;
	DetailsFragment detailsFragment;
	static boolean active = false;
	final String LOG_TAG = "myLogs";
	final public static boolean isActive() {
		return active;
	}
	@Override
    public void onStart() {
       super.onStart();
       active = true;
    } 
	 @Override
     public void onStop() {
        super.onStop();
        active = false;
     }	
//	@Override
//	protected void onSaveInstanceState(Bundle outState) {
//		// TODO Auto-generated method stub
//		super.onSaveInstanceState(outState);
//		try {
//			Log.d(LOG_TAG, " fINALIZED " + this.getClass());
//			this.finalize();
//		} catch (Throwable e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 super.onCreate(savedInstanceState);
		setContentView(R.layout.second_activity);
		
		detailsFragment = new DetailsFragment();
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.frgmCont2, detailsFragment);
		fragmentTransaction.commit();  
                 
	   
	}
	
	
	
}
