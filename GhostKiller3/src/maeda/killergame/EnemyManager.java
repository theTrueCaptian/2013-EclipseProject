package maeda.killergame;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;

public class EnemyManager {
	ArrayList<Ball> enemies = new ArrayList<Ball>();
	int height, width;
	Random rand = new Random(15);
	Context context;
	float killx, killy;
	//the enemy must knoweth thine victem
	ReddieManager reddie;
	//this stuff increases every 30 second passes
	int standVelocity = 80;
	long timepassed =System.currentTimeMillis();
	long incPassed =System.currentTimeMillis();
	boolean inc = false;
	int proceedCTR = 0;
	
	public EnemyManager(Context context, ReddieManager reddie){
		this.context = context;
		this.reddie = reddie;
		
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		width = metrics.widthPixels;
		height = metrics.heightPixels;
		
	}
	
	public int killEnemy(float killerx, float killery){
		int score =0;
		for(int i=0; i<enemies.size(); i++){
			try{
				float startX = enemies.get(i).getX();
				float startY = enemies.get(i).getY();
							
				//calculate distance
				float dxSq = (startX - killerx) * (startX - killerx);
				float dySq = (startY - killery) * (startY - killery);
				int d = (int) Math.sqrt(dxSq + dySq);
				
				int r1Pr2 = (int) (enemies.get(i).radius + 20);
			    if (d <= r1Pr2) {
			    	score=score+10;
			    	//killx = enemies.get(i).getX();
			    	//killy = enemies.get(i).getY();
			    	reddie.newLaserImpact(enemies.get(i).getX(), enemies.get(i).getY(), enemies.get(i).radius);	
			        //System.out.println("Collided or just touching");
			        enemies.remove(i);
			        i--;
			        
			    } 
			}catch(IndexOutOfBoundsException ex){
				System.out.println("index out of bound exception caught in killEnemy method! ");
				break;
			}
		}
		return score;
	}
	public void draw(Canvas canvas){
		if(enemies!=null){
			if(!enemies.isEmpty()){
				int i=0;
				do{
				//for(int i=0; i<enemies.size(); i++){
					//if(enemies.get(i)!=null)
					if(i<enemies.size()){
						try{
							enemies.get(i).draw(canvas);
						}catch(IndexOutOfBoundsException ex){
							System.out.println("EnemyManager drawing method outofboundexception caught!!!");
							break;
						}
					}
					i++;
				}while(i<enemies.size());
			}
		}
		if(inc){
			long now =System.currentTimeMillis();			
			Paint paint=new Paint();
			paint.setAntiAlias(true);       //for smooth rendering
			paint.setARGB(255, 0, 0, 255);    //setting the paint color
			//paint.setTextSize(canvas.getHeight()/4);
			canvas.drawBitmap(Stuff.proceedList.get(proceedCTR), canvas.getWidth()/2-Stuff.proceedList.get(proceedCTR).getWidth()/2-20, canvas.getHeight()/2-Stuff.proceedList.get(proceedCTR).getHeight()/2-20, paint);
			if(now-this.incPassed>5000){
				inc = false;
				incPassed =System.currentTimeMillis();
				proceedCTR++;
				if(proceedCTR>=Stuff.proceedList.size()){
					proceedCTR=0;
				}
			}
		}
	}
	
	public void enemykilled(int k){
		enemies.remove(k);
	}
	
	public void proceedEnemy(){
		for(int i=0; i<enemies.size(); i++){
			toggler(enemies.get(i));
		}
	}
	
	public void toggler(Ball inEnemy){
		if (inEnemy.getSpeed().getxDirection() == SpeedManager.DIRECTION_RIGHT
				&& inEnemy.getX() + inEnemy.getBitmap().getWidth() / 2 >= width) {
			inEnemy.getSpeed().toggleXDirection();
		}

		if (inEnemy.getSpeed().getxDirection() == SpeedManager.DIRECTION_LEFT
				&& inEnemy.getX() - inEnemy.getBitmap().getWidth() / 2 <= 0) {
			inEnemy.getSpeed().toggleXDirection();
		}

		if (inEnemy.getSpeed().getyDirection() == SpeedManager.DIRECTION_UP
				&& inEnemy.getY() + inEnemy.getBitmap().getHeight() / 2 >= height) {
			inEnemy.getSpeed().toggleYDirection();
		}

		if (inEnemy.getSpeed().getyDirection() == SpeedManager.DIRECTION_DOWN
				&& inEnemy.getY() - inEnemy.getBitmap().getHeight() / 2 <= 0) {
			inEnemy.getSpeed().toggleYDirection();
		}
		inEnemy.update( );
		
		
	}
	
	public void genEnemies(){		
		int numOfNewMonster = rand.nextInt(5)+3;
		for(int i=0; i<numOfNewMonster; i++){
			int x = rand.nextInt(width);
			int y = rand.nextInt(height);
			if(i%2==0){
				x=0;
			}else if(i%3==0){
				y=0;
			}else if(i%5==0){
				y=height;
			}else{
				x=width;
			}
			enemies.add(new Ball(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher), x, y, standVelocity));
			long now =System.currentTimeMillis();
			if(now-this.timepassed>30000){
				timepassed =System.currentTimeMillis();
				incPassed =System.currentTimeMillis();
				standVelocity=standVelocity+30;
				inc = true;
			}
		}
	}
	
	public void resumeMoving(){
		for(int i=0; i<this.enemies.size(); i++){
			//restart the time movement
			enemies.get(i).restartTime();
		}
	}
}
