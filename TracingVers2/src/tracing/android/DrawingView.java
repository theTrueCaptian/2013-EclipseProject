package tracing.android;

import java.util.ArrayList;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
//import android.view.View.OnDragListener;
public class DrawingView extends View implements OnTouchListener{

	static int r=0,g=255,b=0;
	final static int radius=10;
	Paint paint;     //using this ,we can draw on canvas
	Context context;
	ArrayList<Node> dots = new ArrayList<Node>();
	Bitmap next;
	View realView;
	long lastTimeTouch=0;
	Bitmap prev = null; 
	 
	int[] picArr = {R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4, R.drawable.p5, R.drawable.p6,
			R.drawable.p7, R.drawable.p8, R.drawable.p9, R.drawable.p10, R.drawable.p11, R.drawable.p12,
			R.drawable.p13, R.drawable.p14, R.drawable.p15, R.drawable.p16, R.drawable.p17, R.drawable.p18,
			R.drawable.p19, R.drawable.p20, R.drawable.p21, R.drawable.p22, R.drawable.p23, R.drawable.p24,
			R.drawable.p25, R.drawable.p26, R.drawable.p27, R.drawable.p28, R.drawable.p29/*, R.drawable.pic30*/};
	int ctr=0;
	
	public DrawingView(Context context, View realView)
	{
		super(context);
		this.context=context;
		//this.realView = realView;
		//realView = this;
		
	    paint=new Paint();
		paint.setAntiAlias(true);       //for smooth rendering
		paint.setARGB(255, r, g, b);    //setting the paint color
		
		//to make it focusable so that it will receive touch events properly
		this.setFocusable(true);

		//adding touch listener to the real view
		this.setOnTouchListener(this);
		
		initButton();
	}
	
	//overriding the View's onDraw(..) method
	public void onDraw(Canvas canvas)
	{
		paint.setARGB(255, r, g, b);
		
		
		//draw letter
		Bitmap scratch = BitmapFactory.decodeResource(getResources(), this.picArr[ctr]);
    	scratch = getResizedBitmap(scratch, canvas.getHeight()-40, canvas.getWidth()-40);
    	
    	canvas.drawColor(Color.BLACK);
    	canvas.drawBitmap(scratch, 0, 0, null);
    	
		//drawing the dots
		for(int i=0; i<dots.size()-1;i++){
			canvas.drawCircle(dots.get(i).getX(),dots.get(i).getY(),radius,paint);
		}
		//System.out.println("hieght:"+canvas.getHeight()+" width:"+canvas.getWidth());
		//realView.draw(canvas);
		
		Bitmap next; 
		if(ctr<picArr.length-1){
			next = BitmapFactory.decodeResource(getResources(), picArr[ctr+1]);
		}else{
			next = BitmapFactory.decodeResource(getResources(), picArr[0]);
		}
		
		next = getResizedBitmap(next, 100, 100);
		canvas.drawBitmap(next, (canvas.getWidth()-100),(canvas.getHeight()-100),  null);
		
		
		if(ctr==0){
			prev = BitmapFactory.decodeResource(getResources(), picArr[picArr.length-1]);
		}else{
			prev = BitmapFactory.decodeResource(getResources(), picArr[ctr-1]);
		}
		
		prev = getResizedBitmap(prev, 100, 100);
		canvas.drawBitmap(prev, 10,(canvas.getHeight()-100),  null);
		
		Paint textPaint = new Paint();
		textPaint.setARGB(255, r, g, b);
		textPaint.setTextSize(20);
		//draw the tinstruction
		canvas.drawText("Trace over the Arabic letter.", 10, 15, textPaint);
		canvas.drawText("Next", canvas.getWidth()-100, canvas.getHeight()-100, textPaint);
		canvas.drawText("Previous", 10, canvas.getHeight()-100, textPaint);
		
	}

	//this is the interface method of "OnTouchListener"
	public boolean onTouch(View view,MotionEvent event)	{
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		long now = System.currentTimeMillis();
		if(now-this.lastTimeTouch>1000){
			this.lastTimeTouch = System.currentTimeMillis();
			if(event.getX()>=width-125 && event.getY()>=height-125){
				if(ctr<picArr.length-1){
					ctr++;
				}else{
					ctr=0;
				}
				dots.clear();
			}
			if(prev!=null)
				if(event.getX()<=prev.getWidth() && event.getY()>=height-125){
					if(ctr==0){
						ctr=picArr.length-1;
					}else{
						ctr--;
					}
					dots.clear();
				}
		}
		for(int i=0; i<event.getHistorySize()-1; i++){
			dots.add(new Node(event.getHistoricalX(i), event.getHistoricalY(i)));
		}

		dots.add(new Node(event.getX(),      //some math logic to plot the circle  in exact touch place
				event.getY()));
		//System.out.println("X,Y:"+event.getX()+","+event.getY());      //see this output in "LogCat"

		invalidate();      //calls onDraw method
		return true;
	}
	
	public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// create a matrix for the manipulation
		Matrix matrix = new Matrix();
		// resize the bit map
		matrix.postScale(scaleWidth, scaleHeight);
		// recreate the new Bitmap
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
		return resizedBitmap;
	}

	public void initButton(){
		//Button button = new Button(context);
	
	}
}
