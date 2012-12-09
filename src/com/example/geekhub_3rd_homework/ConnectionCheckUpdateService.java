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

public class ConnectionCheckUpdateService extends Service {

	final String LOG_TAG = "myLog";

	MyBinder binder = new MyBinder();
	int NOTIFICATION = R.string.update_service_started;

	NotificationManager mNM;

	Timer timer;
	TimerTask tTask;
	long interval = 15000;

	public void onCreate() {
		super.onCreate();
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		timer = new Timer() {
			@Override
			public void cancel() {
				// TODO Auto-generated method stub
				super.cancel();
				Log.d(LOG_TAG, "Cancel TimER");

			}
		};
		schedule();
		showNotification();
	}

	private void showNotification() {
		// In this sample, we'll use the same text for the ticker and the
		// expanded notification
		CharSequence text = getText(R.string.update_service_started);
		// Set the icon, scrolling text and timestamp
		Notification notification = new Notification(
				android.R.drawable.ic_dialog_info, text,
				System.currentTimeMillis());
		// The PendingIntent to launch our activity if the user selects this
		// notification
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		// Set the info for the views that show in the notification panel.
		notification.setLatestEventInfo(this,
				getText(R.string.update_service_label), text, contentIntent);
		mNM.notify(NOTIFICATION, notification);
		Log.d(LOG_TAG, "SDK_INT" + android.os.Build.VERSION.SDK_INT);
		Log.d(LOG_TAG, "RELEASE" + android.os.Build.VERSION.RELEASE);
		//
		// Notification notification = new Notification.Builder(this)
		// .setContentTitle(title)
	}

	void schedule() {

		if (tTask != null)
			tTask.cancel();
		if (interval > 0) {
			tTask = new TimerTask() {
				public void run() {
					sendBroadcast(new Intent(
							MainActivity.CONNECTION_CHECK_UPDATER));
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
		if (interval < 0)
			interval = 0;
		schedule();
		return interval;
	}

	public IBinder onBind(Intent arg0) {
		Log.d(LOG_TAG, "MyService onBind");
		return binder;
	}

	class MyBinder extends Binder {
		ConnectionCheckUpdateService getService() {
			return ConnectionCheckUpdateService.this;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		timer.cancel();
		mNM.cancel(NOTIFICATION);
		Log.d(LOG_TAG, "ConnectionCheckUpdateService onDestroy");

	}
}