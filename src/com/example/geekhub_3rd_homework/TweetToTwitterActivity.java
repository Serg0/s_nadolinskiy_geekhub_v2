package com.example.geekhub_3rd_homework;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import twitter4j.AsyncTwitter;
import twitter4j.AsyncTwitterFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterAdapter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterListener;
import twitter4j.TwitterMethod;
import twitter4j.auth.AccessToken;
import twitter4j.auth.Authorization;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class TweetToTwitterActivity extends Activity {

	private static final String TAG = "Blundell.TweetToTwitterActivity";

	/** Name to store the users access token */
	private static  String PREF_ACCESS_TOKEN = "TWITTER_ACCESS_TOKEN";
	/** Name to store the users access token secret */
	private static  String PREF_ACCESS_TOKEN_SECRET =  "TWITTER_ACCESS_TOKEN_SECRET";
	/**
	 * Consumer Key generated when you registered your app at
	 * https://dev.twitter.com/apps/
	 */
	private static final String CONSUMER_KEY = "820j4TqRXnum214QQu3LQ";
	/**
	 * Consumer Secret generated when you registered your app at
	 * https://dev.twitter.com/apps/
	 */
	private static final String CONSUMER_SECRET = "rVTs6Iuz56Km6W2sHLUUnFAfnUGs1b46vtA37AVwIc"; // XXX
																								// Encode
																								// in
//	final public static String	CALLBACK_SCHEME = "x-latify-oauth-twitter";
//    final public static String	CALLBACK_URL = CALLBACK_SCHEME + ":///callback";
																						// your
//    public static final String CALLBACK_URL = "x-oauthflow-twitter://callback";											// app
	/**
	 * The url that Twitter will redirect to after a user log's in - this will
	 * be picked up by your app manifest and redirected into this activity
	 */
	 private static final String CALLBACK_URL =
	 "tweet-to-twitter-blundell-01-android:///callback";
//	private static final String CALLBACK_URL = "http://dummy.com";
	/** Preferences to store a logged in users credentials */
	private static SharedPreferences mPrefs;
	/** Twitter4j object */
//	private static Twitter mTwitter;
	private static Twitter mTwitter;
	/**
	 * The request token signifies the unique ID of the request you are sending
	 * to twitter
	 */
	private RequestToken mReqToken;

	private Button mLoginButton;
	private Button mTweetButton;

	private String message;

	private TweetToTwitterActivity instance;

	private WebView twitterSite;

	protected String loadURL;

	protected String tweetResult = "";

	private TwitterFactory factory;
	private Context context;
	TwitterListener listener;

	OnClickListener  mTweetButtonOnCliclistener = new View.OnClickListener() {
		  
		  @Override public void onClick(View v) { Log.i(TAG, "Tweet Pressed");
		  Toast.makeText(getInstance(), tweetMessage(getApplicationContext()), Toast.LENGTH_LONG).show();
		  } };
		  
		  OnClickListener  mLogInButtonOnCliclistener = new  View.OnClickListener() {
			  
			  @Override public void onClick(View v) { Log.i(TAG, "Login Pressed");
			  if (mPrefs.contains(PREF_ACCESS_TOKEN)) { Log.i(TAG, "Repeat User");
			  loginAuthorisedUser(); } else { Log.i(TAG, "New User");
			 loginNewUser(); }
			  
			  } };
	
@Override
public View onCreateView(String name, Context context, AttributeSet attrs) {
	// TODO Auto-generated method stub
	return super.onCreateView(name, context, attrs);
}
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "Loading TweetToTwitterActivity");
		setContentView(R.layout.tweet_activity);
//		instance = this;
		setInstance(this);
		this.context = getApplicationContext();
		

/*		  listener = new TwitterAdapter() {
			  Context inner_instance = instance;
		        @Override public void updatedStatus(Status status) {
		        	Log.i(TAG, "Updated status" + status.getText());
		        	tweetResult = "Tweet Successful";
		        	Toast.makeText(getInstance(), tweetResult , Toast.LENGTH_SHORT)
					.show();
//		          onBackPressed();
		        }
		        
		          @Override public void onException(TwitterException e, TwitterMethod method) {
		        	  Log.i(TAG, "Got an error " + method);
		              if (method.equals(TwitterMethod.UPDATE_STATUS)) {
		                e.printStackTrace();
		                tweetResult = "Tweet error, try again later";
		                Toast.makeText(getInstance(), tweetResult , Toast.LENGTH_SHORT)
		    			.show();
		                
		                
		                onBackPressed();
		                
		              } 
		              if (method == TwitterMethod.) {
		                  e.printStackTrace();
		                  Toast.makeText(getApplicationContext(), "Tweet error, try again later" , Toast.LENGTH_SHORT)
		  				.show();
		              }else {
		                throw new AssertionError("Should not happen");
		              }
		            }
			};*/
/*		if (!tweetResult.equals("")){
			Toast.makeText(getApplicationContext(), tweetResult , Toast.LENGTH_SHORT)
			.show();
			tweetResult = "";
			
			}*/
		
		// Create a new shared preference object to remember if the user has
		// already given us permission
		mPrefs = getSharedPreferences("twitterPrefs", MODE_PRIVATE);
		Log.i(TAG, "Got Preferences");
		
	        

	        /*@Override public void onException(TwitterException e, int method) {
	          if (method == TwitterMethods.UPDATE_STATUS) {
	            e.printStackTrace();
	          } else {
	            throw new AssertionError("Should not happen");
	          }
	        }*/
	    
	    // The factory instance is re-useable and thread safe.
	     factory = new TwitterFactory();
	     mTwitter = factory.getInstance();
//	     mTwitter.addListener(listener);
//	    asyncTwitter.updateStatus(args[0]);
		
		// Load the twitter4j helper
//		mTwitter = new TwitterFactory().getInstance();
		Log.i(TAG, "Got Twitter4j");

		// Tell twitter4j that we want to use it with our app
		mTwitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		Log.i(TAG, "Inflated Twitter4j");

		mLoginButton = (Button) findViewById(R.id.login_button);
		mTweetButton = (Button) findViewById(R.id.tweet_button);
		
/*				if (mPrefs.contains(PREF_ACCESS_TOKEN)) {
					Log.i(TAG, "Repeat User");
					loginAuthorisedUser();
				} else {
					Log.i(TAG, "New User");
					loginNewUser();
				}
*/
				
		
		
/*		mLoginButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Log.i(TAG, "Login Pressed");
				if (mPrefs.contains(PREF_ACCESS_TOKEN)) {
					Log.i(TAG, "Repeat User");
					loginAuthorisedUser();
				} else {
					Log.i(TAG, "New User");
					loginNewUser();
				}

			}
		});*/
//		mTweetButton.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				Log.i(TAG, "Tweet Pressed");
//				tweetMessage();
//
//			}
//		});
		 mLoginButton.setOnClickListener(mLogInButtonOnCliclistener);
		 
		  mTweetButton.setOnClickListener(mTweetButtonOnCliclistener);
		
		 

	}
@Override
protected void onRestart() {
	// TODO Auto-generated method stub
	super.onRestart();

}
	/**
	 * Button clickables are declared in XML as this projects min SDK is
	 * 1.6</br> </br> Checks if the user has given this app permission to use
	 * twitter before</br> If so login and enable tweeting</br> Otherwise
	 * redirect to Twitter for permission
	 * 
	 * @param v
	 *            the clicked button
	 */
//	public void buttonLogin(View v) {
//		Log.i(TAG, "Login Pressed");
//		if (mPrefs.contains(PREF_ACCESS_TOKEN)) {
//			Log.i(TAG, "Repeat User");
//			loginAuthorisedUser();
//		} else {
//			Log.i(TAG, "New User");
//			loginNewUser();
//		}
//	}

	/**
	 * Button clickables are declared in XML as this projects min SDK is
	 * 1.6</br> </br>
	 * 
	 * @param v
	 *            the clicked button
	 */
//	public void buttonTweet(View v) {
//		Log.i(TAG, "Tweet Pressed");
//		tweetMessage();
//	}

	public  TweetToTwitterActivity getInstance() {
		return instance;
	}

	public  void setInstance( TweetToTwitterActivity instance) {
		this.instance = instance;
	}

	/**
	 * Create a request that is sent to Twitter asking 'can our app have
	 * permission to use Twitter for this user'</br> We are given back the
	 * {@link mReqToken} that is a unique indetifier to this request</br> The
	 * browser then pops up on the twitter website and the user logins in ( we
	 * never see this informaton )</br> Twitter then redirects us to
	 * {@link CALLBACK_URL} if the login was a success</br>
	 * 
	 */
	private void loginNewUser() {

		twitterSite = new WebView(this);
//twitterSite.setWebViewClient(new CustomWebViewClient());
//					Log.i(TAG, "Request App Authentication");

		try {
			Runnable runnable = new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						mReqToken = mTwitter.getOAuthRequestToken(CALLBACK_URL);
						loadURL = mReqToken.getAuthenticationURL();
						twitterSite.loadUrl(loadURL);

						twitterSite.loadUrl(loadURL);
						twitterSite.requestFocus(View.FOCUS_DOWN);

						
						
					} catch (TwitterException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			Thread thread = new Thread(runnable);
			thread.start();
			thread.join();
			instance.setContentView(twitterSite);
			
//			Log.i(TAG, mReqToken.getAuthenticationURL().toString());
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//					 mReqToken = mTwitter.getOAuthRequestToken();
//					Log.i(TAG, "Starting Webview to login to twitter");
//					loadURL = mReqToken.getAuthenticationURL();
//					Log.i(TAG, mReqToken.getAuthenticationURL().toString());

	
			
			// twitterSite.getSettings().setBuiltInZoomControls(true);
			// twitterSite.getSettings().setLayoutAlgorithm(
			// LayoutAlgorithm.SINGLE_COLUMN);
			twitterSite.requestFocus(View.FOCUS_DOWN);

			setContentView(twitterSite);
		/*	
		} catch (TwitterException e) {
			
			Log.i(TAG, e.toString());
			Toast.makeText(this, "Twitter Login error, try again later "+ e.toString(),
					Toast.LENGTH_SHORT).show();
		}*/
	}
public class CustomWebViewClient extends WebViewClient{
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		// TODO Auto-generated method stub
//		return super.shouldOverrideUrlLoading(view, url);
		return false;
	}
}
	/**
	 * The user had previously given our app permission to use Twitter</br>
	 * Therefore we retrieve these credentials and fill out the Twitter4j helper
	 */
	private void loginAuthorisedUser() {
		String token = mPrefs.getString(PREF_ACCESS_TOKEN, null);
		String secret = mPrefs.getString(PREF_ACCESS_TOKEN_SECRET, null);

		// Create the twitter access token from the credentials we got
		// previously
		AccessToken at = new AccessToken(token, secret);
//mTwitter = new TwitterFactory().getInstance();
//		mTwitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		mTwitter.setOAuthAccessToken(at);
//		mTwitter.getAuthorization();
		Log.i(TAG, "PREF_ACCESS_TOKEN " + token + " PREF_ACCESS_TOKEN_SECRET "+ secret);
		Toast.makeText(this, "Welcome back", Toast.LENGTH_SHORT).show();
		
//		enableTweetButton();
	}

	/**
	 * Catch when Twitter redirects back to our {@link CALLBACK_URL}</br> We use
	 * onNewIntent as in our manifest we have singleInstance="true" if we did
	 * not the getOAuthAccessToken() call would fail
	 */
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Log.i(TAG, "New Intent Arrived");
		dealWithTwitterResponse(intent);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i(TAG, "Arrived at onResume");
		setInstance(this);
		 mLoginButton.setOnClickListener(mLogInButtonOnCliclistener);
		 
		  mTweetButton.setOnClickListener(mTweetButtonOnCliclistener);
	}

	/**
	 * Twitter has sent us back into our app</br> Within the intent it set back
	 * we have a 'key' we can use to authenticate the user
	 * 
	 * @param intent
	 */
	private void dealWithTwitterResponse(Intent intent) {
		Uri uri = intent.getData();
		if (uri != null && uri.toString().contains(CALLBACK_URL)) { // If the
																		// user
																		// has
																		// just
																		// logged
																		// in
			String oauthVerifier = uri.getQueryParameter("oauth_verifier");

			authoriseNewUser(oauthVerifier);
		}
	}

	/**
	 * Create an access token for this new user</br> Fill out the Twitter4j
	 * helper</br> And save these credentials so we can log the user straight in
	 * next time
	 * 
	 * @param oauthVerifier
	 */
	private void authoriseNewUser(final String oauthVerifier) {
		try {
			Runnable runnable = new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						AccessToken at = mTwitter.getOAuthAccessToken(mReqToken,
								oauthVerifier);
						mTwitter.setOAuthAccessToken(at);

						saveAccessToken(at);
						Log.i(TAG, "oauthVerifier = " + oauthVerifier);
						// Set the content view back after we changed to a webview
						

						enableTweetButton();
						
					} catch (TwitterException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			Thread thread = new Thread(runnable);
			thread.start();
			thread.join();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setContentView(R.layout.tweet_activity);
	}

	/**
	 * Allow the user to Tweet
	 */
	private void enableTweetButton() {
		Log.i(TAG, "User logged in - allowing to tweet");
		mLoginButton.setEnabled(false);
		mLoginButton.setClickable(false);
		mTweetButton.setEnabled(true);
		
//		tweetMessage();
	}

	/**
	 * Send a tweet on your timeline, with a Toast msg for success or failure
	 */
	private String tweetMessage(Context context) {
		tweetResult = "Sompthing gone wrong.";
Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				 try {
					 mTwitter.getAuthorization();
					message = DataProvider.getContent().get(DataProvider.getContentPos()).getLink();
					
					 Log.i(TAG, " message =" + message);
						try {
							
							
							Log.i(TAG, "tweetMessage() " + message);
							
							mTwitter.updateStatus(message);
							tweetResult = "Tweet Successful";
							
						} catch (TwitterException e) {
							Log.i(TAG, "TwitterException e code" + e.getErrorCode() + " message "+e.getErrorMessage()+ "try to send message " + message);
	
							tweetResult = "Tweet error, try again later";
						} catch (SQLException e) {
							Log.i(TAG, "SQLException e" + e.getMessage());
							e.printStackTrace();
						}
					
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (java.sql.SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		};
		Thread thread = new Thread(runnable);
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    	return tweetResult;
		   
		
//		onBackPressed();
	}

	private void saveAccessToken(AccessToken at) {
		String token = at.getToken();
		String secret = at.getTokenSecret();
		Editor editor = mPrefs.edit();
		
		editor.putString(PREF_ACCESS_TOKEN, token);
		editor.putString(PREF_ACCESS_TOKEN_SECRET, secret);
		editor.commit();
	}
}