package com.example.geekhub_3rd_homework;

import java.util.ListIterator;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuInflater;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends SherlockFragmentActivity {

	FragmentTransaction fragmentTransaction;
	DetailsFragment detailsFragment;
	//static boolean active = false;
	final String LOG_TAG = "myLogs";
		com.actionbarsherlock.app.ActionBar actionBar;
	private static SecondActivity Instance;
	


//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// TODO Auto-generated method stub
//		
//		com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
//		inflater.inflate(R.menu.activity_main, menu);
//		return super.onCreateOptionsMenu(menu);
//	}
//	
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// TODO Auto-generated method stub
//		switch(item.getItemId())
//		{
//		case R.id.addLike:
//			
//			//MyDBContent arg0 = new MyDBContent(detailsFragment.getContentPos());
//			try {
//				
//				
//			
//				String id = HelperFactory.GetHelper().getArticleDAO().getAllArticle().get(DataProvider.getContentPos()).getId();
//				HelperFactory.GetHelper().getArticleDAO().setLike(id);
//				Toast.makeText(getApplicationContext(), "Like added!!"+id, Toast.LENGTH_LONG).show();
//				//	HelperFactory.GetHelper().getMyDBcontentDAO().create(new MyDBProperties(detailsFragment.getContentPos()));
//				//HelperFactory.GetHelper().getMyDBcontentDAO();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (java.sql.SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return true;
//		case R.id.showAllLikes:
//			DataProvider.switchShowLikes();
//			DataProvider.setContentPos(0);
//			//Intent intent = getIntent();
//			//MainActivity.getAppContext().getApplicationContext().
//			//MainActivity.getInstance().finish();
//			//startActivity(getIntent());
////			Intent refresh = new Intent(this, SecondActivity.class);
////			startActivity(refresh);
////			this.finish();
//			
//		//	MainActivity.onRestartPublic();
//			onRestart();
//			//startActivity(intent);
//			//MainActivity.getAppContext().notifyAll();
//			//this.invalidateOptionsMenu();
//			//this.recreate();
////			try {
////				//Toast.makeText(getApplicationContext(), "All likes showed!!"+HelperFactory.GetHelper().getArticleDAO().getAllLikes().size(), Toast.LENGTH_LONG).show();
////			} catch (SQLException e1) {
////				// TODO Auto-generated catch block
////				e1.printStackTrace();
////			} catch (java.sql.SQLException e1) {
////				// TODO Auto-generated catch block
////				e1.printStackTrace();
////			}
//			
//			try {
//				
//				 ListIterator<Article> iter = HelperFactory.GetHelper().getArticleDAO().getAllLikes().listIterator();
//				 while (iter.hasNext()){
//				Log.d(LOG_TAG,"likes  "+ HelperFactory.GetHelper().getArticleDAO().getAllLikes().get(iter.nextIndex()).getTitle().toString());
//				iter.next();
//				 
//				 }
//				 DataProvider.getLikesFromDB(HelperFactory.GetHelper().getArticleDAO().getAllLikes());
//				 
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (java.sql.SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			return true;
//			
//		}
//		return super.onOptionsItemSelected(item);
//	}
//	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 super.onCreate(savedInstanceState);
		setContentView(R.layout.second_activity);
	    Instance = this;
	    ((TextView) Instance.findViewById(R.id.textView1)).setText(MainActivity.message);
	    
	    actionBar=getSupportActionBar();
		detailsFragment = new DetailsFragment();
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.frgmCont2, detailsFragment);
		fragmentTransaction.commit();  
                 
	   
	}
	
	public static class BroadcastListener extends BroadcastReceiver {

	    @Override
	    public void onReceive(Context context, Intent intent) {
	        if (Instance == null || intent == null) {
	            return;
	        }
	        
	      //  if (DataProvider.isOnline()){MainActivity.message = "Connection is UP";} else {MainActivity.message = "Connection is DOWN";}

	        String action = intent.getAction();
	        if (action.equals(MainActivity.CONNECTION_CHECK_UPDATER)) {
	            ((TextView) Instance.findViewById(R.id.textView1)).setText(MainActivity.message);
	        }
	    }
	}

	
}
