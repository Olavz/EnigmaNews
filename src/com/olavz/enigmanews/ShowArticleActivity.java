package com.olavz.enigmanews;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

public class ShowArticleActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_article);

		String title = getIntent().getStringExtra("title");
		String content = getIntent().getStringExtra("content");
		
		
		String html = "<html><body>"+ content +"</body></html>";
		String mime = "text/html";
		String encoding = "utf-8";
	    ((TextView) findViewById(R.id.txtTitle)).setText(title);
		WebView webView = (WebView)findViewById(R.id.txtContent);
		webView.loadDataWithBaseURL(null, html, mime, encoding, null);
	}

}
