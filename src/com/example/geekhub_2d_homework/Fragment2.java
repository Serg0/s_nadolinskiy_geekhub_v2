package com.example.geekhub_2d_homework;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment2 extends Fragment {
	 FragmentTransaction fTrans2;
	  Fragment2 frag2;
	  Fragment3 frag3;
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment2, null);
    
    
    
    
  }
  
  @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		
		Button b = (Button) getActivity().findViewById(R.id.button1);
		Intent intent = getActivity().getIntent();
	    
	    String fName = intent.getStringExtra("fname");
	    TextView text = (TextView) getView().findViewById(R.id.textView1);
		text.setText(fName);
		//Toast.makeText(getView().getContext(), "first", Toast.LENGTH_SHORT).show(); 
		
	    b.setOnClickListener(new OnClickListener() {
	    	
			
			public void onClick(View v) {
				frag3 = new Fragment3();
			    
				 fTrans2 = getFragmentManager().beginTransaction();
				    fTrans2.replace(R.id.frgmCont2, frag3);
				    fTrans2.addToBackStack(null);
				    fTrans2.commit();  
					
				
				//Toast.makeText(getView().getContext(), "slgkldsgdsj", Toast.LENGTH_SHORT).show();
				
			}
		});
	
	}
 //@Override
public void onResume() {
	// TODO Auto-generated method stub
	
	
	super.onResume();
}

public void onBackPressed() {
	// TODO Auto-generated method stub

	Intent intent3 = getActivity().getIntent();
	String fName = intent3.getStringExtra("fname2");
    TextView text = (TextView) getView().findViewById(R.id.textView1);
	text.setText(fName);
	Toast.makeText(getView().getContext(), fName, Toast.LENGTH_SHORT).show();
	
}

}

