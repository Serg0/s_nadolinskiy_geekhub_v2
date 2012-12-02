package com.example.geekhub_3rd_homework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationHandler;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	static ArrayList<Article> array;
	static ArrayList<String> publishStringArray;
	static ArrayList<String> titleStringArray;
	
	static boolean ShowLikes = false;
	
	public static boolean isShowLikes() {
		return ShowLikes;
	}

	public static void switchShowLikes() {
		ShowLikes = !ShowLikes;
	}

	public static int getContentPos() {
		if (isShowLikes()){return contentPosLikes;}else{return contentPos;}
	}

	

	public static void setContentPos(int position) {
		if (isShowLikes()){contentPosLikes = position;}else{contentPos= position;}
	}
	
	
	static int contentPos = -1;
	static int contentPosLikes = -1;
	
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
	
	public static  void getFeed(){
	
		if(array == null){
			// Handler handler = new Handler();
			Log.d(LOG_TAG, "Array is null");
	 Thread thread = new Thread() {
 	    public void run() {
 	    	try {
                 
 	    			URL url = new URL("http://android-developers.blogspot.com/feeds/posts/default?alt=json");
              	 	HttpURLConnection con = (HttpURLConnection) url.openConnection();
              	Log.d(LOG_TAG, "ETag From DB is " + HelperFactory.GetHelper().getMyDBPropertiesDAO().getAllMyDBProperties().size()); 
//              	Log.d(LOG_TAG, "ETag is " + con.getHeaderField("ETag").toString());
//              	Log.d(LOG_TAG, "Last-Modified is " + con.getHeaderField("Last-Modified").toString()); 
//              	Log.d(LOG_TAG, "Host is "		+ con.getHeaderField("Host").toString());
             // 	 Log.d(LOG_TAG, "myDBProperties is " + con.getHeaderField("ETag").toString());
              	int MyDBPropertiesSize = HelperFactory.GetHelper().getMyDBPropertiesDAO().getAllMyDBProperties().size();
              	 	if ((MyDBPropertiesSize == 0)||(!HelperFactory.GetHelper().getMyDBPropertiesDAO().getAllMyDBProperties().get(MyDBPropertiesSize).getETag().equals(con.getHeaderField("ETag"))))
              	 	{
              	 	            		//array = readStream(con.getInputStream());
              	 		readStream(con.getInputStream());
              	 		HelperFactory.GetHelper().getMyDBPropertiesDAO().getAllMyDBProperties().clear();
              	 		MyDBProperties myDBProperties = new MyDBProperties(con.getHeaderField("ETag").toString(),con.getHeaderField("Last-Modified").toString(),url.toString());
              	 		HelperFactory.GetHelper().getMyDBPropertiesDAO().create(myDBProperties);
              	 		
              	 	}
              	 	//HelperFactory.GetHelper()..
              	 		
              		
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
		
	//return array;
     
	
}
	
	//HelperFactory.GetHelper().getMyDBcontentDAO().create(new MyDBContent(detailsFragment.getContentPos()));
	
	
	private static  void readStream(InputStream in) throws JSONException{
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
    	        
    	        
    	        JSONObject oneObjectsItem = oneObject.getJSONObject("updated");
    	        String updated = oneObjectsItem.getString("$t");
    	     //   if (updated<) TO-DO check if updated is newer than last modifyied else continue
    	        
    	        oneObjectsItem = oneObject.getJSONObject("title");
    	        String title = oneObjectsItem.getString("$t");
    	        oneObjectsItem = oneObject.getJSONObject("content");
      	        String content = oneObjectsItem.getString("$t");
      	        oneObjectsItem = oneObject.getJSONObject("published");
    	        String published = oneObjectsItem.getString("$t");

    	        oneObjectsItem = oneObject.getJSONObject("id");
    	        String id = oneObjectsItem.getString("$t");
    	        
    	        Article article = new Article (title, content, published, updated, id);
    	        Log.d(LOG_TAG, "Adding new Article "+article.getTitle());
    	        HelperFactory.GetHelper().getArticleDAO().create(article);
    	        
    	        //localArray.add(article);
    	        
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

    }
	

	public static List<Article> getContent() throws SQLException, java.sql.SQLException{
		
		if (isShowLikes()){
			return  HelperFactory.GetHelper().getArticleDAO().getAllLikes();
		}else
		{
			return  HelperFactory.GetHelper().getArticleDAO().getAllArticle();
		}
		
	}
	
public static ArrayList<Article> getContentArray() throws SQLException, java.sql.SQLException{
	
	ArrayList<Article> BuffArray = new ArrayList<Article>();
	for (Article v:getContent())
    {
		BuffArray.add(v);
		Log.d(LOG_TAG, "Converting title "+v.getTitle());
    }
	//}
	
	
	return BuffArray;
				
	}
	
	
	
	
//	}
	//HelperFactory.GetHelper().getMyDBcontentDAO().getAllMyDBcontent().get(iter.nextIndex()).getTitle().toString()
	public static ArrayList<String> getTitlesFromDB(ArrayList<Article> list) throws SQLException, java.sql.SQLException{
		//if (titleStringArray == null){
		Log.d(LOG_TAG, "new titles ");
		ArrayList<String> titleStringArray = new ArrayList<String>();
		for (Article v:list)
	    {
			titleStringArray.add(v.getTitle());
			//Log.d(LOG_TAG, "new title "+v.getTitle());
	    }
		//}
		return titleStringArray;
		
		
	}
	public static ArrayList<String> getPublishDatesFromDB(ArrayList<Article> list) throws SQLException, java.sql.SQLException{
		
		//if (publishStringArray == null){
		Log.d(LOG_TAG, "new publish dates ");
			ArrayList<String> publishStringArray = new ArrayList<String>();
		for (Article v:list)
	    {
			publishStringArray.add(v.getPublished());
		//	Log.d(LOG_TAG, "new publish date "+v.getPublished());
	    }
		
	//	}
		return publishStringArray;
	
		
	}
public static   void getAllDatesFromDB(List<String> publishStringArray) throws SQLException, java.sql.SQLException{
		
		//if (publishStringArray == null){
		Log.d(LOG_TAG, "new publish dates ");
			//List<String> publishStringArray = new ArrayList<String>();
		for (String v:publishStringArray)
	    {
			
			Log.d(LOG_TAG, "string  "+v);
	    }
}
		
		public static  void getLikesFromDB(List<Article> list){
			
			//if (publishStringArray == null){
			Log.d(LOG_TAG, "new Likes  ");
			//	ArrayList<String> publishStringArray = new ArrayList<String>();
			for (Article v:list)
		    {
				//(v.getPublished());
			Log.d(LOG_TAG, "new Likes  "+v.getTitle().toString()+" "+v.getId().toString()+" "+v.getIsLiked().toString());
		    }
			
		//	}
			//return publishStringArray;
		
			
		}
	//	}
	//	return publishStringArray;
	
		
	
	
}
