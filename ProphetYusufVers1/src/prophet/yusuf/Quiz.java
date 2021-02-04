package prophet.yusuf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import prophet.yusuf.ProphetYusufMain.States;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class Quiz extends  View {
	
	public enum GameStates{NARRATE, QUESTION, ANSWER, PAUSE}
	
	GameStates gameState = GameStates.NARRATE;
	
	int grade = 0;
	Context context;
	int width, height;
	
	long lastTouch = System.currentTimeMillis();
	Bitmap background;
	String text="";
	
	Paint paint;
	
	StoryReader reader;
	String type;
	
	
	public Quiz(Context context, String type) {
		super(context);
		this.context = context;
		this.type = type;
		
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		width = metrics.widthPixels;
		height = metrics.heightPixels;
		
		Stuff.background1 = getResizedBitmap(Stuff.background1,  height, width);
		//Stuff.background2 = getResizedBitmap(Stuff.background2,  height, width);
		Stuff.parchment = getResizedBitmap(Stuff.parchment,  height*1/3, width);
		background = Stuff.background1;
		
		/*mSoundManager = new SoundManager();
        mSoundManager.initSounds(((ContextWrapper) context).getBaseContext());
        mSoundManager.addSound(1, Stuff.explosion);
        mSoundManager.playSound(1);*/
		paint=new Paint();
		paint.setAntiAlias(true);       //for smooth rendering
		paint.setTypeface(Typeface.createFromAsset(context.getAssets(),"sketchflow.ttf"));
		
		reader = new StoryReader(context, this, type);
		//reader.readItem();
		invalidate();
	}
	
	public void pause(){
		System.out.println("PAUSEDD");
		this.gameState = GameStates.PAUSE;
		
	}
	
	public void resume(){
		System.out.println("RESUMMEDDD");
		this.gameState = GameStates.NARRATE;
	
	}

	public void draw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);		
		//draw the basic layout despite the state
		background = getResizedBitmap(background,  /*height*2/3*/height, width);
		canvas.drawBitmap(background, 0, 0, null);
		canvas.drawBitmap(Stuff.parchment, 0,height*2/3, null);
		
		//draw grades on the top		
		paint.setARGB(255, 0, 255, 0); //setting the paint color
		paint.setTextSize(height/20);
	
		canvas.drawText("Points: "+grade, 15,15, paint);
		
		paint.setARGB(255, 255, 255, 255);
		if(gameState==GameStates.NARRATE){
			//tdefine the lines to display in the parchment
			ArrayList<String> lines = truncate(text, /*(width-width/3)/5*/(int)((width-60)/(paint.getTextSize()/2)));
			for(int i=0; i<lines.size(); i++){//for loop to display each of the lines in the box
				canvas.drawText(lines.get(i), 30,height*2/3+paint.getTextSize()*i+paint.getTextSize(), paint);
			}
		}else if(gameState==GameStates.ANSWER){
			//tdefine the lines to display in the parchment
			ArrayList<String> lines = truncate(text, /*(width-width/3)/5*/(int)((width-60)/(paint.getTextSize()/2)));
			for(int i=0; i<lines.size(); i++){//for loop to display each of the lines in the box
				canvas.drawText(lines.get(i), 30,height*2/3+paint.getTextSize()*i+paint.getTextSize(), paint);
			}
		}else if(gameState==GameStates.QUESTION && type.equals("quiz")){
			//draw the choice buttons
			Stuff.choicebutton = getResizedBitmap(Stuff.choicebutton,  height*1/5, width*2/3);
			canvas.drawBitmap(Stuff.choicebutton, width*1/6, height*1/5, null);
			canvas.drawBitmap(Stuff.choicebutton, width*1/6, height*2/5, null);
			canvas.drawBitmap(Stuff.choicebutton, width*1/6, height*3/5, null);
			
			//ask the question
			canvas.drawText(clean(text), 30,/*paint.getTextSize()*2*/height*4/5+paint.getTextSize(), paint);
			
			
			for(int i=0; i<reader.getNumChoices(); i++){//the for loop to print the three choices
				paint.setARGB(255, 255, 255, 255);			
				//define the lines for each button
				ArrayList<String> lines = truncate(reader.getChoice(i), /*(width-width/2)/10*/(int)((width*2/3-10)/(paint.getTextSize()/2)));
				for(int j=0; j<lines.size(); j++){//the for loop to print each line in the display box
					canvas.drawText(lines.get(j), width*1/2-lines.get(j).length()*3, height*(i+1)/5+30+paint.getTextSize()*j, paint);
				}
			}
		}else if(gameState==GameStates.PAUSE){
			canvas.drawText("Paused.", 50, 70, paint);
			
		}
	}
	
	private String clean(String instring){
		String newString="";
		if(instring.length()>0){
			for(int i=0; i<instring.length(); i++){
				if(instring.charAt(i)!='\n' && instring.charAt(i)!='\t'){
					newString=newString+instring.charAt(i);
				}
			}
			return newString;
		}else
			return instring;
	}
	
	private ArrayList<String> truncate(String inString, int maxNumOfChars){
		inString = clean(inString);
		ArrayList<String> truncated = new ArrayList<String>();
		if(inString.length()<maxNumOfChars){
			truncated.add(inString);
			System.out.println("theres only one line "+inString);
			return truncated;
		}
		System.out.println("maxnumofchars:"+maxNumOfChars);
		StringTokenizer tokenizer = new StringTokenizer(inString);
		String line="";
		while(tokenizer.hasMoreTokens()){
			String word = tokenizer.nextToken(" ");
			//System.out.println("word:"+word);
			//if the the wword plus the parsed line is less than the maxlinelength, then add the word
			if(word.length()+line.length()<maxNumOfChars){
				line=line+word+" ";
				
			}else{//else add the line into truncated and make a new line
				//System.out.println("line:"+line);
				truncated.add(line);
				line = ""+word+" ";
				
			}
		}
	
		//only add a line if there are remainings left
		if(truncated.size()>0)
			if(!truncated.get(truncated.size()-1).equals(line))	{
				truncated.add(line);
				System.out.println("line added:"+line);
			}
		return truncated;
	}
	
	//returns a States type
	public States handleTouches(MotionEvent evt) {
		float x= evt.getX();
		float y = evt.getY();	
		
		//first check if it hits any buttons 
		//afterwards read xml file to go to determine the next one
		long now = System.currentTimeMillis();
		if(now-lastTouch>1000){
			
			//check if the right choice i made in question mode
			
			if(this.gameState==GameStates.QUESTION){
				int chosenIndex=-1;
				if(x>width*1/6 && y>height*1/5 && x<width*5/6 && y<height*2/5-3){//first chice chosen
					chosenIndex = 0;
				}else if(x>width*1/6 && y>height*2/5 && x<width*5/6 && y<height*3/5-3){//2 chice chosen
					chosenIndex = 1;
				}else if(x>width*1/6 && y>height*3/5 && x<width*5/6 && y<height*4/5-3){//3 chice chosen
					chosenIndex = 2;
				}
				if(reader.isCorrect(chosenIndex)){
					//reward the dude with a taost with a good message and increase points
					this.toast("Correct!");
					this.grade++;
				}else{
					this.toast("Wrong.");
					//this.grade--;
				}
			}
			
			if(this.gameState==GameStates.ANSWER){
				
			}
			
			//read xml\	
			//this is the only time when the game changes from the quiz state to another quiz state
			if(!this.reader.readItem()){
				return States.MENU;
			}
				
			
			
			this.lastTouch = System.currentTimeMillis();
		}
		
		invalidate();
		return null;
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
	
	
	public boolean isPaused(){
		if(gameState==GameStates.PAUSE)	{	
			return true;
		}else
			return false;
	}
	/*
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
	*/
	private void toast(String message){
    	long id=0;
    	try{    		
	    	//Context context = mainActivity.getApplicationContext();
			CharSequence text = message;
			int duration = Toast.LENGTH_LONG/2;
			Toast toast = Toast.makeText(context, text, duration);
			toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 20);
			toast.show();
    	}catch (Exception ex){
    		//Context context = mainActivity.getApplicationContext();
    		CharSequence text = ex.toString() +"ID = "+id;
    		int duration = 2;//Toast.LENGTH_LONG;
    		Toast toast = Toast.makeText(context, text, duration);
    		toast.show();
    	}
    }
	private void writeTextFile( String file)  {
		BufferedWriter out = null;
		String externalStoragePath = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(externalStoragePath + file)));
            for (int i = 0; i < Stuff.grades.size(); i++) {
                out.write(Stuff.grades.get(i)+"\n");
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