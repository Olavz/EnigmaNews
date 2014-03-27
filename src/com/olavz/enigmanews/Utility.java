package com.olavz.enigmanews;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Utility {
	
	public static String getContentFromUrl(String url) {
		try {
			DefaultHttpClient httpclient = new DefaultHttpClient();
	        HttpGet httpget = new HttpGet(url);
	        HttpResponse response = httpclient.execute(httpget);
			InputStream ips = response.getEntity().getContent();
			BufferedReader buf = new BufferedReader(new InputStreamReader(ips, "UTF-8"));

			StringBuilder sb = new StringBuilder();
			String s;
			while (true) {
				s = buf.readLine();
				if (s == null || s.length() == 0)
					break;
				sb.append(s);
			}
			buf.close();
			ips.close();
			
			return sb.toString();
		} catch(Exception ex) {
			Log.d("Exception", ex.toString());
		} finally {
			// any cleanup code...
		}
		return "";
	}

	
	public static JSONObject parseJSONObj(String json) {
		JSONObject jo;
		try {
			jo  = new JSONObject(json);
		} catch (JSONException e) {
			return null;
		}
		return jo;		
	}
	
}
