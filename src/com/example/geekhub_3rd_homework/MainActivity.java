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

        titlesFragment = new TitlesFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        
        if (isTablet(this)) {
        	fragmentTransaction.replace(R.id.frgmCont3, titlesFragment);
        }else
        {
        	fragmentTransaction.replace(R.id.frgmCont, titlesFragment);
        }
        
        fragmentTransaction.commit();  
              
    }


   
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }



}
