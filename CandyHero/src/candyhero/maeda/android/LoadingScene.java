package candyhero.maeda.android;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;

import candyhero.maeda.android.GameActivity;


import android.graphics.Color;


public class LoadingScene extends Scene{
	private GameActivity activity;
	private Text loading;
	private BitmapTextureAtlas mBitmapTextureAtlas;
	
	public LoadingScene(){
		activity = GameActivity.getSharedInstance();
		
		//this.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 1056, 1056, TextureOptions.BILINEAR);
		activity.skybg = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, activity, "skybg.png", 0, 0);
		this.mBitmapTextureAtlas.load();
		
		Sprite bgsprite = new Sprite(0, 0,activity.skybg , activity.getVertexBufferObjectManager());
		bgsprite.setSize(activity.width, activity.height);
		this.attachChild(bgsprite );
		
		FontFactory.setAssetBasePath("font/");
		Font loadfont = FontFactory.createFromAsset(activity.getFontManager(), activity.getTextureManager(), 512, 512, TextureOptions.BILINEAR, activity.getAssets(), "Plok.ttf", 32, true, Color.BLACK);
		loadfont.load();
		
		loading = new Text(0, 0, loadfont, "Loading...", activity.getVertexBufferObjectManager());		
		loading.setPosition(activity.mCamera.getWidth()/2-loading.getWidth()/2, activity.mCamera.getHeight()/2-loading.getHeight());		
		this.attachChild(loading);
		
		Font textfont = FontFactory.createFromAsset(activity.getFontManager(), activity.getTextureManager(), 512, 512, TextureOptions.BILINEAR, activity.getAssets(), "Plok.ttf", 20, true, Color.BLACK);
		textfont.load();
		Text ins = new Text(0, 0, textfont, "    Tap on the flying \n objects before they reach \n      Mr. Knight!", activity.getVertexBufferObjectManager());		
		ins.setPosition(activity.mCamera.getWidth()/2-ins.getWidth()/2, activity.mCamera.getHeight()-ins.getHeight()-10);		
		this.attachChild(ins);
		
		activity.loadGraphics();
		activity.loadFonts();
		activity.loadSounds();
		//activity.setCurrentScene(new MainMenuScene());
		DelayModifier dMod = new DelayModifier(10){
			@Override
			protected void onModifierFinished(IEntity pItem) {
			  	loading.setText("Done");
			    activity.setCurrentScene(new GameScene());
			}};
		registerEntityModifier(dMod);
		
	}
	

}
