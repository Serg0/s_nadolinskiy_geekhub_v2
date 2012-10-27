package com.example.geekhub_2d_homework;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends FragmentActivity { 
	 FragmentTransaction fTrans;
	  Fragment1 frag1;
	  Fragment2 frag2;
	
	 
	public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
     frag1 = new Fragment1();
    
 fTrans = getSupportFragmentManager().beginTransaction();
    fTrans.add(R.id.frgmCont, frag1);
    fTrans.commit();  
  
}

@Override
public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_main, menu);
    return true;
}
}
