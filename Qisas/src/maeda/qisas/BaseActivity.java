package maeda.qisas;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.graphics.Typeface;


public class BaseActivity extends SimpleBaseGameActivity  {

	static final int CAMERA_WIDTH = 800;
	static final int CAMERA_HEIGHT = 480;
	
	// ===========================================================
	// Fields
	// ===========================================================
	final int first = 0;
	final int second = 1;
	
	public Font mFont;
	public Camera mCamera;
	//A reference to the current scene
	public Scene mCurrentScene = null;
	public static BaseActivity instance;
	
    
     public EngineOptions onCreateEngineOptions() {
    	 instance = this;
    	 mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
    	 return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
    }


     protected void onCreateResources() {
    	 final ITexture droidFontTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
    	 FontFactory.setAssetBasePath("font/");
    	 //this.mFont = FontFactory.createFromAsset(this.getFontManager(), droidFontTexture, this.getAssets(), "JOKERMAN.ttf", 32, true, Color.BLACK);
 		//mFont = FontFactory.create(this.getFontManager(),this.getTextureManager(), 256, 256,Typeface.create(Typeface.createFromAsset(this.getAssets(), "BELLI.tff"), Typeface.ITALIC), 32);
    	this.mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
 		
    	 mFont.load();
    	
		
    	
     }

    @Override
     protected void onResume(){
    	super.onResume();
    	if(this.mCurrentScene!=null)
    	 this.setCurrentScene(new MainMenuScene());
     }

	@Override
	protected Scene onCreateScene() {
		mEngine.registerUpdateHandler(new FPSLogger());
		
		mCurrentScene = new SplashScene();
				
		return mCurrentScene;
	
	}


	public static BaseActivity getSharedInstance() {
		return instance;
	}
	
	// to change the current main scene

	public void setCurrentScene(Scene scene) {
	    mCurrentScene = scene;
	    getEngine().setScene(mCurrentScene);
	}
	
}


