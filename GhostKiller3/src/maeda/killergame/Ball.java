
 package maeda.killergame;

 import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import android.graphics.Canvas;

public class Ball {

	  private Bitmap bitmap;
	
	  private float x;
	  private float y;
	  private SpeedManager speed;	
	  int r=0, g=0, b=0;
	  boolean collide = false;
	  float radius;
	  Color color;
	  long lastMovementUpdate =0;
	
	  public Ball(Bitmap bitmap, int x, int y, int velocity) {
	
		  this.bitmap = bitmap;
		
		  this.x = x;
		
		  this.y = y;
		
		  this.speed = new SpeedManager(this.randgen(5)+velocity,0);
		  randColor();
		  radius = (float) (randgen(15)+30.0);
		  
	
	  }
	
	  public Bitmap getBitmap() {
	
		  return bitmap;
	
	  }
	
	  public void setBitmap(Bitmap bitmap) {
	
		  this.bitmap = bitmap;
	
	  }
	
	  public float getX() {
	
		  return x;
	
	  }
	  
	  public float getY() {
			
		  return y;
	
	  }
	
	  public void setX(int x) {
	
		  this.x = x;
	
	  }
	
	 public SpeedManager getSpeed() {
	
		 return speed;
	 
	 }
	
	 public void setSpeed(SpeedManager speed) {
	
		 this.speed = speed;
	
	 }
	
	 public void draw(Canvas canvas) {
		 //canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
		 Paint paint=new Paint();
		 paint.setAntiAlias(true);       //for smooth rendering
		 paint.setARGB(255, 0, 0, 255);    //setting the paint color
		 
		 canvas.drawCircle(x, y, radius, paint);
		 paint.setARGB(255, 0, 0, 0);    //setting the paint color
		 canvas.drawCircle(x, y, radius-3, paint);
	 }
	
	 private void randColor()
	 {
		 color = new Color();
		 r=(int)(randgen(155/2)+100);
		 g=(int)(randgen(155)+100);
		 b=(int)(randgen(155)+100);
	 }
	 
	//returns a number between 1(?) and k
	 private float randgen(int k){
		 return (float) (Math.random()*k);
	 }
	 
	 public void update() {	
		 if(lastMovementUpdate==0){
			 lastMovementUpdate = System.currentTimeMillis()-1;
		 }
	
		 x += (speed.getvelocity() * speed.getxDirection()*(System.currentTimeMillis()-lastMovementUpdate)/1000);
		 y += (speed.getvelocity() * speed.getyDirection()*(System.currentTimeMillis()-lastMovementUpdate)/1000);
		 lastMovementUpdate =System.currentTimeMillis();
	 }
	 
	 public void restartTime(){
		 lastMovementUpdate =System.currentTimeMillis();
	 }
	 
}
