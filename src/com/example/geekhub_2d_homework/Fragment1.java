package com.example.geekhub_2d_homework;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ArrayAdapter;
public class Fragment1 extends Fragment {
	String[] names = { "1", "2", "3", "4", "5", "6","7", "8", "9", "0", "11" };

	
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
		    
	        super.onActivityCreated(savedInstanceState);
	    }
}
