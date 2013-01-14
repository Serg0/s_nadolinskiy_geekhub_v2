package com.example.geekhub_3rd_homework;

import android.app.Activity;



import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

/**
 *Demonstrates how to use the scribe library to login with twitter.
 *
 */
public class TweetActivity extends Activity {
	
	final static String APIKEY = "820j4TqRXnum214QQu3LQ";
	final static String APISECRET = "rVTs6Iuz56Km6W2sHLUUnFAfnUGs1b46vtA37AVwIc";
	final static String CALLBACK = "oauth://twitter";
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweet_activity2);
        
        //set up service and get request token as seen on scribe website 
        //https://github.com/fernandezpablo85/scribe-java/wiki/Getting-Started
        final OAuthService s = new ServiceBuilder()
        .provider(TwitterApi.class)
		.apiKey(APIKEY)
		.apiSecret(APISECRET)
		.callback(CALLBACK)
		.build();

		final Token requestToken = s.getRequestToken();
		final String authURL = s.getAuthorizationUrl(requestToken);
        
		final TextView textView = (TextView)findViewById(R.id.textview);
        final  WebView webview = (WebView) findViewById(R.id.webview);
        
        //attach WebViewClient to intercept the callback url
        webview.setWebViewClient(new WebViewClient(){
        	@Override
        	public boolean shouldOverrideUrlLoading(WebView view, String url){
        		
        		//check for our custom callback protocol 
            //otherwise use default behavior
        		if(url.startsWith("oauth")){
        			//authorization complete hide webview for now.
        			webview.setVisibility(View.GONE);
        			
        			Uri uri = Uri.parse(url);
        			String verifier = uri.getQueryParameter("oauth_verifier");
        			Verifier v = new Verifier(verifier);
        			
        			//save this token for practical use.
        			Token accessToken = s.getAccessToken(requestToken, v);
        			
        			//host twitter detected from callback oauth://twitter
        			if(uri.getHost().equals("twitter")){
        				//requesting xml because its easier 
                    //for human to read as it comes back
	        			OAuthRequest req = new OAuthRequest(Verb.GET, 
                            "http://api.twitter.com/1/account/verify_credentials.xml");
	        			s.signRequest(accessToken, req);
	        			Response response = req.send();
	        			textView.setText(response.getBody());
        			}
        			
        			return true;
        		}
        		
        		
        		return super.shouldOverrideUrlLoading(view, url);
        	}
        });
        

        //send user to authorization page
        webview.loadUrl(authURL);
    }
}