package maeda.medeivalmaze.android;
import maeda.medeivalmaze.android.GameActivity;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;

import android.graphics.Typeface;


public class LoadingScene extends Scene{
	GameActivity activity;
	Text loading;
	
	public LoadingScene(int level){
		activity = GameActivity.getSharedInstance();
		
		this.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		Font loadfont = FontFactory.create(activity.getFontManager(), activity.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		loadfont.load();
		
		loading = new Text(0, 0, loadfont, "Loading...", activity.getVertexBufferObjectManager());		
		loading.setPosition(activity.mCamera.getWidth()/2-loading.getWidth()/2, activity.mCamera.getHeight()/2);		
		this.attachChild(loading);
		
		Text lvlmsg = new Text(0, 0, loadfont, ""+GlobalVariables.loadingText, activity.getVertexBufferObjectManager());	
		lvlmsg.setPosition(activity.mCamera.getWidth()/2-lvlmsg.getWidth()/2, activity.mCamera.getHeight()/2+5+loading.getHeight());		
		this.attachChild(lvlmsg);
		
		activity.loadlevel(level);
		activity.loadGraphics();
		activity.loadFonts();
		activity.loadSounds();
		//activity.setCurrentScene(new MainMenuScene());
		DelayModifier dMod = new DelayModifier(2){
			@Override
			protected void onModifierFinished(IEntity pItem) {
			  	loading.setText("Done");
			    activity.setCurrentScene(new GameScene());
			}};
		registerEntityModifier(dMod);
		
	}
	

}
