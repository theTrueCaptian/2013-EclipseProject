package maeda.killergame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.view.View;

public class ScreenOLD extends View{
	
	Context context;
	int height;
	int width;
	
	public ScreenOLD(Context context) {
		super(context);
		this.context = context;
		
		//to make it focusable so that it will receive touch events properly
		this.setFocusable(true);
		invalidate();
	}

	public void onDraw(Canvas canvas)
	{
		height = canvas.getHeight()-15;
		width = canvas.getWidth()-15;
    	canvas.drawColor(Color.BLACK);

	}
	/*
	public States handleTouches(MotionEvent evt) {
		return null;
	}
	*/
	public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// create a matrix for the manipulation
		Matrix matrix = new Matrix();
		// resize the bit map
		matrix.postScale(scaleWidth, scaleHeight);
		// recreate the new Bitmap
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
		return resizedBitmap;
	}
}
