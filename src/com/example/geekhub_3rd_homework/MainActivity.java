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

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

public class MainActivity extends FragmentActivity {
	FragmentTransaction fTrans;
	TitlesFragment tFragment;
	//Content array[];
	ArrayList<Content> array = new ArrayList<Content>();
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
       // array[] = null;
        
        try {
        	  URL url = new URL("http://android-developers.blogspot.com/feeds/posts/default?alt=json");
        	  HttpURLConnection con = (HttpURLConnection) url.openConnection();
        	  array = readStream(con.getInputStream());
        	 // con.getContent();
        	  //Toast.makeText(getBaseContext(), (CharSequence) con.getContent() , Toast.LENGTH_SHORT).show();
        	
        	  } catch (Exception e) {
        	  e.printStackTrace();
        	 
        	}
        tFragment = new TitlesFragment(array);
        
        fTrans = getSupportFragmentManager().beginTransaction();
         fTrans.add(R.id.frgmCont, tFragment);
                 fTrans.commit();  
		//Toast.makeText(getBaseContext(), res , Toast.LENGTH_SHORT).show();
        
    }

    private ArrayList<Content> readStream(InputStream in) throws JSONException {
		// TODO Auto-generated method stub
    	StringBuilder sb = new StringBuilder();
    	BufferedReader reader = null;
    	ArrayList<Content> localArray = new ArrayList<Content>();
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
    	    //jArray.get(index)
   /* 	    TextView text = (TextView) findViewById(R.id.textView1);
     text.setMovementMethod(new ScrollingMovementMethod()); */
    	  //  text.setText("afa");
     
    	    for (int i=0; i < jArray.length(); i++)
    	    {
    	        JSONObject oneObject = jArray.getJSONObject(i);
    	        // Pulling items from the array
    	        //String oneObjectsItem = oneObject.getString("title");
    	        JSONObject oneObjectsItem = oneObject.getJSONObject("title");
    	        String title = oneObjectsItem.getString("$t");
    	      //  oneObject.getJSONObject(name)
    	     //   String oneObjectsItem2 = oneObject.getString("content");
    	        JSONObject oneObjectsItem2 = oneObject.getJSONObject("content");
      	        String content = oneObjectsItem2.getString("$t");
    	        
      	      localArray.add(new Content (title, content));
    	        
    	    /*    text.append(title);
    	        text.append("\n--------------------------------\n");*/
    	       
        	    
    	       
    	    }
    	  
    	    //text.setText("12412");
    	    
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
        super.onConfigurationChanged(newConfig);
        }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }



}
