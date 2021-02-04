package maeda.medeivalmaze.android;

import java.io.IOException;
import java.util.ArrayList;


import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;

public class LevelScene extends MenuScene implements IOnMenuItemClickListener{
	private GameActivity activity;
	private Resources res;
	private XmlResourceParser xpp;
	private ArrayList<Thumbnail> places = new ArrayList<Thumbnail>();
	
	public LevelScene(GameActivity activity){
		super(GameActivity.getSharedInstance().mCamera);
		this.activity = GameActivity.getSharedInstance();
		
		activity.levelsceneloading();
		
		setBackground(new Background(0.20f, 0.6274f, 0.8784f));
		Sprite bgsprite = new Sprite(0, 0,activity.skybg , activity.getVertexBufferObjectManager());
		bgsprite.setSize(activity.width, activity.height);
		this.attachChild(bgsprite );
		
		//decide thumbnail size
		int thumbnailwidth = activity.width/5;
		int thumbnailheight = activity.height/3;
		
		System.out.println("thumbnailwidth:"+thumbnailwidth);
		System.out.println("thumbnailheight:"+thumbnailheight);
		//readlevelsfile
		readItems();
		
		float x=0, y=0;
		//display an array of levels
		int row=0;
		for(int i=0; i<places.size(); i++){
			if(i%5==0){row++;}
			IMenuItem startButton = new TextMenuItem(i, activity.levelfont, places.get(i).name, activity.getVertexBufferObjectManager());
			int col =((i)%5);
			//System.out.println("col:"+col+" row:"+row);
			x = ((col)*(thumbnailwidth))+(thumbnailwidth/4);// + (thumbnailwidth)+(thumbnailwidth/2)+(startButton.getWidth());
			y = ((row)*(thumbnailheight))-(3*thumbnailheight/4) ;//+ (thumbnailheight)+(thumbnailheight/2);
			
			Sprite bg = new Sprite(x,y,activity.thumbnail, activity.getVertexBufferObjectManager());
			bg.setHeight(thumbnailheight-(thumbnailheight/2));
			bg.setWidth(thumbnailwidth-(thumbnailwidth/2));
			this.attachChild(bg);
			
			startButton.setPosition(x, y);
			this.addMenuItem(startButton);	
		}
		setOnMenuItemClickListener(this);
	}
	
	

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {

		//set global variabke
		int input = pMenuItem.getID();
		//set loading text
		GlobalVariables.loadingText = places.get(input).loadingtext;
		//set scene to loading scene to load the correct level and corresponding graphics/sound, etc
		activity.setCurrentScene(new LoadingScene(input));
		return true;
		
	}
	private void readItems(){
		try {
        	res = activity.getResources();
        	xpp = res.getXml(maeda.medievalmaze.android.R.xml.levels);
        	xpp.next();
        } catch (XmlPullParserException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }
		String name="", file="", loadingtext="";
		int eventType;
		try {
			eventType = xpp.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT ){
				if(eventType == XmlPullParser.START_DOCUMENT){
					
				}else if(eventType == XmlPullParser.START_TAG){
					if(xpp.getName().equals("name")){
						eventType = xpp.next();					
						name=xpp.getText();
					}else if(xpp.getName().equals("thumbnail")){
						eventType = xpp.next();					
						file=xpp.getText();							
					}else if(xpp.getName().equals("loadingtext")){
						eventType = xpp.next();					
						loadingtext=xpp.getText();
					}
				}else if(eventType == XmlPullParser.END_TAG){
					if(xpp.getName().equals("level")){
						//add a new thumbnail
						this.places.add(new Thumbnail(name, file, loadingtext));
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
	}
	
	public class Thumbnail{
		String name;
		String file;
		String loadingtext;
		public Thumbnail(String name, String file, String loadingtext){
			this.name = name;
			this.file = file;
			
			this.loadingtext = loadingtext;
		}
	}
	
	
}
