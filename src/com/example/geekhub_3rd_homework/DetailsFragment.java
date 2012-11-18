package com.example.geekhub_3rd_homework;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.Toast;

public class DetailsFragment extends Fragment {
//	String content2;
//	String content;
	int contentPos;
	
	final String LOG_TAG = "myLogs";
	
	public DetailsFragment() {
		// TODO Auto-generated constructor stub
	}

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
	savedInstanceState.putInt("contentPos", contentPos);
	System.out.println("contentPos in saveInstance" + contentPos);
}

@SuppressWarnings("deprecation")
@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.d(LOG_TAG, "befor Destroy");
		 if (savedInstanceState != null){
			    contentPos = savedInstanceState.getInt("contentPos");
			    Log.d(LOG_TAG, "onSaveInstanceState"+contentPos);
			    if (MainActivity.isTablet(getActivity())&&(!MainActivity.isLandscape(getActivity()))){
			    Intent intent = new Intent(this.getActivity(), SecondActivity.class); 
        		intent.putExtra("contentPos", contentPos);
        		startActivity(intent);
//        		try {
//        			getActivity().finish();
//        			
//					Log.d(LOG_TAG, "fINALIZED");
//					return;
//				} catch (Throwable e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
        		return;
			    }else 
			    	if (MainActivity.isTablet(getActivity())&&(MainActivity.isLandscape(getActivity()))){
//			    	try {
//	        			getActivity().finish();
//	        			
//						Log.d(LOG_TAG, "fINALIZED");
//						return;
//					} catch (Throwable e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
			    	
        		//this.getActivity().getFragmentManager().getFragment(getArguments(), getTag());
			    
    }
			   
			  }else{
				  Log.d(LOG_TAG, "NON SAVED INSTANCE");
				  Intent intent = this.getActivity().getIntent();
				    contentPos = intent.getIntExtra("contentPos", -1);
			  }

	    
		 Log.d(LOG_TAG, "after Destroy");
	    WebView webview = (WebView) getActivity().findViewById(R.id.WebView1);
    String query = null;
		try {
			query = URLEncoder.encode(DataProvider.getFeed().get(contentPos).getContent(), "utf-8").replaceAll("\\+"," ");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
String title ="<h2>" + DataProvider.getFeed().get(contentPos).getTitle() + "</h2><br>";
 String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
 webview.getSettings().setBuiltInZoomControls(true);
 webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
 webview.loadData(header+title+query, "text/html; charset=UTF-8", null);
 

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
