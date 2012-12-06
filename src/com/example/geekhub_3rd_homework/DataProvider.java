package com.example.geekhub_3rd_homework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.NetworkOnMainThreadException;
import android.util.Log;

public class DataProvider extends Object {

	private static final String LOG_TAG = "myLog";
	static ArrayList<Article> array;
	static ArrayList<String> publishStringArray;
	static ArrayList<String> titleStringArray;
	static String ETag;
	static boolean ShowLikes = false;
	static Thread thread;

	public static Thread getThread() {
		return thread;
	}

	public static boolean isShowLikes() {
		return ShowLikes;
	}

	public static void switchShowLikes() {
		ShowLikes = !ShowLikes;
	}

	public static int getContentPos() {
		if (isShowLikes()) {
			return contentPosLikes;
		} else {
			return contentPos;
		}
	}

	public static void setContentPos(int position) {
		if (isShowLikes()) {
			contentPosLikes = position;
		} else {
			contentPos = position;
		}
	}

	static int contentPos = -1;
	static int contentPosLikes = -1;

	public static boolean isOnline() {
		Context context = MainActivity.getAppContext();
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	public static ArrayList<Article> getFeed()
			throws NetworkOnMainThreadException {
		long time = System.currentTimeMillis();
		Log.d(LOG_TAG, "Starting to download " + time);
		/*Thread thread = new Thread() {
			public void run() {*/
				try {

					URL url = new URL(
							"http://android-developers.blogspot.com/feeds/posts/default?alt=json");
					HttpURLConnection con = (HttpURLConnection) url
							.openConnection();
					Log.d(LOG_TAG, "ETag  is "
							+ con.getHeaderField("ETag").toString());
					if ((ETag == null)
							|| (!con.getHeaderField("ETag").toString()
									.equals(ETag))) {
						array = readStream(con.getInputStream());
						ETag = con.getHeaderField("ETag").toString();
						Log.d(LOG_TAG, "Reload DATA from SERVER");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			/*}
		};
		thread.start();
		try {thread.join();
		} catch (InterruptedException e) {e.printStackTrace();
		}
		Log.d(LOG_TAG, "Download finished"
	+ (System.currentTimeMillis() - time));*/
		return array;

	}


	private static ArrayList<Article> readStream(InputStream in)
			throws JSONException {
		final ArrayList<Article> localArray = new ArrayList<Article>();
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = null;
 
		try {
			reader = new BufferedReader(new InputStreamReader(in));
			String line = "";

			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			String result = sb.toString();
			JSONObject jObject = new JSONObject(result);
			JSONObject feed = jObject.getJSONObject("feed");
			JSONArray jArray = feed.getJSONArray("entry");

			for (int i = 0; i < jArray.length(); i++) {
				JSONObject oneObject = jArray.getJSONObject(i);

				String updated = oneObject.getJSONObject("updated").getString("$t");
				String title = oneObject.getJSONObject("title").getString("$t");
				String content = oneObject.getJSONObject("content").getString("$t");
				String published = oneObject.getJSONObject("published").getString("$t");
				String id = oneObject.getJSONObject("id").getString("$t");
				
				Article article = new Article(title, content, published,
						updated, id);

				localArray.add(article);

			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return localArray;
	}

	public static List<Article> getContent() throws SQLException,
			java.sql.SQLException {
		if (isShowLikes()) {
			return HelperFactory.GetHelper().getArticleDAO().getAllArticle();
		} else {
			return getFeed();
		}

	}

	public static ArrayList<Article> getContentArray() throws SQLException,
			java.sql.SQLException {

		ArrayList<Article> BuffArray = new ArrayList<Article>();
		for (Article v : getContent()) {
			BuffArray.add(v);
			Log.d(LOG_TAG, "Converting title " + v.getTitle());
		}
		return BuffArray;
	}
	public static ArrayList<String> getTitlesFromDB(ArrayList<Article> list)
			throws SQLException, java.sql.SQLException {
		Log.d(LOG_TAG, "new titles ");
		ArrayList<String> titleStringArray = new ArrayList<String>();
		for (Article v : list) {
			titleStringArray.add(v.getTitle());
		}
		return titleStringArray;

	}

	public static ArrayList<String> getPublishDatesFromDB(
			ArrayList<Article> list) throws SQLException, java.sql.SQLException {
		Log.d(LOG_TAG, "new publish dates ");
		ArrayList<String> publishStringArray = new ArrayList<String>();
		for (Article v : list) {
			publishStringArray.add(v.getPublished());
		}
		return publishStringArray;

	}
	
	public static void getIdsFromDB() throws SQLException, java.sql.SQLException {
		Log.d(LOG_TAG, "new publish dates ");
		
		for (Article v : HelperFactory.GetHelper().getArticleDAO().getAllArticle()) {
//			Log.d("ID", "Id of likes " + v.getId());
			Log.d("ID", "Name of likes " + v.getTitle());
		}

	}

}
