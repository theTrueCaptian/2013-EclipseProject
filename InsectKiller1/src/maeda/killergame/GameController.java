package maeda.killergame;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import maeda.killergame.InsectKiller.States;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameController extends  SurfaceView implements SurfaceHolder.Callback {
	
	public enum GameStates{READY, PLAY, RESUME, PAUSE, GAMEOVER}
	private GameThread thread;
	
	GameStates gameState = GameStates.READY;
	
	int score = 0;
	Context context;
	int width, height;
	
	long enemyRelease = System.currentTimeMillis();
	long changeState = System.currentTimeMillis();
	long durationRelease = 2000;
	
	ReddieManager reddie;
	EnemyManager enemy;
	
	boolean highscore = false;
	
	SoundManager mSoundManager;
	
	public GameController(Context context) {
		super(context);
		this.context = context;
		getHolder().addCallback(this);
		
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		width = metrics.widthPixels;
		height = metrics.heightPixels;
		
		reddie = new ReddieManager(context);
		enemy = new EnemyManager(context, reddie);
		
		thread = new GameThread(getHolder(), this);
		setFocusable(true);
		
		/*mSoundManager = new SoundManager();
        mSoundManager.initSounds(((ContextWrapper) context).getBaseContext());
        mSoundManager.addSound(1, Stuff.explosion);
        mSoundManager.playSound(1);*/
		
		
	}
	
	public void pause(){
		System.out.println("PAUSEDD");
		//thread.interrupt();
		this.gameState = GameStates.PAUSE;
		this.thread.setRunning(false);
		
		
	}
	public void resume(){
		System.out.println("RESUMMEDDD");
		//thread.resume();
		this.gameState = GameStates.PLAY;
		//because of bug, if gameover dont go back to play
		if(this.reddie.gameover){
			this.gameState = GameStates.GAMEOVER;
		}
		///this.thread.setRunning(true);
		//this.thread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,	int height) {
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if(!thread.isRunning()){
			thread = null;
			thread = new GameThread(getHolder(), this);
			thread.setRunning(true);
			thread.start();
		}
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {

			}
		}

	}	

	public void render(Canvas canvas) {
		canvas.drawColor(Color.BLACK);		
		//draw the basic layout despite the state

		//display our buddies reddie		
		reddie.draw(canvas);
				
		//draw scores on the top
		Paint paint=new Paint();
		paint.setAntiAlias(true);       //for smooth rendering
		paint.setARGB(255, 255, 0, 0);    //setting the paint color
		//Stuff.scoreingame = getResizedBitmap(Stuff.scoreingame,  height/15, Stuff.scoreingame.getWidth());
		//canvas.drawBitmap(Stuff.scoreingame, 0, 0, null);
		paint.setARGB(255, 0, 255, 0); //setting the paint color
		paint.setTextSize(height/15);
		canvas.drawText("Score: "+score+" ", /*Stuff.scoreingame.getWidth(), 15*/15,15, paint);
		
		if(gameState==GameStates.READY){
			//display message to tap
			canvas.drawBitmap(Stuff.taptobegin, width/2-Stuff.taptobegin.getWidth()/2, height/2-Stuff.taptobegin.getHeight()/2, null);
		}else if(gameState==GameStates.PLAY){
			//draw the pause button and all the enemies
			Stuff.pausebutton = getResizedBitmap(Stuff.pausebutton,  height/15, Stuff.pausebutton.getWidth());
			canvas.drawBitmap(Stuff.pausebutton, width-Stuff.pausebutton.getWidth(), 0, null);
			//draw thine enemy
			//ballInstance.draw(canvas);
			enemy.draw(canvas);
			
		}else if(gameState==GameStates.PAUSE){
			//draw resume button over this
			Stuff.resumebutton = getResizedBitmap(Stuff.resumebutton,  height/15, Stuff.resumebutton.getWidth());
			canvas.drawBitmap(Stuff.resumebutton, width-Stuff.resumebutton.getWidth(), 0, null);
			paint.setARGB(255, 0, 255, 0);    //setting the paint color
			paint.setTextSize(height/10);
			//canvas.drawText("PAUSED", width/3, height/3, paint);
			
			Stuff.pauseingame = getResizedBitmap(Stuff.pauseingame,  height/3, width/2);
			canvas.drawBitmap(Stuff.pauseingame, width/2-Stuff.pauseingame.getWidth()/2, height/2-Stuff.pauseingame.getHeight()/2, null);
		}else if(gameState==GameStates.GAMEOVER){
		
			canvas.drawBitmap(Stuff.gameover, width/2-Stuff.gameover.getWidth()/2, height/2-Stuff.gameover.getHeight()/2, null);
			if(highscore){
				paint.setARGB(255, 0, 255, 0);    //setting the paint color
				paint.setTextSize(height/10);
				canvas.drawBitmap(Stuff.highscoreacheived, width/2-Stuff.highscore.getWidth()/2-5, 0, paint);
				//System.out.println("HIGHSCORE ACHEIVED");
			}
			
		}
		
		
	}
	
	//returns a States type
	public States handleTouches(MotionEvent evt) {
		long now = System.currentTimeMillis();
		if(gameState==GameStates.READY && now-changeState>1000)	{
			changeState = System.currentTimeMillis();		
			//only change to Play state
			gameState=GameStates.PLAY;
		}else if(gameState==GameStates.PLAY && now-changeState>1000){
			//check if hit an enemy and update the score
			score = score+ enemy.killEnemy(evt.getX(), evt.getY());
				
			//check if press pause
			if(evt.getX()>width-Stuff.pausebutton.getWidth() && evt.getX()<width && evt.getY()>0 & evt.getY()<height/15){
				//change state to resume
				changeState = System.currentTimeMillis();	
				this.enemyRelease = System.currentTimeMillis();
				gameState=GameStates.PAUSE;
				
			}
		}else if(gameState==GameStates.PAUSE && now-changeState>1000){	
			//check if press resume
			if(evt.getX()>width-Stuff.resumebutton.getWidth() && evt.getX()<width && evt.getY()>0 & evt.getY()<height/15){
				//change state to play
				changeState = System.currentTimeMillis();	
				gameState=GameStates.PLAY;
				//allow them balls to move
				enemy.resumeMoving();
				
			}
		}else if(gameState==GameStates.GAMEOVER && now-changeState>3000){
			changeState = System.currentTimeMillis();		
		
			//return to menu
			return States.MENU;
		}
		
		invalidate();
		return null;
	}

	public void update() {
		if(this.gameState==GameStates.PLAY){
			long now = System.currentTimeMillis();
			//gen new enemies when enough time passes
			if(now-enemyRelease>durationRelease){
				durationRelease = 3000;
				enemyRelease = System.currentTimeMillis();
				enemy.genEnemies();
			}		
			
			//check if an enemy hit a reddie
			if(this.reddie.reddies.size()!=0){
				collision();
			}
			
			enemy.proceedEnemy();
		}
		
	}
	
	public void collision(){
		for(int i=0; i<enemy.enemies.size(); i++){
			for(int k=0; k<reddie.reddies.size(); k++){
				if (this.isCollide(enemy.enemies.get(i).getX(), enemy.enemies.get(i).getY(), enemy.enemies.get(i).radius, reddie.reddies.get(k).x, reddie.reddies.get(k).y, reddie.reddies.get(k).radius)) {
			        //System.out.println("Collided or just touching");
			        enemy.enemykilled(i);
			        reddie.wounded(k);
			        if(reddie.reddies.get(k).lives==0){
			        	reddie.killed(k);
			        	k--;
			        	if(k<0)	break;     //no need to continue if there are no more reddies to check on 	
			        }
			        i--;
			        if(i<0 || k<0) break; //no more checking if all enemies are gone
			    } 
			}
		}
		if(reddie.gameover || reddie.reddies.size()<=0){ //if our buddy says gameover, we are done
			this.gameState = GameStates.GAMEOVER;
			//a change in states so update the time tracker
			this.changeState = System.currentTimeMillis();
			updateHighscore();
		}
	}
	
	public boolean isCollide(float x1, float y1, float rad1, float x2, float y2, float rad2){
		//calculate distance
		float dxSq = (x1 - x2) * (x1 - x2);
		float dySq = (y1 - y2) * (y1 - y2);
		int d = (int) Math.sqrt(dxSq + dySq);
		
		int r1Pr2 = (int) (rad1 + rad2);
	    if (d <= r1Pr2) {
	        //System.out.println("Collided or just touching");
	        return true;
	    }else{
	    	return false;
	    }
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
	
	public boolean isGameover(){
		long now = System.currentTimeMillis();
		if(gameState==GameStates.GAMEOVER && now-changeState>3000)	{	
			
			return true;
		}else
			return false;
	}
	public boolean isPaused(){
		if(gameState==GameStates.PAUSE)	{	
			return true;
		}else
			return false;
	}
	
	public void updateHighscore(){
		if(Stuff.topscores!=null){
			for(int i=0; i<Stuff.topscores.length; i++){
				if(score>=Stuff.topscores[i]){
					
					for(int j=Stuff.topscores.length-1; j>i; j--){
						Stuff.topscores[j] = Stuff.topscores[j-1];
					}
					Stuff.topscores[i]=score;
					highscore = true;
					//do a little i/o by sending the new results to the file 
					writeTextFile(Stuff.topscores, "scores.txt");
					
					break;
				}
			}
		}else{
			Stuff.topscores = new int[5];
			Stuff.topscores[0] = score;
        	for(int i=1; i<Stuff.topscores.length; i++){
        		Stuff.topscores[i] = 0;
        	}
        	highscore = true;
		}
	}
	
	private void writeTextFile(int[] scores, String file)  {
		BufferedWriter out = null;
		String externalStoragePath = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(externalStoragePath + file)));
            for (int i = 0; i < scores.length; i++) {
                out.write(scores[i]+" ");
                //out.write("\n");
            }
        } catch (IOException e) {
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
            }
        }
		
	}
}