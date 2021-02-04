package candyhero.maeda.android;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import android.content.Intent;

public class MainMenuScene extends MenuScene implements IOnMenuItemClickListener{
	MainActivity activity;
	int play=0;
	int ins =1;
	
	public MainMenuScene(){
		super(MainActivity.getSharedInstance().mCamera);
		activity = MainActivity.getSharedInstance();
		
		//setBackground(new Background(0.20f, 0.6274f, 0.8784f));
		Sprite bgsprite = new Sprite(0, 0,activity.skybg , activity.getVertexBufferObjectManager());
		bgsprite.setSize(activity.width, activity.height);
		this.attachChild(bgsprite );
		
		Text title1 = new Text(0, 0, activity.mFont, "Medieval Dodgeball", activity.getVertexBufferObjectManager());		
		title1.setPosition(activity.mCamera.getWidth()/2-title1.getWidth()/2, 30);		
		attachChild(title1);
		
		IMenuItem startButton = new TextMenuItem(play, activity.mFont, ("Play!"), activity.getVertexBufferObjectManager());
		startButton.setPosition(mCamera.getWidth()/2-startButton.getWidth()/2, mCamera.getHeight()/2-startButton.getHeight()/2);
		addMenuItem(startButton);		
		
		/*IMenuItem animButton = new TextMenuItem(ins, activity.mFont, ("Instructions"), activity.getVertexBufferObjectManager());
		animButton.setPosition(mCamera.getWidth()/2-animButton.getWidth()/2, mCamera.getHeight()/2-animButton.getHeight()/2 +startButton.getHeight()*2);
		addMenuItem(animButton);*/
		
		setOnMenuItemClickListener(this);
	}
	
	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {

		switch(pMenuItem.getID()){
			case 0:
				activity.menuSound.play();
				Intent myIntent1 = new Intent(activity.getBaseContext(), GameActivity.class);
				activity.startActivityForResult(myIntent1, 0);
				//activity.setCurrentScene(new GameScene());
			
				return true;
			/*case 1:
				Intent myIntent2 = new Intent(activity.getBaseContext(), GameActivity.class);
				activity.startActivityForResult(myIntent2, 0);
				//activity.setCurrentScene(new GameActivity());
				return true;*/
			default:
				break;
		}
		
		return false;
	}

}
