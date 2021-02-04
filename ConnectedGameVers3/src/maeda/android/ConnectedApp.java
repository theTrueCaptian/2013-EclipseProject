package maeda.android;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ConnectedApp {
	private String arabic[] = { 
			"\u0627", "\u0628", "\u062A", "\u062B", "\u062C",
			"\u062D", "\u062E", "\u062F", "\u0630", "\u0631",
			"\u0632", "\u0633", "\u0634", "\u0635", "\u0636",
			"\u0637", "\u0638", "\u0639", "\u063A", "\u0641",
			"\u0642", "\u0643", "\u0644", "\u0645", "\u0646",
			"\u0648", "\u0647", "\uFE80", "\u064A"
		  };
	private int indicator = 1;
	private int[] index;
	private int score = 0;
	private int chance = 3;
	
	Activity mainActivity;
	
	public ConnectedApp(Activity context){
		this.mainActivity = context;
	}
	
	public void OnCreate(DataObject data){
		
		initButtons();
        
        if(data==null){
        	changeMessage("What is the first letter?");
	        updateScoreBoard();        
	        generateWord();
	        	        
        }else{
        	indicator = data.getIndicator();
        	index = data.getIndex();
        	score = data.getScore();
        	chance = data.getChance();
        	
        	updateScoreBoard();        
	        displayWord();
	        gameLoop();
        }
	}
	
	public Object onRetainNonConfigurationInstance(){
    	final DataObject data = new DataObject(indicator, index, score, chance);
    	return data;
    }
	private void changeMessage(String message){
    	TextView tx=(TextView)mainActivity.findViewById(R.id.messageboard);
    	tx.setText(message);
    }
    
    private void generateWord(){
    	//generate a random word
        Random rand = new Random();
        int length = 3;//rand.nextInt(4)+3;
        index = new int[length];
        for(int i=0; i<length; i++){
        	index[i] = rand.nextInt(29);
        }
    	
        displayWord();
    }
   
    private void displayWord(){
    	String word="";
    	//form the string of word
    	for(int i=0; i<index.length; i++){
    		word = word + arabic[index[i]];
    	}
    	TextView tx=(TextView)mainActivity.findViewById(R.id.wordboard);
		try{
			//DejaVuSans.ttf font file
			tx.setTypeface(Typeface.createFromAsset(mainActivity.getAssets(),"DejaVuSans.ttf"));
			//tx.setText("\uFEB3\uFE92\uFE98\uFE94");
			//tx.setText("\u0641\u0631\u0633");
			tx.setText(ArabicUtilities.reshape(word));
		}
		catch(Exception ex){
			tx.setText("font cannot load: "+ ex.toString() );
		}
    }
    
    private boolean isCorrect(int rightLetter, int clickedLetter){
    	String right = arabic[rightLetter];
    	String clicked = boardToUnicode(clickedLetter);
    	if(right.equals(clicked)){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public void onClickAction(View V)
    {
    	//do something when the button is clicked
    	changeMessage("Maeda is awesome like that");
    	//check if correct
    	if(isCorrect(index[indicator-1], V.getId())){
    		toast("Correct");
    		//update indicator
        	indicator++;
        	//update score
        	score++;
        	updateScoreBoard();
        	chance = 3;
    	}else{
			//lose a chance
			chance--;
			//check if player loses all chances
			if(chance==0){
				//display the correct letter
				toast("The correct letter is "+arabic[index[indicator-1]]);
				//increase indicator 
				indicator++;
			}else{
				toast("Wrong");
			}
    	}    		
    	
    	gameLoop();
    }
    
    public void gameLoop(){
    	//change string to ask what the next letter is
    	if(indicator==1){
    		changeMessage("What is the first letter?");
    	}else if(indicator==2){
    		changeMessage("What is the second letter?");
    	}else if(indicator==3 && index.length>=3){
    		changeMessage("What is the third letter?");
    	}else if(indicator==4 && index.length>=4){
    		changeMessage("What is the fourth letter?");
    	}else if(indicator==5 && index.length==5){
    		changeMessage("What is the fifth letter?");
    	}else if(indicator==6 && index.length==6){
    		changeMessage("What is the sixth letter?");
    	}else{
    		indicator = 1;
    		changeMessage("What is the first letter?");
    		generateWord();
    	}
    }
    private void toast(String message){
    	long id=0;
    	try{    		
	    	Context context = mainActivity.getApplicationContext();
			CharSequence text = message;
			int duration = Toast.LENGTH_LONG/2;
			Toast toast = Toast.makeText(context, text, duration);
			toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 20);
			toast.show();
    	}catch (Exception ex){
    		Context context = mainActivity.getApplicationContext();
    		CharSequence text = ex.toString() +"ID = "+id;
    		int duration = 2;//Toast.LENGTH_LONG;
    		Toast toast = Toast.makeText(context, text, duration);
    		toast.show();
    	}
    }
    private void updateScoreBoard(){
    	TextView tx=(TextView)mainActivity.findViewById(R.id.scoreboard);
    	tx.setText("Score:"+score+"             ");
    }
    
   private void initButtons(){
	   ImageButton.OnClickListener listener = new ImageButton.OnClickListener() {
		   @Override public void onClick(View view) {
			   	onClickAction(view);
		   } };
		   ((ImageButton) mainActivity.findViewById(R.id.imageButton1)).setOnClickListener(listener);
		   ((ImageButton) mainActivity.findViewById(R.id.imageButton2)).setOnClickListener(listener);
		   ((ImageButton) mainActivity.findViewById(R.id.imageButton3)).setOnClickListener(listener);
		   ((ImageButton) mainActivity.findViewById(R.id.imageButton4)).setOnClickListener(listener);
		   ((ImageButton) mainActivity.findViewById(R.id.imageButton5)).setOnClickListener(listener);
		   ((ImageButton) mainActivity.findViewById(R.id.imageButton6)).setOnClickListener(listener);
		   ((ImageButton) mainActivity.findViewById(R.id.imageButton7)).setOnClickListener(listener);
		   ((ImageButton) mainActivity.findViewById(R.id.imageButton8)).setOnClickListener(listener);
		   ((ImageButton) mainActivity.findViewById(R.id.imageButton9)).setOnClickListener(listener);
		   ((ImageButton) mainActivity.findViewById(R.id.imageButton10)).setOnClickListener(listener);
		   ((ImageButton) mainActivity.findViewById(R.id.imageButton11)).setOnClickListener(listener);
		   ((ImageButton) mainActivity.findViewById(R.id.imageButton12)).setOnClickListener(listener);
		   ((ImageButton) mainActivity.findViewById(R.id.imageButton13)).setOnClickListener(listener);
		   ((ImageButton) mainActivity.findViewById(R.id.imageButton14)).setOnClickListener(listener);
		   ((ImageButton) mainActivity.findViewById(R.id.imageButton15)).setOnClickListener(listener);
		   ((ImageButton) mainActivity.findViewById(R.id.imageButton16)).setOnClickListener(listener);
		   ((ImageButton) mainActivity.findViewById(R.id.imageButton17)).setOnClickListener(listener);
		   ((ImageButton) mainActivity.findViewById(R.id.imageButton18)).setOnClickListener(listener);
		   ((ImageButton) mainActivity.findViewById(R.id.imageButton19)).setOnClickListener(listener);
		   ((ImageButton) mainActivity.findViewById(R.id.imageButton20)).setOnClickListener(listener);
		   ((ImageButton) mainActivity.findViewById(R.id.imageButton21)).setOnClickListener(listener);
		   ((ImageButton) mainActivity.findViewById(R.id.imageButton22)).setOnClickListener(listener);
		   ((ImageButton) mainActivity.findViewById(R.id.imageButton23)).setOnClickListener(listener);
		   ((ImageButton) mainActivity.findViewById(R.id.imageButton24)).setOnClickListener(listener);
		   ((ImageButton) mainActivity.findViewById(R.id.imageButton25)).setOnClickListener(listener);
		   ((ImageButton) mainActivity.findViewById(R.id.imageButton26)).setOnClickListener(listener);
		   ((ImageButton) mainActivity.findViewById(R.id.imageButton27)).setOnClickListener(listener);
		   ((ImageButton) mainActivity.findViewById(R.id.imageButton28)).setOnClickListener(listener);
		   ((ImageButton) mainActivity.findViewById(R.id.imageButton29)).setOnClickListener(listener);
		   ((ImageButton) mainActivity.findViewById(R.id.imageButton30)).setOnClickListener(listener);
   }
   
    private String boardToUnicode(int clickedLetter){
    	switch(clickedLetter){
    	case R.id.imageButton1:
    		return arabic[0];
    	case R.id.imageButton2:
    		return arabic[1];
    	case R.id.imageButton3:
    		return arabic[2];
    	case R.id.imageButton4:
    		return arabic[3];
    	case R.id.imageButton5:
    		return arabic[4];
    	case R.id.imageButton6:
    		return arabic[5];
    	case R.id.imageButton7:
    		return arabic[6];
    	case R.id.imageButton8:
    		return arabic[7];
    	case R.id.imageButton9:
    		return arabic[8];
    	case R.id.imageButton10:
    		return arabic[9];
    	case R.id.imageButton11:
    		return arabic[10];
    	case R.id.imageButton12:
    		return arabic[11];
    	case R.id.imageButton13:
    		return arabic[12];
    	case R.id.imageButton14:
    		return arabic[13];
    	case R.id.imageButton15:
    		return arabic[14];
    	case R.id.imageButton16:
    		return arabic[15];
    	case R.id.imageButton17:
    		return arabic[16];
    	case R.id.imageButton18:
    		return arabic[17];
    	case R.id.imageButton19:
    		return arabic[18];
    	case R.id.imageButton20:
    		return arabic[19];
    	case R.id.imageButton21:
    		return arabic[20];
    	case R.id.imageButton22:
    		return arabic[21];
    	case R.id.imageButton23:
    		return arabic[22];
    	case R.id.imageButton24:
    		return arabic[23];
    	case R.id.imageButton25:
    		return arabic[24];
    	case R.id.imageButton26:
    		return arabic[25];
    	case R.id.imageButton27:
    		return arabic[26];
    	case R.id.imageButton28:
    		return arabic[27];
    	default :
    		return arabic[28];
    	
    	
    	}
    	
    }
	
    
}

