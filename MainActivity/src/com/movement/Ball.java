
 package com.movement;

 import android.graphics.Bitmap;

import android.graphics.Canvas;

public class Ball {

	  private Bitmap bitmap;
	
	  private int x;
	  private int y;
	  private SpeedManager speed;	
	
	  public Ball(Bitmap bitmap, int x, int y) {
	
		  this.bitmap = bitmap;
		
		  this.x = x;
		
		  this.y = y;
		
		  this.speed = new SpeedManager();
	
	  }
	
	  public Bitmap getBitmap() {
	
		  return bitmap;
	
	  }
	
	  public void setBitmap(Bitmap bitmap) {
	
		  this.bitmap = bitmap;
	
	  }
	
	  public int getX() {
	
		  return x;
	
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
	
		 canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
	
	 }
	
	 public void update() {	
	
		 x += (speed.getvelocity() * speed.getxDirection());
	 }

}
