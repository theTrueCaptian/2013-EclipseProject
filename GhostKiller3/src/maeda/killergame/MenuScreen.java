package maeda.killergame;

import com.tapfortap.Interstitial;

import maeda.killergame.InsectKiller.States;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.view.MotionEvent;
import android.view.View;

public class MenuScreen extends View {

	Context context;
	int height;
	int width;
	public MenuScreen(Context context) {
		super(context);
		this.context = context;
		
		// Later when you want to display the interstitial
				Interstitial.show(context);
		
		//to make it focusable so that it will receive touch events properly
		this.setFocusable(true);
		//adding touch listener to the real view
		//this.setOnTouchListener(this);
	}
	
	public void onDraw(Canvas canvas)
	{
		height = canvas.getHeight()-15;
		width = canvas.getWidth()-15;
		
		//draw menu items
		//Bitmap start = BitmapFactory.decodeFile("start.png");
		Stuff.menubackground = getResizedBitmap(Stuff.menubackground,  height, width);
		Stuff.title = getResizedBitmap(Stuff.title,  height/4, Stuff.title.getWidth());
    	Stuff.start = getResizedBitmap(Stuff.start,  height/4-10, Stuff.start.getWidth());
    	Stuff.help = getResizedBitmap(Stuff.help,  height/4-10, Stuff.help.getWidth());
    	Stuff.highscore = getResizedBitmap(Stuff.highscore,  height/4-10, Stuff.highscore.getWidth());
		    	
    	canvas.drawColor(Color.BLACK);
    	canvas.drawBitmap(Stuff.menubackground, 0, 0, null);
    	canvas.drawBitmap(Stuff.title, width/2-Stuff.title.getWidth()/2, 0, null);
    	canvas.drawBitmap(Stuff.start, width/2-Stuff.start.getWidth()/2, height/4, null);
    	canvas.drawBitmap(Stuff.help, width/2-Stuff.help.getWidth()/2, 2*height/4, null);
    	canvas.drawBitmap(Stuff.highscore, width/2-Stuff.highscore.getWidth()/2, 3*height/4, null);
		
    	//if(cool)
    		//canvas.drawColor(Color.RED);
	}

	boolean cool =false;
	//if theres no change in states, it shall return a null
	public States handleTouches(MotionEvent evt){
		float x = evt.getX();
		float y = evt.getY();
		
		invalidate();
		if(x>width/2-Stuff.start.getWidth()/2 && y>height/4 && x<width/2-Stuff.start.getWidth()/2+Stuff.start.getWidth() && y<height/4+Stuff.start.getHeight()){
			return States.GAME;
		}else if(x>width/2-Stuff.help.getWidth()/2 && y>2*height/4 && x<width/2-Stuff.help.getWidth()/2+Stuff.help.getWidth() && y<2*height/4+Stuff.help.getHeight()){
			return States.HELP;
		}else if(x>width/2-Stuff.highscore.getWidth()/2 && y>3*height/4 && x<width/2-Stuff.highscore.getWidth()/2+Stuff.highscore.getWidth() && y<3*height/4+Stuff.highscore.getHeight()){
			return States.HIGHSCORE;
			//cool = true;
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
