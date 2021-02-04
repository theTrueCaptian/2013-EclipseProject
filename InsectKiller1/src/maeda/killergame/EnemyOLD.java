package maeda.killergame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class EnemyOLD {
	float speed;
	float x;
	float y;
	Color color;
	float radius;
	boolean collide = false;
	int lives;
	float slope;
	float intercept;
	int r=0, g=0, b=0;
	
	public EnemyOLD(float x, float slope, float intercept){
		this.x=x;
		this.slope = slope;
		this.intercept = intercept;
		y = calcY();
		
		speed = (float) (randgen(100));
		lives = (int)(randgen(4));
		randColor();
		radius = (float) (randgen(50));
	}
	
	public float calcY(){
		return x*slope+intercept;
	}
	
	public void draw(Canvas canvas){
		Paint paint=new Paint();
		paint.setAntiAlias(true);       //for smooth rendering
		paint.setARGB(255, r, g, b);    //setting the paint color
		canvas.drawCircle(x, y, radius, paint);
	}
	
	private void randColor()
	{
		color = new Color();
		r=(int)(randgen(255));
		g=(int)(randgen(255));
		b=(int)(randgen(255));
	}
	
	//returns a number between 1(?) and k
	private float randgen(int k){
		return (float) (Math.random()*k);
	}
}
