package com.olavz.enigmanews;

import java.util.ArrayList;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	private String URL_JSON_SOURCE = "https://ajax.googleapis.com/ajax/services/feed/load?v=2.0&q=http%3A%2F%2Fgoo.gl%2FdfVCjV&num=20";
	private ArrayList<Entry> entries;
	private CanvasView canvasView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		canvasView = new CanvasView(this);
		setContentView(canvasView);

		entries = new ArrayList<Entry>();
		fetchData();
	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void fetchData() {
		new WebRequest().execute();
	}

	private class WebRequest extends AsyncTask {
		private String result;

		@Override
		protected Object doInBackground(Object... arg0) {
			String res = Utility.getContentFromUrl(URL_JSON_SOURCE);
			return res;
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);

			// Start parsing the results.
			JSONObject jo;
			try {
				jo = new JSONObject(result.toString());
				JSONArray ja = jo.getJSONObject("responseData")
						.getJSONObject("feed").getJSONArray("entries");
				for (int i = 0; i < ja.length(); i++) {
					JSONObject o = ja.getJSONObject(i);
					entries.add(new Entry(o.toString()));
					Log.d("test", "Got size: " + entries.size());
				}
				canvasView.updateEntries(entries);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}

}
