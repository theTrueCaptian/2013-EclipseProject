package candyhero.maeda.android;

import java.io.IOException;

import android.graphics.Color;
import android.util.DisplayMetrics;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.debug.Debug;


public class MainActivity extends SimpleBaseGameActivity {

	public Camera mCamera;
	//A reference to the current scene
	public Scene mCurrentScene = null;
	public static MainActivity instance;
	
	//screen height and width
	public int height=0, width=0;
	public Font mFont;
	private BitmapTextureAtlas mBitmapTextureAtlas;
	public TextureRegion skybg;
	public Sound menuSound;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		 instance = this;
		 
		 DisplayMetrics displaymetrics = new DisplayMetrics();
		 getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		 this.height = displaymetrics.heightPixels;
		 this.width = displaymetrics.widthPixels;
		 
	   	 mCamera = new Camera(0, 0, this.width, this.height);
	   	 
	   	final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(this.width, this.height), mCamera);
	    engineOptions.getAudioOptions().setNeedsSound(true);
	   	 return engineOptions;
	}

	@Override
	protected void onCreateResources() {
		FontFactory.setAssetBasePath("font/");
   	 	//this.mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		//mFont.load();
   	 	
		this.mFont = FontFactory.createFromAsset(this.getFontManager(), this.getTextureManager(), 512, 512, TextureOptions.BILINEAR, this.getAssets(), "Plok.ttf", 32, true, Color.BLACK);
   	 	this.mFont.load();
   	 	
   	 	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
   	 	this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 1056, 1056, TextureOptions.BILINEAR);
		this.skybg = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "skybg.png", 0, 0);
		this.mBitmapTextureAtlas.load();
		
		SoundFactory.setAssetBasePath("mfx/");
		try {
			this.menuSound = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, GlobalVariables.menuSoundString);
		} catch (final IOException e) {
			Debug.e(e);
		}
		
	}

	@Override
	protected Scene onCreateScene() {
		mEngine.registerUpdateHandler(new FPSLogger());
		
		mCurrentScene = new SplashScene();
				
		return mCurrentScene;
	
	}
	
	public static MainActivity getSharedInstance() {
		return instance;
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
	   		this.setCurrentScene(new MainMenuScene());
    }

}
