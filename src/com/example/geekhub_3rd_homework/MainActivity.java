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

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

public class MainActivity extends FragmentActivity {
	FragmentTransaction fragmentTransaction;
	TitlesFragment titlesFragment;
	ArrayList<Article> array = null;

	
	public static boolean isTablet(Context context) {
	    boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
	    boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
	    return (xlarge || large);
	}
	
	
	
    @SuppressWarnings("static-access")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread thread = new Thread() {
    	    public void run() {
    	    	try {
                    
    	    			URL url = new URL("http://android-developers.blogspot.com/feeds/posts/default?alt=json");
                 	 	HttpURLConnection con = (HttpURLConnection) url.openConnection();
                 		long i = 0; 
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
        
       // Content.getConnection();
   
    	
        titlesFragment = new TitlesFragment(array);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        
        if (isTablet(this)) {
        	fragmentTransaction.add(R.id.frgmCont3, titlesFragment);
        }else
        {
        	
        	fragmentTransaction.add(R.id.frgmCont, titlesFragment);
        }
        
        fragmentTransaction.commit();  
                 
              
    }

    private ArrayList<Article> readStream(InputStream in) throws JSONException {
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
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    	setContentView(R.layout.activity_main);
        super.onConfigurationChanged(newConfig);
        
        }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }



}
