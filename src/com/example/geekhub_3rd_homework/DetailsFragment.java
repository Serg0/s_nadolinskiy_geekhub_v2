package com.example.geekhub_3rd_homework;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.actionbarsherlock.view.MenuItem;

public class DetailsFragment extends SherlockDialogFragment {
//	String content2;
//	String content;
	int contentPos;
	
	final String LOG_TAG = "myLogs";
	
	public DetailsFragment() {
		// TODO Auto-generated constructor stub
	}
	
	public int getContentPos(){return contentPos;}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	return inflater.inflate(R.layout.fragment_details, null);
}

@Override
public void onSaveInstanceState(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onSaveInstanceState(savedInstanceState);
	//savedInstanceState.putInt("contentPos", contentPos);
//	Log.d(LOG_TAG, "savedInstanceState.putIntcontentPos "+ contentPos);
	
}
public void onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu, com.actionbarsherlock.view.MenuInflater inflater) {
	// TODO Auto-generated method stub

	
	inflater.inflate(R.menu.action_bar_menu2, menu);
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
switch (item.getItemId()) {
case R.id.AddLike:
// Handle fragment menu item
	String id;
	try {
		Toast.makeText(MainActivity.getAppContext().getApplicationContext(), "Like added!!", Toast.LENGTH_LONG).show();
		id = HelperFactory.GetHelper().getArticleDAO().getAllArticle().get(DataProvider.getContentPos()).getId();
		HelperFactory.GetHelper().getArticleDAO().setLike(id);
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (java.sql.SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
return true;
default:
// Not one of ours. Perform default menu processing
return super.onOptionsItemSelected(item);
}
}


@SuppressWarnings("deprecation")
@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		 //------------------------------------------------
		contentPos = DataProvider.getContentPos();
		setHasOptionsMenu(true);
		 //------------------------------------------------
		if (savedInstanceState != null){
		   // contentPos = savedInstanceState.getInt("contentPos");
			
			
			if (MainActivity.isTablet(getActivity())){
					
				if(!MainActivity.isLandscape(getActivity())){
			    Intent intent = new Intent(this.getActivity(), SecondActivity.class); 
        		//intent.putExtra("contentPos", contentPos);
        		
        		startActivity(intent);
        	return;
				}else
				
				{
				
					
					if (getView().getContext().getClass().equals(SecondActivity.class)) {
						
						Log.d(LOG_TAG, " fINALIZED " + this.getActivity().getClass());
						this.getActivity().finish();
						}
					Log.d(LOG_TAG, " View in " + getView().getContext().getClass().toString());
				
				}
				
			}

			Log.d(LOG_TAG, " Clearing instance"+savedInstanceState.getInt("contentPos"));
			savedInstanceState.remove("contentPos");
			
		    
		}else{
//		 Intent intent = this.getActivity().getIntent();
//		    contentPos = intent.getIntExtra("contentPos", 0);
		}

		WebView webview = (WebView) getActivity().findViewById(R.id.WebView1);
    String query = null;
    
		try {
			//query = URLEncoder.encode(DataProvider.getFeed().get(contentPos).getContent(), "utf-8").replaceAll("\\+"," ");
			query = URLEncoder.encode(DataProvider.getContent().get(contentPos).getContent(), "utf-8").replaceAll("\\+"," ");
			String title ="<h2>" + DataProvider.getContent().get(contentPos).getTitle() + "</h2><br>";
			String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
			 webview.getSettings().setBuiltInZoomControls(true);
			 webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
			 webview.loadData(header+title+query, "text/html; charset=UTF-8", null);
			 
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (java.sql.SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

 
 

	}
//public void onSaveInstanceState(Bundle outState) {
//	// TODO Auto-generated method stub
//	super.onSaveInstanceState(outState);
//	
//	outState.putString("content", content);
//}
//public void  onRestoreInstanceState(Bundle savedInstanceState) {
//    super.onRestoreInstanceState(savedInstanceState);
//    content = savedInstanceState.getString("content");
//  }

}
