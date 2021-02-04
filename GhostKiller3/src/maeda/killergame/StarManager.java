package maeda.killergame;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

public class StarManager {
	public static int durationRelease = 10000;
	private long release = System.currentTimeMillis();
	
	private ArrayList<Star> stars;
	private int canvasHeight, canvasWidth;
	
	private long displayMessage = System.currentTimeMillis();
	private boolean kill = false;
	private int xKill=0, yKill=0;
	
	private Paint paint = new Paint();
	
	
	public StarManager(int canvasWidth, int canvasHeight, Context context){
		this.canvasHeight = canvasHeight;
		this.canvasWidth = canvasWidth;
		
		paint.setAntiAlias(true);       //for smooth rendering
		paint.setTypeface(Typeface.createFromAsset(context.getAssets(),"CloisterBlack.ttf"));
		paint.setARGB(255, 0, 255, 0);
		paint.setTextSize(30);
		
		stars = new ArrayList<Star>();
		stars.add(new Star((int)(Math.random()*(canvasWidth-Stuff.star.getWidth())), 0));
		
		update();
	}
	
	public void update(){
		for(int i=0; i<stars.size(); i++){
			stars.get(i).update(canvasHeight);
		}
		
		//should we add?
		long now = System.currentTimeMillis();
		if(now-release>=durationRelease){
			stars.add(new Star((int)(Math.random()*(canvasWidth-Stuff.star.getWidth())), 0));
			release = System.currentTimeMillis();
		}
		
		//search for one that needs to be deleted from list
		int i=0;
		while(i<stars.size()){
			if(stars.get(i).isGone())
				stars.remove(i);
			else 
				i++;
		}
		
	}
	
	public int collide(float x, float y){
		for(int i=0; i<stars.size(); i++){
			if((x>stars.get(i).getX() && x<stars.get(i).getX()+Stuff.star.getWidth())&& (y>stars.get(i).getY() && y<stars.get(i).getY()+Stuff.star.getHeight())){
				kill(i);
				return 1;
			}
		}
		return 0;
	}
	
	//kills a star and reeturns a life
	public int kill(int i){
		//used for message
		kill = true;
		displayMessage = System.currentTimeMillis();
		this.xKill = stars.get(i).getX();
		this.yKill = stars.get(i).getY();
		try{
			stars.remove(i);
		}catch(ArrayIndexOutOfBoundsException ed){
			System.out.println("aww star killer got an exception");
			kill(i);
		}
		return 1;
	}
	
	public void draw(Canvas canvas){
		for(int i=0; i<stars.size(); i++){
			stars.get(i).draw(canvas);
		}
		long now = System.currentTimeMillis();
		if(kill && now-displayMessage<3000){
			//display
			canvas.drawText("1+ Life!", xKill, yKill, paint);
			
		}else{
			kill = false;
		}
	}
}
