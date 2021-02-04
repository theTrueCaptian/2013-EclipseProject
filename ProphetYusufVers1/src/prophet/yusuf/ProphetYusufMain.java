package prophet.yusuf;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;

public class ProphetYusufMain extends Activity implements OnTouchListener{
	
	public enum States{ LOADING, MENU, GRADES, STORY, QUIZ}
	States state = States.LOADING;
	WakeLock wakeLock;
	long lastTouch = System.currentTimeMillis();
	
	//the views needed for the different states
	LoadingScreen load;
	MenuScreen menu;
	Quiz game;
	HighscoreScreen highscore;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");
        
        load = new LoadingScreen(this);
        setContentView(load);
        
        state=States.MENU;
        menu = new MenuScreen(this);
        setContentView(menu);
        menu.setOnTouchListener(this);

        
        //setContentView(R.layout.main);
        
        //StoryReader reader = new StoryReader(this);

        
    }
    
    @Override
	public boolean onTouch(View v, MotionEvent event) {
	
		if(this.state==States.MENU){
			//change screens
			States newState = menu.handleTouches(event);
			long now = System.currentTimeMillis();
			if(newState!=null && now-lastTouch>1000)	{
				this.lastTouch = System.currentTimeMillis();
				state=newState;
				changeStateTo(state);
			}
		}/*else if(this.currState==States.HELP){
			States k = help.handleTouches(event);
			long now = System.currentTimeMillis();
			if(k!=null && now-time1>1000)	{
				time1 = System.currentTimeMillis();
				currState=k;
				changeStateTo(currState);
			}
		}else if(this.currState==States.HIGHSCORE){
			States k = highscore.handleTouches(event);
			long now = System.currentTimeMillis();
			if(k!=null && now-time1>1000)	{
				time1 = System.currentTimeMillis();
				currState=k;
				changeStateTo(currState);
			}
		}*/else if(this.state==States.QUIZ || this.state==States.STORY){
			States k = game.handleTouches(event);
			long now = System.currentTimeMillis();
			if(k!=null  && now-lastTouch>1000)	{
				this.lastTouch = System.currentTimeMillis();
				state=k;
				changeStateTo(state);
			}
		}
		return true;
	}
	
	public void changeStateTo(States instate){
		if(instate==States.MENU){
			menu = new MenuScreen(this);
			setContentView(menu);
	        menu.setOnTouchListener(this);
	        lastTouch = System.currentTimeMillis();
		}else if(instate==States.GRADES){
			highscore = new HighscoreScreen(this);
			setContentView(highscore);
	        highscore.setOnTouchListener(this);
	        lastTouch = System.currentTimeMillis();
		}else if(instate==States.QUIZ){
			System.out.println("CHANING STATE TO QUIZ!!!");
			game = new Quiz(this, "quiz");
			setContentView(game);
	        game.setOnTouchListener(this);
	        lastTouch = System.currentTimeMillis();
		}else if(instate==States.STORY){
			System.out.println("CHANING STATE TO story!!!");
			game = new Quiz(this, "story");
			setContentView(game);
	        game.setOnTouchListener(this);
	        lastTouch = System.currentTimeMillis();
		}
	}

    @Override
    public void onResume() {
        super.onResume();
        wakeLock.acquire();
        /*if(this.currState==States.GAME){
        	game.resume();
        }*/
        
    }

    @Override
    public void onPause() {
        super.onPause();
        wakeLock.release();
        /*if(this.currState==States.GAME){
        	game.pause();
        }*/
        
    }
    
    protected void onDestroy() {
    	super.onDestroy();
    }
  
    protected void onStop() {
    	super.onStop();
    }


}
