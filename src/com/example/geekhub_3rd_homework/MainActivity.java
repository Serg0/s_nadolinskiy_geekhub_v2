package com.example.geekhub_3rd_homework;

import java.util.ArrayList;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.database.SQLException;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ShareCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.plus.PlusClient;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class MainActivity extends SherlockFragmentActivity implements
		ConnectionCallbacks, OnConnectionFailedListener {
	FragmentTransaction fragmentTransaction;
	TitlesFragment titlesFragment;
	ArrayList<Article> array = null;
	private static Context context;
	private static MainActivity Instance;
	static String message = null;
	final public static String CONNECTION_CHECK_UPDATER = "com.example.geekhub_3rd_homework.CONNECTION_CHECK_UPDATER";
	private static final String LOG_TAG = "myLog";

	//G+Share
	private static final int REQUEST_CODE_RESOLVE_ERR = 9000;
	private ProgressDialog mConnectionProgressDialog;
	private PlusClient mPlusClient;
	private ConnectionResult mConnectionResult;
	
	//G+Share//
	
	boolean bound = false;
	ServiceConnection sConn;
	Intent intent;
	ConnectionCheckUpdateService myService;
	long interval;
	private DetailsFragment detailsFragment;
	@Override
	public void onStart() {
		super.onStart();
		
//		gPlusSignIn();
		
	}
	
	public static void onShare(Article article){
		getInstance().gPlusSignIn();
		 Intent shareIntent = ShareCompat.IntentBuilder.from(getInstance().getThis())
		          .setType("text/plain")
		          .setText(article.getLink())
		          .getIntent()
		          .setPackage("com.google.android.gms.plus");

		 getInstance().startActivity(shareIntent);
		
	}
	public void gPlusSignIn(){
		if (!mPlusClient.isConnected()) {
	        if (mConnectionResult == null) {
	            mConnectionProgressDialog.show();
	        } else {
	            try {
	                mConnectionResult.startResolutionForResult(this, REQUEST_CODE_RESOLVE_ERR);
	            } catch (SendIntentException e) {
	                // Try connecting again.
	                mConnectionResult = null;
	                mPlusClient.connect();
	            }
	        }
	    }
		
	}
	
	
	@Override
	public void onStop() {
	        super.onStop();
	        

	 }
	
	
	
	private boolean isMyServiceRunning() {
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if (ConnectionCheckUpdateService.class.getName().equals(
					service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	public static boolean isTablet(Context context) {
		boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
		boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
		return (xlarge || large);
	}

	public static boolean isLandscape(Context context) {

		return (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
	}

	public static Context getAppContext() {
		return MainActivity.context;
	}

	public MainActivity getThis() {
		return this;
	}

	public void onRestartPublic() {

		Log.d(LOG_TAG, " Trying to Restart");
		this.invalidateOptionsMenu();
		Log.d(LOG_TAG, " After to Restart");

	}

	
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d(LOG_TAG, "onCreate Main Activity");
		MainActivity.context = getApplicationContext();
		setInstance(this);
		((TextView) Instance.findViewById(R.id.textView1)).setText(message);

		mPlusClient = new PlusClient(this, this, this, Scopes.PLUS_PROFILE);
		// Progress bar to be displayed if the connection failure is not
		// resolved.
		mConnectionProgressDialog = new ProgressDialog(this);
		mConnectionProgressDialog.setMessage("Signing in...");
		mPlusClient.connect();
		gPlusSignIn();
		
		
		intent = new Intent(this, ConnectionCheckUpdateService.class);

		sConn = new ServiceConnection() {

			public void onServiceConnected(ComponentName name, IBinder binder) {
				Log.d(LOG_TAG, "MainActivity onServiceConnected");
				myService = ((ConnectionCheckUpdateService.MyBinder) binder)
						.getService();
				bound = true;
			}

			public void onServiceDisconnected(ComponentName name) {
				Log.d(LOG_TAG, "MainActivity onServiceDisconnected");
				bound = false;
			}

		};

		bindService(intent, sConn, Context.BIND_AUTO_CREATE);
		startService(intent);
		HelperFactory.SetHelper(getApplicationContext());

		if (DataProvider.isOnline()) {
			titlesFragment = new TitlesFragment();
			titlesFragment.setHasOptionsMenu(true);
			fragmentTransaction = getSupportFragmentManager()
					.beginTransaction();
			// Tablet land
			if ((isTablet(this)) && (isLandscape(this))) {
				fragmentTransaction.replace(R.id.frgmCont3, titlesFragment);
			} else // Tablet port
			if ((isTablet(this)) && (!isLandscape(this))) {
				fragmentTransaction.replace(R.id.frgmCont5, titlesFragment);
			} else // phone
			{
				fragmentTransaction.replace(R.id.frgmCont, titlesFragment);
			}

			fragmentTransaction.commit();

		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("No internet connection")
					.setCancelable(false)
					.setPositiveButton("Retry",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									finish();
									startActivity(getIntent());
								}
							})
					.setNegativeButton("Try from DB",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
								}
							});
			builder.create().show();
		}
		if (!(DataProvider.getContentPos() == -1) && (isTablet(this))
				&& (isLandscape(this))) {

			detailsFragment = new DetailsFragment();
			fragmentTransaction = getSupportFragmentManager()
					.beginTransaction();
			fragmentTransaction.replace(R.id.frgmCont4, detailsFragment);
			fragmentTransaction.commit();
		}
	}

	public static MainActivity getInstance() {
		return Instance;
	}

	public void setInstance(MainActivity instance) {
		Instance = instance;
	}

	@Override
	public void onBackPressed() {
		if (DataProvider.isShowLikes()) {
			try {
				DataProvider.switchShowLikes();
				titlesFragment.switchTitles();
				titlesFragment.refreshListView();

			} catch (SQLException e) {
				e.printStackTrace();
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		} else {

			super.onBackPressed();
		}
	}

	public static class BroadcastListener extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (Instance == null || intent == null) {
				return;
			}

			if (DataProvider.isOnline()) {
				message = "Connection is UP";
			} else {
				message = "Connection is DOWN";
			}

			String action = intent.getAction();
			if (action.equals(CONNECTION_CHECK_UPDATER)) {
				((TextView) Instance.findViewById(R.id.textView1))
						.setText(message);
			}
		}
	}

	@Override
	public void finish() {
		try {
			OnFinished();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		super.finish();

	}

	private void OnFinished() throws SQLException, java.sql.SQLException {
		HelperFactory.GetHelper().getArticleDAO().getAllArticle().clear();
		HelperFactory.ReleaseHelper();
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (!bound)
			return;
		unbindService(sConn);
		stopService(new Intent(this, ConnectionCheckUpdateService.class));
		Log.d(LOG_TAG, " End & UnBind Service");
		bound = false;
		mPlusClient.disconnect();
		Log.d(LOG_TAG, "mPlusClient.disconnect()");
		
	}

	public void onActivityResult(int requestCode, int responseCode,
			Intent intent) {
		if (requestCode == REQUEST_CODE_RESOLVE_ERR
				&& responseCode == RESULT_OK) {
			mConnectionResult = null;
			mPlusClient.connect();
		}
	}

	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub
		if (mConnectionProgressDialog.isShowing()) {
			// The user clicked the sign-in button already.
			// Dismiss the progress dialog and start to resolve connection
			// errors.
			mConnectionProgressDialog.dismiss();

			if (result.hasResolution()) {
				try {
					result.startResolutionForResult(this,
							REQUEST_CODE_RESOLVE_ERR);
				} catch (SendIntentException e) {
					mPlusClient.connect();
				}
			}
		}

		// Save the intent so that we can start an activity when the user clicks
		// the sign-in button..
		mConnectionResult = result;

	}

	public void onConnected() {
		String accountName = mPlusClient.getAccountName();
		Log.d(LOG_TAG, "connected");
		Toast.makeText(this, accountName + " is connected.", Toast.LENGTH_LONG)
				.show();
		mConnectionProgressDialog.dismiss();
	}

	public void onDisconnected() {
		Log.d(LOG_TAG, "disconnected");

	}

}