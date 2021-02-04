package maeda.battuta;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.*;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;

import android.content.Intent;


public class MainMenuScene extends MenuScene implements IOnMenuItemClickListener  {
	BaseActivity activity;
	final int MENU_START = 0;
	final int ANIMATION_START = 1;
	
	public MainMenuScene(){
		super(BaseActivity.getSharedInstance().mCamera);
		activity = BaseActivity.getSharedInstance();
		
		setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		IMenuItem startButton = new TextMenuItem(MENU_START, activity.mFont, activity.getString(R.string.start), activity.getVertexBufferObjectManager());
		startButton.setPosition(mCamera.getWidth()/2-startButton.getWidth()/2, mCamera.getHeight()/2-startButton.getHeight()/2);
		addMenuItem(startButton);
		
		IMenuItem animButton = new TextMenuItem(ANIMATION_START, activity.mFont, activity.getString(R.string.animation_start), activity.getVertexBufferObjectManager());
		animButton.setPosition(mCamera.getWidth()/2-animButton.getWidth()/2, mCamera.getHeight()/2-animButton.getHeight()/2+startButton.getHeight()*2);
		addMenuItem(animButton);
		
		setOnMenuItemClickListener(this);
	}

	@Override
	public boolean onMenuItemClicked(MenuScene arg0, IMenuItem arg1, float arg2, float arg3){
		switch(arg1.getID()){
			case MENU_START:
				Intent myIntent = new Intent(activity.getBaseContext(), GameActivity.class);
                activity.startActivityForResult(myIntent, 0);
				//activity.setCurrentScene(new GameScene());
				return true;
			case ANIMATION_START:
				//Intent myIntent1 = new Intent(activity.getBaseContext(), StoryActivity.class);
                //activity.startActivityForResult(myIntent1, 0);
				//activity.setCurrentScene(new GameScene());
				return true;
			default:
				break;
		}
		return false;
	}
	
}
