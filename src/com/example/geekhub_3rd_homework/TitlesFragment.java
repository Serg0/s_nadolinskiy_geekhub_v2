package com.example.geekhub_3rd_homework;

import java.util.ArrayList;

import org.apache.http.impl.conn.tsccm.WaitingThread;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Dialog;


import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.MenuItem;
//import android.view.Menu;

public class TitlesFragment extends SherlockFragment {
	ArrayList<Article> aLocal;
	com.actionbarsherlock.app.ActionBar actionBar;
	FragmentTransaction fragmentTransaction;
	DetailsFragment detailsFragment;
	final static String LOG_TAG = "myLog";
	public static  RowAdapter adapter;
	public ListView lvMain;
	private static TitlesFragment Instance;
	ProgressDialog pd;
	 private Handler mHandler = new Handler(Looper.getMainLooper());
	 
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
	
	public static class BroadcastListener extends BroadcastReceiver {

	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	
	    	Log.d(LOG_TAG, "TitleFragment Recived Broadcast befor all cheks");
	        if (Instance == null || intent == null || Instance.getView() == null) {
	            return;
	        }
	        
	        String message;
			
	        String action = intent.getAction();
	        if (action.equals(MainActivity.CONNECTION_CHECK_UPDATER)) {
	        	Log.d(LOG_TAG, "TitleFragment Recived Broadcast ");
	        	//ListView lv = (ListView) Instance.getView().findViewById(R.id.listView1);
	        	if (DataProvider.isOnline()){
	        		message = "Connection is UP";
	        		Log.d(LOG_TAG, message);
//	        		lv.invalidate();
//	        		lv.setAdapter(adapter);
//	        		lv.refreshDrawableState();
	        		try {
						Instance.refreshListView(adapter);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (java.sql.SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		
	        		
	        	} else 
	        	{
	        		message = "Connection is DOWN";
	        		Log.d(LOG_TAG, message);
//	        		lv.invalidate();
//	        		lv.setAdapter(null);
//	        		lv.refreshDrawableState();
	        		try {
						Instance.refreshListView(null);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (java.sql.SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		
	        		
	        	}
	        	Log.d(LOG_TAG, message);
	        //	Toast.makeText(MainActivity.getAppContext().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	        	
	        
	        }
	    }
	}
	private void refreshListView(RowAdapter _adapter) throws SQLException, java.sql.SQLException {
		// TODO Auto-generated method stub
		Log.d(LOG_TAG, "refreshListView");
		
		if (_adapter == null){Toast.makeText(MainActivity.getAppContext().getApplicationContext(), "Connection is DOWN!", Toast.LENGTH_SHORT).show();
		lvMain.invalidate();
		lvMain.setAdapter(_adapter);
		lvMain.refreshDrawableState();
		Log.d(LOG_TAG, "after refreshListView");}else
		{
		adapter = new RowAdapter(getActivity(),DataProvider.getContent());
		}

	}
	
    public void onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu, com.actionbarsherlock.view.MenuInflater inflater) {
    	// TODO Auto-generated method stub

    	
    	inflater.inflate(R.menu.action_bar_menu, menu);
    	MenuItem item = (menu.findItem(R.id.ShowItem));
    	
    	if ((DataProvider.isShowLikes())){item.setTitle("SHOW ALL ARTICLES");}else{item.setTitle("SHOW ALL LIKES");}
    	
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    case R.id.ShowItem:
    // Handle fragment menu item
    	
    	DataProvider.switchShowLikes();
		DataProvider.setContentPos(0);
		adapter.notifyDataSetChanged();
		lvMain.invalidate();
		lvMain.setAdapter(null);
		lvMain.refreshDrawableState();
		//adapter.notifyDataSetChanged();
		try {
			adapter = new RowAdapter(getActivity(),DataProvider.getContent());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (java.sql.SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lvMain.setAdapter(adapter);
//		item.sett
//		TextView text = (TextView) this.findViewById(R.id.ShowItem);
		if ((DataProvider.isShowLikes())){
			item.setTitle("SHOW ALL ARTICLES");
			Toast.makeText(MainActivity.getAppContext().getApplicationContext(), "All Likes showed!!", Toast.LENGTH_SHORT).show();
		}else{
			item.setTitle("SHOW ALL LIKES");
			Toast.makeText(MainActivity.getAppContext().getApplicationContext(), "All ARTICLES showed!!", Toast.LENGTH_SHORT).show();
			}
//    	adapter.notifyDataSetChanged();
//    	lvMain.refreshDrawableState();
//    	lvMain.invalidate();
    //	this.onConfigurationChanged(null);
    //	Toast.makeText(MainActivity.getAppContext().getApplicationContext(), "@&&&&&&&&&&", Toast.LENGTH_LONG).show();
    return true;
    default:
    // Not one of ours. Perform default menu processing
    return super.onOptionsItemSelected(item);
    }
    }
    
    
	@Override
	public void onActivityCreated(Bundle savedInstanceState)  {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	
@Override
public void onResume() {
	// TODO Auto-generated method stub
	super.onResume();


		Log.d(LOG_TAG, "TitlesFragment onActivityCreated ");
		Instance = this;
		
		lvMain = (ListView) getView().findViewById(R.id.listView1);
		
		 
		 actionBar=getSherlockActivity().getSupportActionBar();
//	      pd.setButton(Dialog.BUTTON_POSITIVE, "OK",  new View().OnClickListener() {
//	        public void onClick(DialogInterface dialog, int which) {
//	        }
//	      });
	      
	      
	 
	      Log.d(LOG_TAG, "TitlesFragment pd.show(); ");
	      Thread thread = new Thread() {
	   	    public void run() {	 
	   	     mHandler.post(new Runnable() {
	             public void run() {
	   	     pd = new ProgressDialog(getActivity());
	   	      pd.setTitle("Data from server");
	   	      pd.setMessage("Loading...");

		      pd.show();
		try {
			
			//ArrayList<Article> article = (ArrayList<Article>) HelperFactory.GetHelper().getArticleDAO().getAllArticle();
		    adapter = new RowAdapter(getActivity(),DataProvider.getContent());
		 //   DataProvider.getContentArray();
			Log.d(LOG_TAG, "in time of GetDB ");
		//	setHasOptionsMenu(true);
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
		
	//	lvMain.refreshDrawableState();
//		Thread thread
		
		lvMain.setAdapter(adapter);

		pd.dismiss();
	             }
	         });
	   	 }
	  	 };
	  	 thread.start();
//	  	 try {
//	  		 thread.join();
//	 		} catch (InterruptedException e) {
//	 			// TODO Auto-generated catch block
//	 			e.printStackTrace();
//	 		}
	  	
	  	  Log.d(LOG_TAG, "TitlesFragment pd.dismiss();");
		//Log.d(LOG_TAG, "After SetAdapter ");
		// myProgressBar.setVisibility(View.INVISIBLE);
//		 ProgressBar myProgressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar1);
//	        
//         myProgressBar.setVisibility(View.INVISIBLE);  
		
		if (!(DataProvider.getContentPos() == -1)&&(MainActivity.isTablet(getActivity()))&&(MainActivity.isLandscape(getActivity()))&&(DataProvider.getContentPos() != -1)){
			
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
