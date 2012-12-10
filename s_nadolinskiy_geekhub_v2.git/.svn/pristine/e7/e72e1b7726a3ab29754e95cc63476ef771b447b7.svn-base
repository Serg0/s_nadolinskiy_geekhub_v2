package com.example.geekhub_2d_homework;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Fragment1 extends Fragment {
	String[] names = { "alpha", "bravo", "charlie", "delta", "eco"};

	
	  @Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	      Bundle savedInstanceState) {
	    
	
	 
	    return inflater.inflate(R.layout.fragment1, null);
	    
	  }
	
	  @Override
	    public void onActivityCreated(Bundle savedInstanceState) {
		  ListView lvMain = (ListView) getView().findViewById(R.id.listView1);
		    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, names);
		    lvMain.setAdapter(adapter);
		 //   Toast.makeText(getActivity(), "test", Toast.LENGTH_SHORT).show();
		    
		    lvMain.setOnItemClickListener(new OnItemClickListener() {
		        public void onItemClick(AdapterView<?> parent, View view,
		                int position, long id) {
		        	//startActivity(new Intent(getActivity(), SecondActivity.class));
		        	
		        	
		        	Intent intent = new Intent(getActivity(), SecondActivity.class); 
		            intent.putExtra("fname", parent.getAdapter().getItem(position).toString());
		           
		            startActivity(intent);
		        	//Toast.makeText(getActivity(), parent.getAdapter().getItem(position).toString(), Toast.LENGTH_SHORT).show();
		        	
		        }

				
		    });

				
	        super.onActivityCreated(savedInstanceState);
	        
	    }

	
}
