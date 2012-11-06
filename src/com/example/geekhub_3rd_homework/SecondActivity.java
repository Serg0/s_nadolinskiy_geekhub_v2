package com.example.geekhub_3rd_homework;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class SecondActivity extends FragmentActivity {

	public FragmentTransaction fTrans2;
	DetailsFragment dFragment;
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 super.onCreate(savedInstanceState);
		setContentView(R.layout.second_activity);
		
		dFragment = new DetailsFragment();
//        
		fTrans2 = getSupportFragmentManager().beginTransaction();
		fTrans2.add(R.id.frgmCont2, dFragment);
        fTrans2.commit();  
//                 
                 
	   
	}
}
