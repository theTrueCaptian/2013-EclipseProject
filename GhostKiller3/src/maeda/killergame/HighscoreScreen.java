package maeda.killergame;

import maeda.killergame.InsectKiller.States;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class HighscoreScreen extends View{

	Context context;
	String message = "";
	long time1 = System.currentTimeMillis();
	
	public HighscoreScreen(Context context) {
		super(context);
		this.context = context;
		
		//to make it focusable so that it will receive touch events properly
		this.setFocusable(true);
				
		
	}

	public void onDraw(Canvas canvas){
		int height = canvas.getHeight();
		int width = canvas.getWidth();
		
		Paint paint=new Paint();
		paint.setAntiAlias(true);       //for smooth rendering
		paint.setARGB(255, 0, 255, 0);    //setting the paint color
		paint.setTextSize(30);
		
		Stuff.highscore = getResizedBitmap(Stuff.highscore,  height/3-10, Stuff.highscore.getWidth());
    	
    	canvas.drawColor(Color.BLACK);
    	
    	canvas.drawBitmap(Stuff.highscore, width/2-Stuff.highscore.getWidth()/2, 0, null);
    	if(Stuff.topscores!=null){
	    	for(int i=0; i<Stuff.topscores.length; i++){
	    		canvas.drawText((i+1)+". "+Stuff.topscores[i]+" ", width/2-50, height/3+20+30*i, paint);
	    	}
    	}else{
    		System.out.println("NULLL topscores?!?!?");
    		Stuff.topscores = new int[5];
        	for(int i=0; i<Stuff.topscores.length; i++)
        		Stuff.topscores[i] = 0;
    	}
	}
	
	
	
	public States handleTouches(MotionEvent evt){
		//go bck to menu screen
		long now = System.currentTimeMillis();
		if(now-time1>1000)	{
			time1 = System.currentTimeMillis();
			return States.MENU;
		}
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
