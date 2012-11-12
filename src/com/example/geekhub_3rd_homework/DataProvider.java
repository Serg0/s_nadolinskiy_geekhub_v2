package com.example.geekhub_3rd_homework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataProvider {

	static ArrayList<Article> array;
	
	public static ArrayList<Article> getConnection(){
	
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
	return array;
     
	
}

	private static ArrayList<Article> readStream(InputStream in) throws JSONException {
		// TODO Auto-generated method stub
    	StringBuilder sb = new StringBuilder();
    	BufferedReader reader = null;
    	ArrayList<Article> localArray = new ArrayList<Article>();
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
    	        localArray.add(new Article (title, content));
    	        
    	       
    	    }
    	  
    	    
    	  } catch (IOException e) {
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
    	  
    	  return localArray;

    }
}
