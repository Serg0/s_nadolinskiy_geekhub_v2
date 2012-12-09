package com.example.geekhub_2d_homework;

import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment3 extends Fragment {

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment3, null);
    
    
  }
  
  @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		final Intent intent = getActivity().getIntent();
	    
	    String fName = intent.getStringExtra("fname");
	    TextView text = (TextView) getView().findViewById(R.id.textView1);
		text.setText(fName);
		
		
		Button b = (Button) getActivity().findViewById(R.id.button1);
		
	    
	   	    
	    b.setOnClickListener(new OnClickListener() {
	    	
			
			public void onClick(View v) {
				//Intent intent = new Intent(getActivity(), SecondActivity.class); 
				int randInt = new Random().nextInt(1000) + 1;
	            intent.putExtra("fname2", Integer.toString(randInt));
	            Random r=new Random();
	            int i1=(r.nextInt(80) +65);
                 
	            String fName = intent.getStringExtra("fname2");
	            
	           
	        	Toast.makeText(getView().getContext(), fName , Toast.LENGTH_SHORT).show();
				
				//Toast.makeText(getView().getContext(), "slgkldsgdsj", Toast.LENGTH_SHORT).show();
				
			}
		});
	    
		
	}
  
  
}