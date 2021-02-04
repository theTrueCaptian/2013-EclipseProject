package prophet.yusuf;

import prophet.yusuf.ProphetYusufMain.States;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

public class MenuScreen extends View {

	Context context;
	int height;
	int width;
	public MenuScreen(Context context) {
		super(context);
		this.context = context;
		
		
		//to make it focusable so that it will receive touch events properly
		//this.setFocusable(true);
		
		
	}
	
	public void onDraw(Canvas canvas)
	{
		canvas.drawColor(Color.BLACK);
				
		height = canvas.getHeight()-15;
		width = canvas.getWidth()-15;
		
		Stuff.menubackground = getResizedBitmap(Stuff.menubackground,  height, width);
		canvas.drawBitmap(Stuff.menubackground, 0,0, null);
		
		Paint paint = new Paint();
		paint.setTypeface(Typeface.createFromAsset(context.getAssets(),"sketchflow.ttf"));
		paint.setARGB(255, 0, 255, 0);
		paint.setTextSize(height/6);
		canvas.drawText("Prophet Yusuf a.s.", 10, 40, paint);
		
		Stuff.choicebutton = getResizedBitmap(Stuff.choicebutton,  height*1/4-5, width*2/3);
		canvas.drawBitmap(Stuff.choicebutton, width*1/6, height*1/4, null);
		canvas.drawBitmap(Stuff.choicebutton, width*1/6, height*2/4, null);
		canvas.drawText("Story", width*2/6+5, height*1/4+paint.getTextSize(), paint);
		canvas.drawText("Quiz", width*2/6+5, height*2/4+paint.getTextSize(), paint);
		
	}

	//if theres no change in states, it shall return a null
	public States handleTouches(MotionEvent evt){
		float x = evt.getX();
		float y = evt.getY();
		if(x>width*1/6 && y>height*1/4 && x<width*2/3+width*1/6 && y<height*2/4){
			return States.STORY;
		}else if(x>width*1/6 && y>height*2/4 && x<width*2/3+width*1/6 && y<height*3/4){
			return States.QUIZ;
		}
		return null;
		/*
		invalidate();
		if(x>width/2-Stuff.start.getWidth()/2 && y>height/4 && x<width/2-Stuff.start.getWidth()/2+Stuff.start.getWidth() && y<height/4+Stuff.start.getHeight()){
			return States.GAME;
		}else if(x>width/2-Stuff.help.getWidth()/2 && y>2*height/4 && x<width/2-Stuff.help.getWidth()/2+Stuff.help.getWidth() && y<2*height/4+Stuff.help.getHeight()){
			return States.HELP;
		}else if(x>width/2-Stuff.highscore.getWidth()/2 && y>3*height/4 && x<width/2-Stuff.highscore.getWidth()/2+Stuff.highscore.getWidth() && y<3*height/4+Stuff.highscore.getHeight()){
			return States.HIGHSCORE;
			//cool = true;
		}*/
		//return null;
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

