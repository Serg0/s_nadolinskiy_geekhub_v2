package com.example.geekhub_3rd_homework;

import java.util.concurrent.TimeUnit;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ConnectionCheckUpdateServise extends Service {

	
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

	void timer()
	{
		new Thread(new Runnable() {
		      public void run() {
		        for (; ; ) {
		         try {
		        	 TimeUnit.SECONDS.sleep(5);
		        	sendBroadcast(new Intent(MainActivity.CONNECTION_CHECK_UPDATER));
		            TimeUnit.SECONDS.sleep(5);
		          } catch (InterruptedException e) {
		            e.printStackTrace();
		          }
		        }
		      
		      }
		    }).start();
	}
	
}
