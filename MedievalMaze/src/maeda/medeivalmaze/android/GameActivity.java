package maeda.medeivalmaze.android;

import java.io.IOException;

import maeda.medeivalmaze.android.LevelScene.Thumbnail;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.badlogic.gdx.math.Vector2;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.util.DisplayMetrics;

public class GameActivity extends SimpleBaseGameActivity{
	public int WORLDWIDTH = 1000, WORLDHEIGHT=500;
	public int SCORE = 0;
	
	//A reference to the current scene
	public Scene mCurrentScene = null;
	public static GameActivity instance;
	
	//esstential varibles
	public Font mfont;
	public Font levelfont;
	public Font scorehudFont;
	public int levelChosen = 1;
	
	//camera stuff
	//public Camera mCamera;
	public BoundCamera mCamera;
	
	//screen height and width
	public int height=0, width=0;
	
	private Resources res;
	private XmlResourceParser xpp;
	
	
	//creating the engine
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		 instance = this;
		
		 DisplayMetrics displaymetrics = new DisplayMetrics();
		 getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		 this.height = displaymetrics.heightPixels;
		 this.width = displaymetrics.widthPixels;
		 
	   	 //mCamera = new Camera(0, 0, this.width, this.height);
		 this.mCamera = new BoundCamera(0, 0, this.width	, this.height);
		 this.mCamera.setBounds(0, 0, 2000, 700);
		 this.mCamera.setBoundsEnabled(true);
	   	 
	   	 return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(this.width, this.height), mCamera);

	 }

	//the methods here relate to loading scne**************************************************************************
	@Override
	protected Scene onCreateScene() {
		mEngine.registerUpdateHandler(new FPSLogger());
		
		
		
				
		mCurrentScene = new LevelScene(this);//new LoadingScene();
			
		return mCurrentScene;
	}
	
	public static GameActivity getSharedInstance() {
		return instance;
	}
	public Engine getEngine(){
		return this.mEngine;
	}
	
	// to change the current main scene

	public void setCurrentScene(Scene scene) {
	    mCurrentScene = scene;
	    getEngine().setScene(mCurrentScene);
	}
	
	@Override
    protected void onResume(){
	   	super.onResume();
	   	if(this.mCurrentScene!=null)
	   		this.setCurrentScene(new LevelScene(this));
    }
	
	@Override
	public void onPauseGame() {
		super.onPauseGame();
	}

	//all methods from here relate to loading resources**********************************************************
	@Override
	protected void onCreateResources() {
		
		
	}
	
	public void loadSounds() {
		// TODO Auto-generated method stub
		
	}

	public void loadFonts() {
		mfont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		mfont.load();
		
		this.scorehudFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 20);
		this.scorehudFont.load();
	}

	public void loadGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "face_box.png", 0, 0);
		this.mCircleFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "face_circle_tiled.png", 0, 32, 2, 1); // 64x32
		this.bananaTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "banana_tiled.png", 0, 70, 4, 2); 
		this.actionbutton = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "actionctrl_tiled.png", 0, 64, 2, 1); // 128x64
		this.mBitmapTextureAtlas.load();

		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		this.mPlayerSword = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "sword.png", 0, 0);
		this.mBitmapTextureAtlas.load();
		
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		this.grassRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "grass.png", 0, 0);
		this.mBitmapTextureAtlas.load();
		
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 1056, 1056, TextureOptions.BILINEAR);
		this.skybg = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "skybg.png", 0, 0);
		this.mBitmapTextureAtlas.load();
		
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		this.enemyfort = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "enemyfort.png", 0, 0);		
		this.mBitmapTextureAtlas.load();
		
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		this.mKnightTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "knight.png", 0, 1, 3, 2); // 582x529
		this.mBitmapTextureAtlas.load();
		
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		this.enemyball1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "enemyball.png", 0, 0); 
		this.mBitmapTextureAtlas.load();
		
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		this.enemyball2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "enemyball2.png", 0, 0); 
		this.mBitmapTextureAtlas.load();
		
		this.mOnScreenControlTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 128, TextureOptions.BILINEAR);
		this.mOnScreenControlBaseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, this, "onscreen_control_base.png", 0, 0);
		this.mOnScreenControlKnobTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, this, "onscreen_control_knob.png", 128, 0);
		this.mOnScreenControlTexture.load();
		
	}
	
	//loading for level scene is seperate from the game scene(which are the above methods)
	public void levelsceneloading(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 1056, 1056, TextureOptions.BILINEAR);
		this.skybg = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "skybg.png", 0, 0);
		this.mBitmapTextureAtlas.load();
		
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 1056, 1056, TextureOptions.BILINEAR);
		this.thumbnail = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "thumbnail.png", 0, 0);
		this.mBitmapTextureAtlas.load();
		
		levelfont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), this.height/30);
		levelfont.load();
	}
	
	//this is sdone in the gamescene to load straight from the level xml file
	public void loadlevel(int level){
		try {
        	res = this.getResources();
        	xpp = res.getXml(maeda.medievalmaze.android.R.xml.levels);
        	xpp.next();
        } catch (XmlPullParserException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }
		String name="", file="", loadingtext="";
		int ctr = 0;
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
	
	public BitmapTextureAtlas mBitmapTextureAtlas;
	public ITextureRegion mFaceTextureRegion;
	public ITextureRegion grassRegion;
	public TiledTextureRegion mCircleFaceTextureRegion;
	public TiledTextureRegion bananaTextureRegion;
	public TiledTextureRegion mKnightTextureRegion;

	public BitmapTextureAtlas mOnScreenControlTexture;
	public ITextureRegion mOnScreenControlBaseTextureRegion;
	public ITextureRegion mOnScreenControlKnobTextureRegion;
	public ITiledTextureRegion actionbutton;
	
	public ITextureRegion mPlayerSword;
	public ITextureRegion skybg;
	public ITextureRegion thumbnail;
	public ITextureRegion enemyfort;
	public ITextureRegion enemyball1, enemyball2;
}
