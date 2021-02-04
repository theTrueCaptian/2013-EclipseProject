package maeda.killergame;

import java.util.ArrayList;

import maeda.killergame.InsectKiller.States;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.MotionEvent;
import android.view.View;

public class HelpScreen extends View{
	ArrayList<Bitmap> instructions = new ArrayList<Bitmap>();
	int counter = 0;
	long time1 = System.currentTimeMillis();
	public HelpScreen(Context context) {
		super(context);
		
		//to make it focusable so that it will receive touch events properly
		this.setFocusable(true);
		
		instructions.add(Stuff.instruction1);
		instructions.add(Stuff.instruction2);
		instructions.add(Stuff.instruction3);
		instructions.add(Stuff.instruction4);
	}
	
	public void onDraw(Canvas canvas){
		int height = canvas.getHeight();
		int width = canvas.getWidth();
		instructions.set(counter, getResizedBitmap(instructions.get(counter),  height, width));
		canvas.drawBitmap(instructions.get(counter), 0, 0, null);
		/*try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public States handleTouches(MotionEvent evt){
		long now = System.currentTimeMillis();
		if(now-time1>1000)	{
			time1 = System.currentTimeMillis();
			counter++;
			if(counter>instructions.size()-1){
				return States.MENU;
			}
		}
		invalidate();
		return null;
		
	}

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
