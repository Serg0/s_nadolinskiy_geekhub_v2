package com.example.geekhub_3rd_homework;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.MenuItem;

public class TitlesFragment extends SherlockFragment {
	ArrayList<Article> aLocal;
	com.actionbarsherlock.app.ActionBar actionBar;
	FragmentTransaction fragmentTransaction;
	DetailsFragment detailsFragment;
	final static String LOG_TAG = "myLog";
	public static RowAdapter adapter;
	public ListView lvMain;
	private static TitlesFragment Instance;
	public static TitlesFragment getInstance() {
		return Instance;
	}

	ProgressDialog pd;
	private Handler mHandler = new Handler(Looper.getMainLooper());
	private boolean restore;
	private List<Article> articles;
	static boolean isOnlinePrevious = true;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_titles, null);

	}

	public static class BroadcastListener extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			Log.d(LOG_TAG, "TitleFragment Recived Broadcast befor all cheks");
			if (Instance == null || intent == null
					|| Instance.getView() == null) {
				return;
			}
			String message;
			String action = intent.getAction();
			if (action.equals(MainActivity.CONNECTION_CHECK_UPDATER)) {
				Log.d(LOG_TAG, "TitleFragment Recived Broadcast ");
				if (DataProvider.isOnline()) {
					message = "Connection is UP";
					try {
						isOnlinePrevious = true;
						Instance.refreshListView(adapter);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (java.sql.SQLException e) {
						e.printStackTrace();
					}

				} else {
					message = "Connection is DOWN";
					try {
						isOnlinePrevious = false;
						Instance.refreshListView(null);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (java.sql.SQLException e) {
						e.printStackTrace();
					}

				}
				Log.d(LOG_TAG, message);

			}
		}
	}

	public void refreshListView(RowAdapter _adapter) throws SQLException,
			java.sql.SQLException {
		Log.d(LOG_TAG, "refreshListView");

		if (_adapter == null) {
			lvMain.invalidate();
			lvMain.setAdapter(_adapter);
			lvMain.refreshDrawableState();
			Log.d(LOG_TAG, "after refreshListView");
		} else {
			adapter = new RowAdapter(getActivity(), DataProvider.getContent());
		}
		

	}
	public void refreshListView() throws SQLException,
	java.sql.SQLException {
		adapter = new RowAdapter(getActivity(), DataProvider.getContent());
		adapter.notifyDataSetChanged();
		lvMain.invalidate();
		lvMain.setAdapter(adapter);
		lvMain.refreshDrawableState();
	
}

	public void onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu,
			com.actionbarsherlock.view.MenuInflater inflater) {
		inflater.inflate(R.menu.action_bar_menu, menu);
		MenuItem item = (menu.findItem(R.id.ShowItem));

		if ((DataProvider.isShowLikes())) {
			item.setTitle("SHOW ALL ARTICLES");
		} else {
			item.setTitle("SHOW ALL LIKES");
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.ShowItem:
			
			try {
				DataProvider.getIdsFromDB();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (java.sql.SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// Handle fragment menu item
			if ((DataProvider.isOnline())
					&& (isOnlinePrevious == DataProvider.isOnline())) {
				DataProvider.switchShowLikes();
				DataProvider.setContentPos(0);
				adapter.notifyDataSetChanged();
				lvMain.invalidate();
				lvMain.setAdapter(null);
				lvMain.refreshDrawableState();

				adapter.notifyDataSetChanged();
				try {
					adapter = new RowAdapter(getActivity(),
							DataProvider.getContent());
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (java.sql.SQLException e) {
					e.printStackTrace();
				}

				lvMain.setAdapter(adapter);

				if ((DataProvider.isShowLikes())) {
					item.setTitle("SHOW ALL ARTICLES");
					Toast.makeText(
							MainActivity.getAppContext()
									.getApplicationContext(),
							"All Likes showed!!", Toast.LENGTH_SHORT).show();
				} else {
					item.setTitle("SHOW ALL LIKES");
					Toast.makeText(
							MainActivity.getAppContext()
									.getApplicationContext(),
							"All ARTICLES showed!!", Toast.LENGTH_SHORT).show();
				}
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Log.d(LOG_TAG, "TitlesFragment onActivityCreated ");
		Instance = this;

		lvMain = (ListView) getView().findViewById(R.id.listView1);

		actionBar = getSherlockActivity().getSupportActionBar();

		loadData();
	}

	private void loadData() {
		showLoadingIndicatior();
		new Thread(new Runnable() {

			public void run() {
				try {
					articles = DataProvider.getContent();
					updateUi(articles);
					hideLoadingIndicator();
				} catch (SQLException e) {
					e.printStackTrace();
				} /*catch (java.sql.SQLException e) {
					e.printStackTrace();
				}*/ catch (java.sql.SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void showLoadingIndicatior() {
		pd = new ProgressDialog(getActivity());
		pd.setTitle("Data from server");
		pd.setMessage("Loading...");
		pd.show();
	}

	private void hideLoadingIndicator() {
		pd.dismiss();
	}

	private void updateUi(final List<Article> articles) {
		getActivity().runOnUiThread(new Runnable() {

			public void run() {
//				if (adapter == null) {
					adapter = new RowAdapter(getActivity(), articles);
//				}

				if (lvMain.getAdapter() == null) {
					lvMain.setAdapter(adapter);
				} else {
					adapter.notifyDataSetChanged();
				}

			}
		});

		lvMain.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				DataProvider.setContentPos(position);
				Log.d(LOG_TAG, "Position is " + Integer.toString(position));
				Log.d(LOG_TAG,
						"Position is "
								+ Integer.toString(DataProvider.getContentPos()));

				if ((MainActivity.isTablet(getActivity()))
						&& (MainActivity.isLandscape(getActivity()))) {

					detailsFragment = new DetailsFragment();
					fragmentTransaction = getActivity()
							.getSupportFragmentManager().beginTransaction();
					fragmentTransaction
							.replace(R.id.frgmCont4, detailsFragment);
					fragmentTransaction.commit();

				} else {
					Intent intent = new Intent(getActivity(),
							SecondActivity.class);
					Log.d(LOG_TAG, "Creating new activity"
							+ getActivity().getClass());

					// intent.putExtra("contentPos", position);
					// getActivity().getIntent().putExtra("contentPos",
					// position);
					startActivity(intent);

				}
			}

		});
	}

}
