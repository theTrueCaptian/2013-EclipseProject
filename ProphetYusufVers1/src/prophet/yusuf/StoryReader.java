package prophet.yusuf;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import prophet.yusuf.Quiz.GameStates;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;


public class StoryReader {
	
	private Resources res;
	private XmlResourceParser xpp;
	private Quiz game;
	private int eventType;
	//the arraylist to the choices available to the user during question mode
	private ArrayList<String> choices = new ArrayList<String>();
	//index to the correct answer choice
	private int correctIndex;
	private Context context;
	String type;//either quiz or a story type of reader
	
	public StoryReader(Context context, Quiz game, String type){
		this.game = game;
		this.context = context;
		//TextView myXmlContent = (TextView)((Activity) context).findViewById(R.id.my_xml);
        this.type = type;
        try {
        	res = context.getResources();
    		xpp = res.getXml(R.xml.yusuf5);
    		xpp.next();
        	//stringXmlContent = readItem(context);
        	//myXmlContent.setText(stringXmlContent);
        } catch (XmlPullParserException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }
	}
	
		
	public String getChoice(int index){
		return this.choices.get(index);
	}
	
	public int getNumChoices(){
		return choices.size();
	}
	
	public boolean isCorrect(int chosenIndex){
		if(chosenIndex==correctIndex)
			return true;
		else return false;
					
	}
	
	public boolean readItem(){
		try {
			eventType = xpp.getEventType();
			//reset the choices array
			this.choices = new ArrayList<String>();
			while (eventType != XmlPullParser.END_DOCUMENT ){
				if(eventType == XmlPullParser.START_DOCUMENT){
					
				}else if(eventType == XmlPullParser.START_TAG){
					if(xpp.getName().equals("narrate")){
						game.gameState = GameStates.NARRATE;
						eventType = xpp.next();
						game.text = xpp.getText();
					}else if(xpp.getName().equals("question") &&  type.equals("quiz")){
						game.gameState = GameStates.QUESTION;
						eventType = xpp.next();
						game.text = xpp.getText();
					}else if(xpp.getName().equals("answer")){
						game.gameState = GameStates.ANSWER;
						eventType = xpp.next();
						game.text = xpp.getText();
					}else if(xpp.getName().equals("image")){
						//find wihci file has that name and then change the background
						FileIO input = new FileIO(context);
						eventType = xpp.next();
						game.background = input.load(xpp.getText()+"");
					}else if(xpp.getName().equals("wrong")&&  type.equals("quiz")){
						eventType = xpp.next();
						choices.add(xpp.getText());
					}else if(xpp.getName().equals("correct")&&  type.equals("quiz")){						
						eventType = xpp.next();
						choices.add(xpp.getText());
						correctIndex = choices.size() - 1;
					}
				}else if(eventType == XmlPullParser.END_TAG){
					//only stop reading if these end tags are found
					if(xpp.getName().equals("story")){
						return false;
					}
					if(xpp.getName().equals("narrate") ||  xpp.getName().equals("answer") || (xpp.getName().equals("question")&&  type.equals("quiz"))){
						eventType = xpp.next();
						
						break;
					}
				}
				eventType = xpp.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			System.out.println(e.getLocalizedMessage());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getLocalizedMessage());
		}
		return true;
	}
}
