package com.example.geekhub_3rd_homework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationHandler;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class DataProvider  extends Object{

	private static final String LOG_TAG = "DataProvider";

	public static boolean isOnline() {
		Context context = MainActivity.getAppContext();
	    ConnectivityManager cm =
	        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	} 
	static ArrayList<Article> array;
	
	public static ArrayList<Article> getFeed(){
	
		if(array == null){
			// Handler handler = new Handler();
			
	 Thread thread = new Thread() {
 	    public void run() {
 	    	try {
                 
 	    			URL url = new URL("http://android-developers.blogspot.com/feeds/posts/default?alt=json");
              	 	HttpURLConnection con = (HttpURLConnection) url.openConnection();
              		array = readStream(con.getInputStream());

              }
             	catch (Exception e) {
                  	  e.printStackTrace();
                  }

 	    }
 	 };
 	 thread.start();
 	 try {
 		 thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
	return array;
     
	
}
	
	//HelperFactory.GetHelper().getMyDBcontentDAO().create(new MyDBContent(detailsFragment.getContentPos()));
	
	
	private static ArrayList<Article> readStream(InputStream in) throws JSONException, SQLException, java.sql.SQLException {
		// TODO Auto-generated method stub
		final ArrayList<Article> localArray = new ArrayList<Article>();
    	 
//    	Thread thread2 = new Thread() {
//     	    public void run() {
     	    	StringBuilder sb = new StringBuilder();
     	    	BufferedReader reader = null;
     	    	
    	try {
    	    reader = new BufferedReader(new InputStreamReader(in));
    	    String line = "";
   
    	    while ((line = reader.readLine()) != null)
    	    {
    	        sb.append(line);
    	    }
    	    String result = sb.toString();
    	    
    	    
    	    JSONObject jObject = new JSONObject(result);
    	    JSONObject feed = jObject.getJSONObject("feed");
    	    JSONArray jArray = feed.getJSONArray("entry");
     
    	    for (int i=0; i < jArray.length(); i++)
    	    {
    	        JSONObject oneObject = jArray.getJSONObject(i);
    	        JSONObject oneObjectsItem = oneObject.getJSONObject("title");
    	        String title = oneObjectsItem.getString("$t");
    	        JSONObject oneObjectsItem2 = oneObject.getJSONObject("content");
      	        String content = oneObjectsItem2.getString("$t");
      	        JSONObject oneObjectsItem3 = oneObject.getJSONObject("published");
    	        String published = oneObjectsItem3.getString("$t");
    	        Article article = new Article (title, content, published);
    	        Log.d(LOG_TAG, "Adding new Article "+article.getTitle());
    	        HelperFactory.GetHelper().getArticleDAO().create(article);
    	        
    	        localArray.add(article);
    	        
    	    }
    	    
    	  } catch (IOException e) {
    	    e.printStackTrace();
    	  } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (java.sql.SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
    	    if (reader != null) {
    	      try {
    	        reader.close();
    	      } catch (IOException e) {
    	        e.printStackTrace();
    	        }
    	    }
    	  }
//     		 };
//    	};
//    	thread2.start();
//     	 	 try {
//     	 		 thread2.join();
//     			} catch (InterruptedException e) {
//     				// TODO Auto-generated catch block
//     				e.printStackTrace();
//     			}
     			
    	  return localArray;

    }
	
	public static ArrayList<String> getTitles(){
		ArrayList<String> titleStringArray = new ArrayList<String>();
		
		for (Article v:getFeed())
	    {
			titleStringArray.add(v.getTitle());
	    }
		return titleStringArray;
	
		
	}
	public static ArrayList<String> getPublishDates(){
		ArrayList<String> publishStringArray = new ArrayList<String>();
		
		for (Article v:getFeed())
	    {
			publishStringArray.add(v.getPublished());
	    }
		return publishStringArray;
	
		
	}
	//HelperFactory.GetHelper().getMyDBcontentDAO().getAllMyDBcontent().get(iter.nextIndex()).getTitle().toString()
	public static ArrayList<String> getTitlesFromDB() throws SQLException, java.sql.SQLException{
		ArrayList<String> titleStringArray = new ArrayList<String>();
		
		for (Article v:HelperFactory.GetHelper().getArticleDAO().getAllArticle())
	    {
			titleStringArray.add(v.getTitle());
			Log.d(LOG_TAG, "new title "+v.getTitle());
	    }
		
		return titleStringArray;
		
		
	}
	public static ArrayList<String> getPublishDatesFromDB() throws SQLException, java.sql.SQLException{
		ArrayList<String> publishStringArray = new ArrayList<String>();
		
		for (Article v:HelperFactory.GetHelper().getArticleDAO().getAllArticle())
	    {
			publishStringArray.add(v.getPublished());
			Log.d(LOG_TAG, "new publish date "+v.getPublished());
	    }
		return publishStringArray;
	
		
	}
	
}
