package maeda.android;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
public class DrawingView extends View implements OnTouchListener{

	static int r=255,g=0,b=0;
	final static int radius=3;
	Paint paint;     //using this ,we can draw on canvas
	ArrayList<Node> dots = new ArrayList<Node>();
	
	public DrawingView(Context context)
	{
		super(context);
	    paint=new Paint();
		paint.setAntiAlias(true);       //for smooth rendering
		paint.setARGB(255, r, g, b);    //setting the paint color
		
		//to make it focusable so that it will receive touch events properly
		setFocusable(true);

		//adding touch listener to this view
		this.setOnTouchListener(this);
	}

	//overriding the View's onDraw(..) method
	public void onDraw(Canvas canvas)
	{
		paint.setARGB(255, r, g, b);

		//drawing the dots
		for(int i=0; i<dots.size();i++){
			canvas.drawCircle(dots.get(i).getX(),dots.get(i).getX(),radius,paint);
		}
		

	}

	//this is the interface method of "OnTouchListener"
	public boolean onTouch(View view,MotionEvent event)
	{
		
		dots.add(new Node(event.getX(),      //some math logic to plot the circle  in exact touch place
						  event.getY()));
		  System.out.println("X,Y:"+event.getX()+","+event.getY());      //see this output in "LogCat"
		//randColor();       //calls this method to generate a color before drawing
		invalidate();      //calls onDraw method
		return true;
	}

	//this method sets a random color using Math.random()
	//Note: RGB color values ranges from 0 to 255..
	public void randColor()
	{
		r=(int)(Math.random()*255);
		g=(int)(Math.random()*255);
		b=(int)(Math.random()*255);
		//Toast.makeText(c, "r,g,b="+r+","+g+","+b,Toast.LENGTH_SHORT).show();
	}
}
