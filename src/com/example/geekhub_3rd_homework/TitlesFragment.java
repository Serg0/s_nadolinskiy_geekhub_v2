package com.example.geekhub_3rd_homework;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug.IntToString;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class TitlesFragment extends Fragment {
	ArrayList<Article> aLocal;
	
	FragmentTransaction fragmentTransaction;
	DetailsFragment detailsFragment;
	final String LOG_TAG = "myLogs";

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
		Log.d(LOG_TAG, "Berofe getDB ");
		 RowAdapter adapter = null;
		
		try {
			
			//ArrayList<Article> article = (ArrayList<Article>) HelperFactory.GetHelper().getArticleDAO().getAllArticle();
		    adapter = new RowAdapter(getActivity(),DataProvider.getContent());
		    DataProvider.getContentArray();
			Log.d(LOG_TAG, "in time of GetDB ");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Log.d(LOG_TAG, "SQLException ");
			e.printStackTrace();
		} catch (java.sql.SQLException e) {
			// TODO Auto-generated catch block
			Log.d(LOG_TAG, "java.sql.SQLException ");
			e.printStackTrace();
		}
		Log.d(LOG_TAG, "After getDB ");
		lvMain.setAdapter(adapter);
		Log.d(LOG_TAG, "After SetAdapter ");
		// myProgressBar.setVisibility(View.INVISIBLE);
//		 ProgressBar myProgressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar1);
//	        
//         myProgressBar.setVisibility(View.INVISIBLE);  
		
		if ((savedInstanceState != null)&&(MainActivity.isTablet(getActivity()))&&(MainActivity.isLandscape(getActivity()))&&(DataProvider.getContentPos() != -1)){
			
		    detailsFragment = new DetailsFragment();
            fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frgmCont4, detailsFragment);
            fragmentTransaction.commit();
            Log.d(LOG_TAG, "Creating new frag in "+ getActivity().getClass()+" ");
         return;
         //   getActivity().getIntent().putExtra("contentPos",savedInstanceState.getInt("contentPos"));
		
		}
		
	    lvMain.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view,
	                int position, long id) {
	        	
	        	DataProvider.setContentPos(position);
	        	Log.d(LOG_TAG, "Position is "+ Integer.toString(position));
	        	Log.d(LOG_TAG, "Position is "+ Integer.toString(DataProvider.getContentPos()));
	        	
	        	
	        	//Toast.makeText(MainActivity.getAppContext().getApplicationContext(), Integer.toString(position), Toast.LENGTH_LONG).show();
	        	
	        	if ((MainActivity.isTablet(getActivity()))&&(MainActivity.isLandscape(getActivity()))) {
//					Intent intent = getActivity().getIntent();
//					intent.putExtra("content", DataProvider.getFeed().get(position).getContent());
					//getActivity().getIntent().putExtra("content", DataProvider.getFeed().get(position).getContent());
					//getActivity().getIntent().putExtra("contentPos", position);
		           
		           
		            detailsFragment = new DetailsFragment();
		            fragmentTransaction = getFragmentManager().beginTransaction();
		            fragmentTransaction.replace(R.id.frgmCont4, detailsFragment);
		            fragmentTransaction.commit();
		            
				}else
				{
	        		Intent intent = new Intent(getActivity(), SecondActivity.class); 
	        		Log.d(LOG_TAG, "Creating new activity"+ getActivity().getClass());
	        		
//	        		intent.putExtra("contentPos", position);
//	        		getActivity().getIntent().putExtra("contentPos", position);
		            startActivity(intent);
		            
				}	
	        }

			
	    });
		
	}

}
