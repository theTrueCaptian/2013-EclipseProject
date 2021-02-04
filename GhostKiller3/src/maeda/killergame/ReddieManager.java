package maeda.killergame;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
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
	//float laserx, lasery;
	ArrayList<LaserImpact> impacts = new ArrayList<LaserImpact>();
	boolean losealife = false;
	long lifegone = System.currentTimeMillis();
	long comboText = System.currentTimeMillis();
	float combox = 0, comboy = 0;
	int combo=0;
	Paint paint;
	
	public ReddieManager(Context context){
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		width = metrics.widthPixels;
		height = metrics.heightPixels;
		paint=new Paint();
		paint.setTypeface(Typeface.createFromAsset(context.getAssets(),"CloisterBlack.ttf"));
		
		genReddies();
		
	}
	
	public void draw(Canvas canvas){
		for(int i=0; i<reddies.size(); i++){
			//System.out.println(reddies.get(i).x+" "+reddies.get(i).y+" "+reddies.get(i).radius);
			reddies.get(i).draw(canvas);			
		}
		long now = System.currentTimeMillis();
		if(losealife && now-lifegone<500){
			if(Stuff.lifelost!=null)
				canvas.drawBitmap(Stuff.lifelost, width/2-Stuff.lifelost.getWidth()/2, height/2-Stuff.lifelost.getHeight()/2, null);
		}else{
			losealife = false;
		}
		displayLaser(canvas);
		
	}
	
	public void setCombo(int comboiin){
		combo = comboiin;
		if(combo>=20)
		this.comboText = System.currentTimeMillis();
	}
	public void wounded(int k){
		reddies.get(k).lives--;
		losealife = true;
		this.lifegone = System.currentTimeMillis();
	}
	
	public void killed(int k){
		reddies.remove(k);
		if(reddies.size()==0){
			gameover=true;
		}
	}
	
	public void displayLaser(Canvas canvas){
		
		paint.setAntiAlias(true);       //for smooth rendering
		//draw lasers
		int i=0;
		//for(int i = 0; i<impacts.size(); i++){
		if(!impacts.isEmpty()){
			do{
				long now = System.currentTimeMillis();
				if(now-impacts.get(i).laserlife<500 && !reddies.isEmpty()){
				
					paint.setARGB(255, 255, 255, 255);    //setting the paint color
					canvas.drawCircle(impacts.get(i).laserx, impacts.get(i).lasery, impacts.get(i).radius, paint);//white flash
					paint.setARGB(255, 0, 0, 0);    //setting the paint color
					canvas.drawCircle(impacts.get(i).laserx, impacts.get(i).lasery, impacts.get(i).radius/2, paint);//black impact
					paint.setTextSize(15);
					paint.setARGB(255, 0, 255, 0); 
					canvas.drawText("+10", impacts.get(i).laserx-40, impacts.get(i).lasery-40, paint);
					//impacts.get(i).laserx--;
					impacts.get(i).lasery--;
					impacts.get(i).radius--;
					i++;
					
				}else{
					impacts.remove(i);
					//i--;
				}

			}while(i<impacts.size());
			
		}
		long timenow = System.currentTimeMillis();
			if(timenow-this.comboText<700 ){
				//draw the impact combo text encouragement
				paint.setARGB(255, 0, 255, 0); 
				paint.setTextSize(40); 
				//also display the combo of the impacts award text
				if(this.combo>=20 && combo<=30){
					canvas.drawText("Good Job!", combox+30, comboy+30, paint);
					canvas.drawText("+"+(combo*2), combox-30, comboy-30, paint);
				}else if(this.combo>=40 && combo<50){
					canvas.drawText("AWESOME!! ",  combox+30, comboy+30, paint);
					canvas.drawText("+"+(combo*3), combox-30, comboy-30, paint);
				}else if(this.combo>=50 && combo<60){
					canvas.drawText("Pro! ",  combox+30, comboy+30, paint);
					canvas.drawText("+"+(combo*4), combox-30, comboy-30, paint);
				}else if(combo>=60) {
					canvas.drawText("Bravo! ",  combox+30, comboy+30, paint);
					canvas.drawText("+"+(combo*5), combox-30, comboy-30, paint);
				}
			}
				
			
			
		
		
	}
	
	public void newLaserImpact(float x, float y, float radius){
		//this.laserx=x;
		//lasery=y;
		this.impacts.add(new LaserImpact(x, y, radius));
		//if(impacts.isEmpty()){
		//if(combo>=20)
			//this.comboText = System.currentTimeMillis();
		
			this.combox =impacts.get(impacts.size()-1).laserx;
			this.comboy = impacts.get(impacts.size()-1).lasery;
		//}
		//laserlife = System.currentTimeMillis();
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
				radius = (float) (random.nextInt(15)+20.0);
				//check if the coordinate available
			}while(!isEmpty(x,y,radius));
			
			
			reddies.add(new RedCircle(x, y, radius));
			//this.laserx=x;
			//lasery=y;
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
