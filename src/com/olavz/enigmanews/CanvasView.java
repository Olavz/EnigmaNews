package com.olavz.enigmanews;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CanvasView extends View {

	private Context context;
	private Random random = new Random(System.currentTimeMillis());
	private ArrayList<Entry> entries;

	public CanvasView(Context context) {
		super(context);
		this.context = context;
		entries = new ArrayList<Entry>();
	}

	public void updateEntries(ArrayList<Entry> entries) {
		this.entries = entries;
		this.invalidate();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// get pointer index from the event object
		int pointerIndex = event.getActionIndex();

		// get pointer ID
		int pointerId = event.getPointerId(pointerIndex);
		// get masked (not specific to a pointer) action
		int maskedAction = event.getActionMasked();

		switch (maskedAction) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_POINTER_DOWN: {
				// We have a new pointer. Lets add it to the list of pointers
				PointF f = new PointF();
				f.x = event.getX(pointerIndex);
				f.y = event.getY(pointerIndex);
				validateClick((int)f.x, (int)f.y);
				break;
			}
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP:
			case MotionEvent.ACTION_CANCEL: {
				break;
			}
		}

		return true;
	}
	
	private Entry selectedEntry;
	private int selectedTries = 0;
	
	private void validateClick(int x, int y) {
		boolean hitEntry = false;
		for(Entry entry : entries) {
			for(Rect rect : entry.getBounds()) {
				if(x > rect.left && x < (rect.left+rect.width())) {
					if(y > rect.top && y < (rect.top + rect.height())) {
						hitEntry = true;
						Log.d("test", "X " + x + " Y " + y + " " + selectedTries);
						
						// We touched this entry
						if(selectedTries == 0) {
							selectedEntry = entry;
							selectedTries++;
						} else if(selectedTries == 1) {
							if(selectedEntry == entry) {
								// We have a match or we clicked the same key twise :P
								Log.d("test", "Open entry: " + entry.title);
								Intent intent = new Intent("com.olavz.enigmanews.ShowArticleActivity");
								intent.putExtra("title", entry.title);
								intent.putExtra("content", entry.content);
								context.startActivity(intent);
							}
							selectedEntry = null;
							selectedTries = 0;
						}
						
					}
				}
			}
		}
		
		if(!hitEntry) {
			// We dint hit any of the entries, so lets asume the users wants a restart.
			selectedEntry = null;
			selectedTries = 0;
			Log.d("test", "Restart ");
		}
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

		boolean runLoop = true;
		while (runLoop) {
			canvas.drawPaint(paint);
			for (int i = 0; i < entries.size(); i++) {
				String[] txtParts = entries.get(i).getParts();
				Rect[] bounds = new Rect[entries.get(i).getParts().length];

				for (int j = 0; j < txtParts.length; j++) {
					bounds[j] = drawBox(canvas, txtParts[j]);
				}

				entries.get(i).setBounds(bounds);
			}

			if (validateAllBoundsIsCollide() == false) {
				runLoop = false;
				Log.d("test", "Not valid, retrying");
			}
		}

	}

	private Rect drawBox(Canvas ctx, String txt) {
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setStyle(Style.FILL);
		paint.setTextSize(40);

		int wStart = random.nextInt(ctx.getWidth() / 2);
		int hStart = random.nextInt(ctx.getHeight());

		Rect bounds = new Rect();
		paint.getTextBounds(txt, 0, txt.length(), bounds);
		if (wStart + bounds.width() > ctx.getWidth()) {
			wStart = wStart - (wStart + bounds.width() - ctx.getWidth() + 20);
		}
		ctx.drawText(txt, wStart, hStart + bounds.height(), paint);

		Rect modded = new Rect();
		modded.left = wStart;
		modded.right = wStart + bounds.width();
		modded.top = hStart;
		modded.bottom = hStart + bounds.height();

		return modded;
	}

	public boolean validateAllBoundsIsCollide() {

		ArrayList<Rect> bounds = new ArrayList<Rect>();
		for (Entry entry : entries) {
			for (Rect r : entry.getBounds()) {
				bounds.add(r);
			}
		}

		for (int i = 0; i < bounds.size(); i++) {
			for (int j = 0; j < bounds.size(); j++) {
				if (j != i) {
					Rect a = bounds.get(i);
					Rect b = bounds.get(j);

					if (Rect.intersects(a, b)) {
						return true;
					}
				}
			}

		}
		return false;
	}

}