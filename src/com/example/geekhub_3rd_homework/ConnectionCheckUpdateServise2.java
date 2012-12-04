package com.example.geekhub_3rd_homework;



import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class ConnectionCheckUpdateServise2  extends Service {

  final String LOG_TAG = "myLog";

  MyBinder binder = new MyBinder();

  Timer timer;
  TimerTask tTask;
  long interval = 10000;

  public void onCreate() {
    super.onCreate();
    Log.d(LOG_TAG, "ConnectionCheckUpdateServise2 onCreate");
    timer = new Timer(){@Override
    public void cancel() {
    	// TODO Auto-generated method stub
    	super.cancel();
    	Log.d(LOG_TAG, "Cancel TimER");
    	
    }};
    schedule();
  }

  void schedule() {
	 
    if (tTask != null) tTask.cancel();
    if (interval > 0) {
      tTask = new TimerTask() {
        public void run() {
       //   Log.d(LOG_TAG, "run ConnectionCheckUpdateServise2 task");
          sendBroadcast(new Intent(MainActivity.CONNECTION_CHECK_UPDATER));
        }
      };
      timer.schedule(tTask, 5000, interval);
    }
  }

  long upInterval(long gap) {
    interval = interval + gap;
    schedule();
    return interval;
  }

  long downInterval(long gap) {
    interval = interval - gap;
    if (interval < 0) interval = 0;
    schedule();
    return interval;
  }

  public IBinder onBind(Intent arg0) {
    Log.d(LOG_TAG, "MyService onBind");
    return binder;
  }

  class MyBinder extends Binder {
	  ConnectionCheckUpdateServise2 getService() {
      return ConnectionCheckUpdateServise2.this;
    }
  }
  
  @Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		timer.cancel();
		
		Log.d(LOG_TAG, "ConnectionCheckUpdateServise2 onDestroy");
		
	}
}