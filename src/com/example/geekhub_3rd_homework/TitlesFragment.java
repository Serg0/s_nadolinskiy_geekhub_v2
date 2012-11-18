package com.example.geekhub_3rd_homework;




import java.util.ArrayList;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class TitlesFragment extends Fragment {
	ArrayList<Article> aLocal;
//	ArrayList<String> titleStringArray = new ArrayList<String>();
	//ArrayList<String> contentStringArray = new ArrayList<String>();
	
	FragmentTransaction fragmentTransaction;
	DetailsFragment detailsFragment;
	final String LOG_TAG = "myLogs";
//	public TitlesFragment(ArrayList<Article> array) {
//		// TODO Auto-generated constructor stub
//		aLocal = array;
//		
//		
//    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		return inflater.inflate(R.layout.fragment_titles, null);
		
	}
	
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//    	
//    	  
//        super.onConfigurationChanged(newConfig);
//        
//        detailsFragment = new DetailsFragment();
//        fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.frgmCont4, detailsFragment);
//        fragmentTransaction.commit();
//        }
    
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		ListView lvMain = (ListView) getView().findViewById(R.id.listView1);
		 RowAdapter adapter = new RowAdapter(getActivity(),  DataProvider.getTitles(), DataProvider.getPublishDates());
		lvMain.setAdapter(adapter);
		
		if ((savedInstanceState != null)&&(MainActivity.isTablet(getActivity()))&&(MainActivity.isLandscape(getActivity()))){
			
		    detailsFragment = new DetailsFragment();
            fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frgmCont4, detailsFragment);
            fragmentTransaction.commit();
            Log.d(LOG_TAG, "Creating new activity"+ getActivity().getClass());
            return;
         //   getActivity().getIntent().putExtra("contentPos",savedInstanceState.getInt("contentPos"));
		
		}
		
	    lvMain.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view,
	                int position, long id) {
	        	
	        	
//	        	Toast.makeText(getActivity().getBaseContext(), DataProvider.getFeed().get(position).getPublished(), Toast.LENGTH_LONG).show();
	        	
	        	if ((MainActivity.isTablet(getActivity()))&&(MainActivity.isLandscape(getActivity()))) {
//					Intent intent = getActivity().getIntent();
//					intent.putExtra("content", DataProvider.getFeed().get(position).getContent());
					//getActivity().getIntent().putExtra("content", DataProvider.getFeed().get(position).getContent());
					getActivity().getIntent().putExtra("contentPos", position);
		           
		           
		            detailsFragment = new DetailsFragment();
		            fragmentTransaction = getFragmentManager().beginTransaction();
		            fragmentTransaction.replace(R.id.frgmCont4, detailsFragment);
		            fragmentTransaction.commit();
		            
				}else
				{
	        		Intent intent = new Intent(getActivity(), SecondActivity.class); 
	        		Log.d(LOG_TAG, "Creating new activity"+ getActivity().getClass());
	        		
	        		intent.putExtra("contentPos", position);
	        		getActivity().getIntent().putExtra("contentPos", position);
		            startActivity(intent);
		            
				}	
	        }

			
	    });
		
	}

}
