package com.olavz.enigmanews;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Rect;

public class Entry {

	public String title = "";
	public String link = "";
	public String author = "";
	public String publishedDate = "";
	public String contentSnippet = "";
	public String content = "";
	
	public String jsonString = "";
	public JSONObject jsonObject = null;
	
	// Bounds can represent multiple bounds when title is split into multiple peacesl.
	private Rect[] bounds;
	
	public Entry(String jsonString) {
		try {
			jsonObject = new JSONObject(jsonString);
		} catch (JSONException e) {
		}
		
		try {
			title = jsonObject.getString("title");
			link = jsonObject.getString("link");
			author = jsonObject.getString("author");
			publishedDate = jsonObject.getString("publishedDate");
			contentSnippet = jsonObject.getString("contentSnippet");
			content = jsonObject.getString("content");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String[] getParts() {
		return "Hello wORLd,wHERE ARE i?".split(",");
	}
	
	public JSONObject getJSONObject() {
		return jsonObject;
	}
	
	public void setBounds(Rect[] bounds) {
		this.bounds = bounds;
	}
	
	public Rect[] getBounds() {
		return bounds;
	}
	
	
}
