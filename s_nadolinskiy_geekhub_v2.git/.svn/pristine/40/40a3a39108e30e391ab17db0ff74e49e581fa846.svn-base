package com.example.geekhub_2d_homework;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class SecondActivity extends FragmentActivity {
	public FragmentTransaction fTrans2;
	  Fragment2 frag2;
	  Fragment3 frag3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_activity);
		/*Intent intent = getIntent();
	    
	    String fName = intent.getStringExtra("fname");
	//Toast.makeText(getApplicationContext(), fName, Toast.LENGTH_SHORT).show();
	 * 
	 */
	 frag2 = new Fragment2();
	    
	 fTrans2 = getSupportFragmentManager().beginTransaction();
	    fTrans2.add(R.id.frgmCont2, frag2);
	    fTrans2.commit();  
		
		
	}
	@Override
	public void onBackPressed()
	{
	    super.onBackPressed();
	    frag2.onBackPressed();
	}
}
