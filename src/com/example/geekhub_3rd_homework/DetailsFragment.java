package com.example.geekhub_3rd_homework;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;

import android.content.Intent;
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
import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.Session.StatusCallback;
import com.facebook.model.GraphUser;

public class DetailsFragment extends SherlockFragment {
	int contentPos;
	final String LOG_TAG = "myLogs";
	private StatusCallback mStatusCallback =  new SessionStatusCallback();
	public static Article article;
	 private GraphUser mUser;
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
//			String id;

			try {
//				id = DataProvider.getContent().get(contentPos).getId()
//						.toString();
				if (isLiked(article)) {
					HelperFactory.GetHelper().getArticleDAO().deleteLike(article.getId());
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
					HelperFactory
							.GetHelper()
							.getArticleDAO()
							.create(article);
					item.setTitle("DELETE LIKE");
				}
			} catch (SQLException e) {
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			}

			return true;
		case R.id.share_button:
/*			 Intent shareIntent = ShareCompat.IntentBuilder.from(this.getActivity())
	          .setType("text/plain")
	          .setText(article.getLink())
	          .getIntent()
	          .setPackage("com.google.android.apps.plus");

	      startActivity(shareIntent);*/
			Toast.makeText(getActivity(), "Feachure is under construction!", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.menuToTweet:
			Intent intent = new Intent(getActivity(),
					TweetToTwitterActivity.class);
			intent.putExtra("article link", article.getLink());
						getActivity().startActivity(intent);
						return true;
		case R.id.menuToShareOnFacebook:
			 Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
		        Session session = Session.getActiveSession();
		        if (session == null) {
		           /* if (savedInstanceState != null) {
		                session = Session.restoreSession(getActivity(), null, mStatusCallback, savedInstanceState);
		            }*/
		            if (session == null) {
		                session = new Session.Builder(getActivity()).setApplicationId(getActivity().getString(R.string.app_id)).build();
		            }
		            Session.setActiveSession(session);
		            if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
		                session.openForRead(new Session.OpenRequest(this).setCallback(mStatusCallback));
		            }
		        }
		        addFacebookConnection();

		        shareOnFacebook(article.getTitle(),article.getLink());
						return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public boolean isLiked(Article article) throws SQLException, java.sql.SQLException {

		/*return HelperFactory
				.GetHelper()
				.getArticleDAO()
				.isLiked(DataProvider.getContent().get(DataProvider.getContentPos()).getId()
								.toString());*/
		return HelperFactory
				.GetHelper()
				.getArticleDAO()
				.isLiked(article);
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		try {
			article = DataProvider.getContent().get(DataProvider.getContentPos());
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
//					Log.i("Blundell.TweetToTwitterActivity", " article.getLink() =" + article.getTitle());
//					intent.putExtra("link", article.getLink());
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
			query = URLEncoder.encode(
					article.getContent(),
					"utf-8").replaceAll("\\+", " ");
			String title = "<h2>"
					+ article.getTitle()
					+ "</h2><br>";
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
	
    private void makeMeRequest(final Session session) {
        Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
            @Override
            public void onCompleted(GraphUser user, Response response) {
                // If the response is successful
                if (session == Session.getActiveSession()) {
                    if (user != null) {
                        mUser = user;
                    }
                }

//                updateConnectionsState();

                if (response.getError() != null) {
                    // Handle errors, will do so later.
                    Log.d(LOG_TAG, response.getError().getErrorMessage(), response.getError().getException());
                }
            }
        });
    }
    private void addFacebookConnection() {
        Session session = Session.getActiveSession();
        if (!session.isOpened() && !session.isClosed()) {
            session.openForPublish(new Session.OpenRequest(this).setCallback(mStatusCallback).setPermissions(Arrays.asList("publish_actions")));
        } else {
            Session.openActiveSession(getActivity(), this, true, mStatusCallback);
        }
    }
    
	 private class SessionStatusCallback implements Session.StatusCallback {
	        @Override
	        public void call(Session session, SessionState state, Exception exception) {
	            if (state.isOpened()) {
	                makeMeRequest(session);
	            } else {
//	                updateConnectionsState();
	            }
	        }
}
	 private void shareOnFacebook(String message, String link) {
	        Session session = Session.getActiveSession();

	        if (session != null) {
	            Bundle postParams = new Bundle();
	            postParams.putString("message", message);
	            postParams.putString("link", link);

	            Request.Callback callback= new Request.Callback() {
	                public void onCompleted(Response response) {
	                    FacebookRequestError error = response.getError();
	                    if (error != null) {
	                        Toast.makeText(getActivity(), error.getErrorMessage(), Toast.LENGTH_SHORT).show();
	                        Log.d(LOG_TAG, "Errog code  = " + error.getErrorCode()+ " error.getErrorMessage() " + error.getErrorMessage());
	                    } else {
	                        Toast.makeText(getActivity(), "Facebook status updated successfully", Toast.LENGTH_SHORT).show();
	                    }
	                }
	            };

	            Request request = new Request(session, "me/feed", postParams, HttpMethod.POST, callback);

	            RequestAsyncTask task = new RequestAsyncTask(request);
	            task.execute();
	        }
	    }
	   /* @Override
	    public void onStart() {
	        super.onStart();
	        Session.getActiveSession().addCallback(mStatusCallback);
	    }

	    @Override
	    public void onStop() {
	        super.onStop();
	        Session.getActiveSession().removeCallback(mStatusCallback);
	    }*/
	    @Override
	    public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        Session.getActiveSession().onActivityResult(getActivity(), requestCode, resultCode, data);
	    }
}
