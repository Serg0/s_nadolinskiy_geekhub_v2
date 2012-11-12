package com.example.geekhub_3rd_homework;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class SecondActivity extends FragmentActivity {

	FragmentTransaction fragmentTransaction;
	DetailsFragment detailsFragment;
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		setContentView(R.layout.second_activity);
	    super.onConfigurationChanged(newConfig);
	    }
	
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
