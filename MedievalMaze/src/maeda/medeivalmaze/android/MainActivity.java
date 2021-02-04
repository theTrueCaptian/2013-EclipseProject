package maeda.medeivalmaze.android;

import android.graphics.Typeface;
import android.util.DisplayMetrics;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;


public class MainActivity extends SimpleBaseGameActivity {

	public Camera mCamera;
	//A reference to the current scene
	public Scene mCurrentScene = null;
	public static MainActivity instance;
	
	//screen height and width
	public int height=0, width=0;
	public Font mFont;
	
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		 instance = this;
		 
		 DisplayMetrics displaymetrics = new DisplayMetrics();
		 getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		 this.height = displaymetrics.heightPixels;
		 this.width = displaymetrics.widthPixels;
		 
	   	 mCamera = new Camera(0, 0, this.width, this.height);
	   	 
	   	 return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(this.width, this.height), mCamera);
   
	}

	@Override
	protected void onCreateResources() {
		FontFactory.setAssetBasePath("font/");
   	 	this.mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		
   	 	mFont.load();
		
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
