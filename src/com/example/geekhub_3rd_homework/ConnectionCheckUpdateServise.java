package com.example.geekhub_3rd_homework;

import java.util.concurrent.TimeUnit;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ConnectionCheckUpdateServise extends Service {

	
	private static final String LOG_TAG = "ConnectionCheckUpdateServise";
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		timer();
		
		return super.onStartCommand(intent, flags, startId);
		
		
		
	}
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
@Override
public void onDestroy() {
	// TODO Auto-generated method stub
	
	Log.d(LOG_TAG, " Destroy Service");
	super.onDestroy();
}
	void timer()
	{
		new Thread(new Runnable() {
		      public void run() {
		        while (true) { //while true!!!
		         try {
		        	 TimeUnit.SECONDS.sleep(5);
		        	sendBroadcast(new Intent(MainActivity.CONNECTION_CHECK_UPDATER));
		            TimeUnit.SECONDS.sleep(5);
		          } catch (InterruptedException e) {
		            e.printStackTrace();
		          }
		        }
		    	  //-------------------------------
//		    	  Binder mBinder = new Binder();
//		    	  Log.d(LOG_TAG, "new Binder();");
//				//mBinder;
//		    	  
//		    	     long endTime = System.currentTimeMillis() + 5*1000;            
//		    	        while (System.currentTimeMillis() < endTime) 
//		    	        {                
//		    	            synchronized (mBinder) 
//		    	            {                    
//		    	                try 
//		    	                {                        
//		    	                    mBinder.wait(endTime - System.currentTimeMillis());      
//		    	                } 
//		    	                catch (Exception e) 
//		    	                {                    
//
//		    	                }                
//		    	            }            
//		    	        }    
//		      }
		      }   }).start();
	}
	
}
