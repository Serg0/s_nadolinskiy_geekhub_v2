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
	int contentPos;
	final String LOG_TAG = "myLogs";
	
	public DetailsFragment() {
	}
	
	public int getContentPos(){return contentPos;}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	return inflater.inflate(R.layout.fragment_details, null);
}

@Override
public void onSaveInstanceState(Bundle savedInstanceState) {
	super.onSaveInstanceState(savedInstanceState);
}
public void onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu, com.actionbarsherlock.view.MenuInflater inflater) {
	inflater.inflate(R.menu.action_bar_menu2, menu);
	
	MenuItem item = (menu.findItem(R.id.AddLike));

	try {
		if (isLiked()) {
			item.setTitle("DELETE LIKE");
		} else {
			item.setTitle("ADD LIKE");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (java.sql.SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
switch (item.getItemId()) {
case R.id.AddLike:
	String id;
	
	try {
		id = DataProvider.getContent().get(contentPos).getId().toString();
		if (isLiked())
		{
			HelperFactory.GetHelper().getArticleDAO().deleteLike(id);
			Toast.makeText(MainActivity.getAppContext().getApplicationContext(), "Like deleted!!", Toast.LENGTH_LONG).show();
			TitlesFragment.getInstance().refreshListView();
			item.setTitle("ADD LIKE");
			
		}else
		{
		Toast.makeText(MainActivity.getAppContext().getApplicationContext(), "Like added!!", Toast.LENGTH_LONG).show();
		HelperFactory.GetHelper().getArticleDAO().create(DataProvider.getContent().get(DataProvider.getContentPos()));
		item.setTitle("DELETE LIKE");
		}
	} catch (SQLException e) {
	} catch (java.sql.SQLException e) {e.printStackTrace();}
	
	
return true;
default:
return super.onOptionsItemSelected(item);
}
}

public boolean isLiked() throws SQLException, java.sql.SQLException {
	
	return HelperFactory.GetHelper().getArticleDAO().isLiked(DataProvider.getContent().get(DataProvider.getContentPos()).getId().toString());
}
@SuppressWarnings("deprecation")
@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		contentPos = DataProvider.getContentPos();
		setHasOptionsMenu(true);
		if (savedInstanceState != null){
			if (MainActivity.isTablet(getActivity())){
				if(!MainActivity.isLandscape(getActivity())){
			    Intent intent = new Intent(this.getActivity(), SecondActivity.class); 
        		startActivity(intent);
        	return;
				}else
				{
					if (getView().getContext().getClass().equals(SecondActivity.class)) {
						this.getActivity().finish();
						}
				}
				
			}
		}else{
		}
		WebView webview = (WebView) getActivity().findViewById(R.id.WebView1);
    String query = null;
    
		try {
			query = URLEncoder.encode(DataProvider.getContent().get(contentPos).getContent(), "utf-8").replaceAll("\\+"," ");
			String title ="<h2>" + DataProvider.getContent().get(contentPos).getTitle() + "</h2><br>";
			String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
			 webview.getSettings().setBuiltInZoomControls(true);
			 webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
			 webview.loadData(header+title+query, "text/html; charset=UTF-8", null);
			 
		} catch (UnsupportedEncodingException e) {e.printStackTrace();
		} catch (SQLException e) {e.printStackTrace();
		} catch (java.sql.SQLException e) {e.printStackTrace();
		}
		
}
}
