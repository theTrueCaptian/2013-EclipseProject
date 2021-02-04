package maeda.killergame;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;

public class ReddieManager {
	
	//indecisive people need this variable
	static int numReddies = 1;
	ArrayList<RedCircle> reddies = new ArrayList<RedCircle>();
	//reddie determines whether we're done
	boolean gameover = false;
	//canvas info
	int height, width;
	long laserlife = System.currentTimeMillis();
	float laserx, lasery;
	
	public ReddieManager(Context context){
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		width = metrics.widthPixels;
		height = metrics.heightPixels;
		
		genReddies();
		
	}
	
	public void draw(Canvas canvas){
		for(int i=0; i<reddies.size(); i++){
			//System.out.println(reddies.get(i).x+" "+reddies.get(i).y+" "+reddies.get(i).radius);
			reddies.get(i).draw(canvas);			
		}
		displayLaser(canvas);
	}
	
	public void wounded(int k){
		reddies.get(k).lives--;
	}
	
	public void killed(int k){
		reddies.remove(k);
		if(reddies.size()==0){
			gameover=true;
		}
	}
	
	public void displayLaser(Canvas canvas){
		Paint paint=new Paint();
		paint.setAntiAlias(true);       //for smooth rendering
		//draw laser
		long now = System.currentTimeMillis();
		if(now-laserlife<200 && !reddies.isEmpty()){
			paint.setARGB(255, 255, 0, 0);    //setting the paint color
			canvas.drawLine(laserx, lasery, reddies.get(0).x, reddies.get(0).y, paint);
			canvas.drawCircle(laserx, lasery, 20, paint);
			paint.setTextSize(15);
			canvas.drawText("+10", laserx-20, lasery+20, paint);
			
		}
	}
	
	public void useLaser(float x, float y){
		this.laserx=x;
		lasery=y;
		laserlife = System.currentTimeMillis();
	}
	
	public void genReddies(){
		for(int i=0; i<numReddies; i++){
			float x, y, radius;
			Random random = new Random(15);
			do{				
				System.out.println(width+"  "+height);
				
				x = random.nextInt(width-150)+100;
				y = random.nextInt(height-150)+100;
				//radius between 15 and 30
				radius = (float) (random.nextInt(15)+10.0);
				//check if the coordinate available
			}while(!isEmpty(x,y,radius));
			
			
			reddies.add(new RedCircle(x, y, radius));
			this.laserx=x;
			lasery=y;
		}
		
	}
	
	public boolean isEmpty(float x, float y, float radius){
		for(int i=0; i<reddies.size(); i++){
			float startX = reddies.get(i).x;
			float startY = reddies.get(i).y;
		
			//calculate distance
			float dxSq = (startX - x) * (startX - x);
			float dySq = (startY - y) * (startY - y);
			int d = (int) Math.sqrt(dxSq + dySq);
			
			int r1Pr2 = (int) (reddies.get(i).radius + radius);
		    if (d < r1Pr2) {
		        System.out.println("Collided");
		        return false;
		    } else if (d == r1Pr2) {
		        System.out.println("Just touching");
		        return false;
		    } 
		    
			
		}
		return true;
	}
}
