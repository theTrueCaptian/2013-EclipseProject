package maeda.killergame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class RedCircle {
	float x, y;
	float radius;
	int lives = 3;
	
	public RedCircle(float x, float y, float radius){
		this.x=x;
		this.y=y;
		this.radius = radius;
	}

	//returns a number between 1(?) and k
	public float randgen(int k){
		return (float) (Math.random()*k);
	}
	
	public void draw(Canvas canvas){
		Paint paint=new Paint();
		paint.setAntiAlias(true);       //for smooth rendering
		
		paint.setARGB(255, 0, 0, 0);
		
		paint.setARGB(255, 255, 0, 0);    //setting the paint color
		Stuff.player = getResizedBitmap(Stuff.player, (int)radius*2,(int)radius*2);
		canvas.drawBitmap(Stuff.player, x-radius, y-radius, null);		
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
