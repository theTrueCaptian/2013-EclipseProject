package maeda.killergame;

import android.graphics.Canvas;
import android.graphics.Paint;

public class RedCircle {
	float x, y;
	float radius;
	int lives = 3;
	
	
	
	public RedCircle(float x, float y, float radius){
		this.x=x;
		this.y=y;
		this.radius = radius;
		//radius = (float) (randgen(50));
	}

	//returns a number between 1(?) and k
	public float randgen(int k){
		return (float) (Math.random()*k);
	}
	
	public void draw(Canvas canvas){
		Paint paint=new Paint();
		paint.setAntiAlias(true);       //for smooth rendering
		paint.setARGB(255, 255, 0, 0);    //setting the paint color
		canvas.drawCircle(x, y, radius, paint);
		paint.setARGB(255, 0, 255, 0);
		canvas.drawText(lives+"", x, y, paint);
	}
}
