package com.example.geekhub_3rd_homework;

import com.google.android.gms.plus.PlusShare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ParseDeepLinkActivity extends Activity {
	@Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    String deepLinkId = PlusShare.getDeepLinkId(this.getIntent());
	    Intent deepLinkTarget = parseDeepLinkId(deepLinkId);
	    startActivity(deepLinkTarget);
	    finish();
	  }

	private Intent parseDeepLinkId(String deepLinkId) {
		return new Intent(deepLinkId);
	}


}
