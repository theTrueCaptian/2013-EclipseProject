package maeda.killergame;

import android.graphics.Canvas;

public class Star {
	private int x, y;
	private boolean gone = false;
	private int alternate = 0;
	public Star(int x, int y){
		this.x=x;
		this.y=y;
		
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public void update(int canvasHeight){
		if(!gone)
			if(y+Stuff.star.getHeight()<=canvasHeight)
				y=y+4;
			else
				gone = true;
	}
	
	public void draw(Canvas canvas){
		if(alternate%2==0){
			canvas.drawBitmap(Stuff.star, x,y, null);
			alternate++;
		}else{
			canvas.drawBitmap(Stuff.star2, x,y, null);
			alternate++;
		}
		
	}
	
	public boolean isGone(){
		return gone;
	}
}
