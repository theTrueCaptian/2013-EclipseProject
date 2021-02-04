package maeda.medeivalmaze.android;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;

import android.content.Intent;
import android.graphics.Typeface;

public class GameOverScene extends Scene implements IOnMenuItemClickListener {
	private GameActivity activity;
	private Text GameOverText;
	private int replayid=0, menu=1;
	//private MenuScene menuscene;
	public GameOverScene(GameActivity activity){
		this.activity = activity;
		
		
		
		Sprite bgsprite = new Sprite(0, 0,activity.skybg , activity.getVertexBufferObjectManager());
		bgsprite.setSize(activity.width, activity.height);
		this.attachChild(bgsprite );
		//this.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		System.out.println("setting the bgsprite done");
		//menuitems
		//menuscene= new MenuScene();
		//menuscene.setPosition(0,0);
		IMenuItem replay = new TextMenuItem(replayid, activity.mfont, ("Replay!"), activity.getVertexBufferObjectManager());
		replay.setPosition(activity.width/3-replay.getWidth()/2, 2*activity.height/3-replay.getHeight()/2);
		//addMenuItem(replay);		
				
		IMenuItem menumenuscene = new TextMenuItem(menu, activity.mfont, ("Back to Menu"), activity.getVertexBufferObjectManager());
		menumenuscene.setPosition(2*activity.width/3-menumenuscene.getWidth()/2, 2*activity.height/3-menumenuscene.getHeight()/2);
		//addMenuItem(menumenuscene);
		attachChild(menumenuscene);
		attachChild(replay);
		
		this.registerTouchArea(replay);
		this.registerTouchArea(menumenuscene);
		//setOnMenuItemClickListener(this);
				
		/*this.attachChild(menuscene);
		System.out.println("attached menuscene already");
		*/
		
		Font loadfont = FontFactory.create(activity.getFontManager(), activity.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 40);
		loadfont.load();
		
		GameOverText = new Text(0, 0, loadfont, "Game Over! \n Score:"+activity.SCORE, activity.getVertexBufferObjectManager());		
		GameOverText.setPosition(activity.width/2-GameOverText.getWidth()/2, 0);		
		this.attachChild(GameOverText);
		
		activity.mCamera.setChaseEntity(bgsprite);
		
		GameOverText.registerEntityModifier( new MoveYModifier(1, 0, activity.height/2-GameOverText.getHeight()/2));
		System.out.println("game text set alrady");
		
		DelayModifier dMod = new DelayModifier(5){
			@Override
			protected void onModifierFinished(IEntity pItem) {
			        //activity.setCurrentScene(new MainMenuScene());
				//attachChild(menuscene);
				//System.out.println("attached menuscene already");
			}};
		registerEntityModifier(dMod);
		
	}
	
	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
			System.out.println("touch detected");
			switch(pMenuItem.getID()){
				case 0:
					Intent myIntent1 = new Intent(activity.getBaseContext(), GameActivity.class);
					activity.startActivityForResult(myIntent1, 0);
					//activity.setCurrentScene(new GameScene());
				
					return true;
				case 1:
					Intent myIntent2 = new Intent(activity.getBaseContext(), MainActivity.class);
					activity.startActivityForResult(myIntent2, 0);
					//activity.setCurrentScene(new GameActivity());
					return true;
				default:
					break;
			}
			return false;
	}
}
