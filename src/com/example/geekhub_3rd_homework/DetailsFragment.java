package com.example.geekhub_3rd_homework;

import java.net.URLEncoder;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

public class DetailsFragment extends Fragment {
	
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
public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    }

@SuppressWarnings("deprecation")
@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
//		WebView webview = new WebView(this);
//        setContentView(webview);
//        
		Intent intent = getActivity().getIntent();
	    String content = intent.getStringExtra("content");
	   // TextView text = (TextView) getActivity().findViewById(R.id.textView2);
	    WebView webview = (WebView) getActivity().findViewById(R.id.WebView1);
	//    text.setMovementMethod(new ScrollingMovementMethod()); 
	    webview.loadData(URLEncoder.encode(content).replaceAll("\\+"," "),"text/html", "UTF-8");
	   // webview.loadData(content, mimeType, encoding)
	  
		
	}
}
