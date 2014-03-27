package com.olavz.enigmanews;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.View;

public class CanvasView extends View {
	private ArrayList<Entry> entries;
	
	public CanvasView(Context context) {
		super(context);
		entries = new ArrayList<Entry>();
	}
	
	public void updateEntries(ArrayList<Entry> entries) {
		this.entries = entries;
		this.invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int cxtWidth = getWidth();
		int cxtHeight = getHeight();

		Paint paint = new Paint();
		paint.setColor(Color.parseColor("#AACC00"));
		paint.setStyle(Style.FILL);
		canvas.drawPaint(paint);
		
		Log.d("test", "Now: " + entries.size());
		for(int i=0; i<entries.size(); i++) {
			String[] txtParts = entries.get(i).getParts();
			for(String txtKey : txtParts) {
				drawBox(canvas, txtKey);
			}
		}
	}
	
	private void drawBox(Canvas ctx, String txt) {
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setStyle(Style.FILL);
		paint.setTextSize(80);
		Random r = new Random();
		
		int wStart = r.nextInt(100);
		int hStart = r.nextInt(ctx.getHeight());
		paint.setStrokeWidth(0);
		
		Rect bounds = new Rect();
		paint.getTextBounds(txt, 0, txt.length(), bounds);
		
		ctx.drawRect(wStart, hStart, wStart+bounds.width(), hStart+bounds.height(), paint);
		
		paint.setColor(Color.RED);
		ctx.drawText(txt, wStart, hStart + bounds.height(), paint);
	}
	
	private void drawOptions() {
		for(Entry entry : entries) {
			entry.getParts();
		}
	}

}