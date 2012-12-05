package com.example.geekhub_3rd_homework;



import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class ConnectionCheckUpdateServise2  extends Service {

  final String LOG_TAG = "myLog";

  MyBinder binder = new MyBinder();
   int NOTIFICATION = R.string.update_service_started;

   NotificationManager mNM;
  
  Timer timer;
  TimerTask tTask;
  long interval = 10000;

  public void onCreate() {
    super.onCreate();
    mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    Log.d(LOG_TAG, "ConnectionCheckUpdateServise2 onCreate");
    timer = new Timer(){@Override
    public void cancel() {
    	// TODO Auto-generated method stub
    	super.cancel();
    	Log.d(LOG_TAG, "Cancel TimER");
    	
    }};
    schedule();
    showNotification();
  }
  
  private void showNotification() {
      // In this sample, we'll use the same text for the ticker and the expanded notification
	  Log.d(LOG_TAG, "getText showNotification()");
      CharSequence text = getText(R.string.update_service_started);

      // Set the icon, scrolling text and timestamp
      Log.d(LOG_TAG, "new Notification showNotification()");
      Notification notification = new Notification(android.R.drawable.ic_dialog_info, text,
              System.currentTimeMillis());

      // The PendingIntent to launch our activity if the user selects this notification
      Log.d(LOG_TAG, "PendingIntent");
      PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
              new Intent(this, MainActivity.class), 0);
      
      Log.d(LOG_TAG, "notification.setLatestEventInfo");
      // Set the info for the views that show in the notification panel.
      notification.setLatestEventInfo(this, getText(R.string.update_service_label),
              text, contentIntent);
     // notification.
      // Send the notification.
      Log.d(LOG_TAG, "mNM.notify");
      mNM.notify(NOTIFICATION, notification);
      Log.d(LOG_TAG, "AFTER mNM.notify");
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
		 mNM.cancel(NOTIFICATION);

		Log.d(LOG_TAG, "ConnectionCheckUpdateServise2 onDestroy");
		
	}
}