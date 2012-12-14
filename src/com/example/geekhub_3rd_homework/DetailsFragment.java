package com.example.geekhub_3rd_homework;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.plus.PlusClient;

public class DetailsFragment extends SherlockFragment  {
	int contentPos;
	final String LOG_TAG = "myLogs";
	public static Article article;
	
		
	public DetailsFragment() {
	}

	public int getContentPos() {
		return contentPos;
	}

		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_details, null);
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
	}

	public void onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu,
			com.actionbarsherlock.view.MenuInflater inflater) {
		inflater.inflate(R.menu.action_bar_menu2, menu);

		MenuItem item = menu.findItem(R.id.AddLike);

		try {
			if (isLiked(article)) {
				item.setTitle("DELETE LIKE");
			} else {
				item.setTitle("ADD LIKE");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.AddLike:
			// String id;

			try {
				// id = DataProvider.getContent().get(contentPos).getId()
				// .toString();
				if (isLiked(article)) {
					HelperFactory.GetHelper().getArticleDAO()
							.deleteLike(article.getId());
					Toast.makeText(
							MainActivity.getAppContext()
									.getApplicationContext(), "Like deleted!!",
							Toast.LENGTH_LONG).show();
					TitlesFragment.getInstance().refreshListView();
					item.setTitle("ADD LIKE");

				} else {
					Toast.makeText(
							MainActivity.getAppContext()
									.getApplicationContext(), "Like added!!",
							Toast.LENGTH_LONG).show();
					HelperFactory.GetHelper().getArticleDAO().create(article);
					item.setTitle("DELETE LIKE");
				}
			} catch (SQLException e) {
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			}

			return true;
		case R.id.share_button:

			MainActivity.onShare(article);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public boolean isLiked(Article article) throws SQLException,
			java.sql.SQLException {

		/*
		 * return HelperFactory .GetHelper() .getArticleDAO()
		 * .isLiked(DataProvider
		 * .getContent().get(DataProvider.getContentPos()).getId() .toString());
		 */
		return HelperFactory.GetHelper().getArticleDAO().isLiked(article);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		try {
			article = DataProvider.getContent().get(
					DataProvider.getContentPos());
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (java.sql.SQLException e1) {
			e1.printStackTrace();
		}
		setHasOptionsMenu(true);
		if (savedInstanceState != null) {
			if (MainActivity.isTablet(getActivity())) {
				if (!MainActivity.isLandscape(getActivity())) {
					Intent intent = new Intent(this.getActivity(),
							SecondActivity.class);
					startActivity(intent);
					return;
				} else {
					if (getView().getContext().getClass()
							.equals(SecondActivity.class)) {
						this.getActivity().finish();
					}
				}

			}
		} else {
		}
		WebView webview = (WebView) getActivity().findViewById(R.id.WebView1);
		String query = null;

		try {
			query = URLEncoder.encode(article.getContent(), "utf-8")
					.replaceAll("\\+", " ");
			String title = "<h2>" + article.getTitle() + "</h2><br>";
			String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
			webview.getSettings().setBuiltInZoomControls(true);
			webview.getSettings().setLayoutAlgorithm(
					LayoutAlgorithm.SINGLE_COLUMN);
			webview.loadData(header + title + query,
					"text/html; charset=UTF-8", null);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	
}
