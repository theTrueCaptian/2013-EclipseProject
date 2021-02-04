package maeda.qisas;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.*;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import android.content.Intent;

public class MainMenuScene extends MenuScene implements IOnMenuItemClickListener  {
	BaseActivity activity;
	final int first = 0;
	final int second = 1;
	
	private BitmapTextureAtlas mBitmapTextureAtlas;
	protected ITextureRegion faceTextureRegion=null;
	private Sprite introbg=null;
	
	public MainMenuScene(){
		super(BaseActivity.getSharedInstance().mCamera);
		activity = BaseActivity.getSharedInstance();
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.mBitmapTextureAtlas  = new BitmapTextureAtlas(activity.getTextureManager(), 300, 200, TextureOptions.DEFAULT);
		faceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, activity, "introbg.png", 0, 0);
		this.mBitmapTextureAtlas.load();
		introbg = new Sprite(0, 0, activity.CAMERA_WIDTH, activity.CAMERA_HEIGHT, faceTextureRegion, activity.getVertexBufferObjectManager());
		this.attachChild(introbg);
		
		Text title1 = new Text(0, 0, activity.mFont, "Qisas I: Stories of the Prophets \n        Based on ibn Kathir", activity.getVertexBufferObjectManager());		
		title1.setPosition(activity.mCamera.getWidth()/2-title1.getWidth()/2, 30);		
		attachChild(title1);
		
		/*setBackground(new Background(0.20f, 0.6274f, 0.8784f));
		IMenuItem startButton = new TextMenuItem(first, activity.mFont, ("Prophet Adam"), activity.getVertexBufferObjectManager());
		startButton.setPosition(mCamera.getWidth()/2-startButton.getWidth()/2, mCamera.getHeight()/2-startButton.getHeight()/2);
		addMenuItem(startButton);*/
		
		IMenuItem animButton = new TextMenuItem(second, activity.mFont, ("Prophet Yusuf"), activity.getVertexBufferObjectManager());
		animButton.setPosition(mCamera.getWidth()/2-animButton.getWidth()/2, mCamera.getHeight()/2-animButton.getHeight()/2/*+startButton.getHeight()*2*/);
		addMenuItem(animButton);
		
		setOnMenuItemClickListener(this);
	}
	
	@Override
	public boolean onMenuItemClicked(MenuScene arg0, IMenuItem arg1, float arg2, float arg3){
		
		switch(arg1.getID()){
			case first:
				GlobalVariables.prophadam = true;
				Intent myIntent1 = new Intent(activity.getBaseContext(), GameActivity.class);
				activity.startActivityForResult(myIntent1, 0);
				activity.setCurrentScene(new GameScene());
			
				//activity.setCurrentScene(new GameScene());
				return true;
			case second:
				GlobalVariables.prophadam = false;
				Intent myIntent2 = new Intent(activity.getBaseContext(), GameActivity.class);
				activity.startActivityForResult(myIntent2, 0);
				activity.setCurrentScene(new GameScene());
				return true;
			default:
				break;
		}
		return false;
	}
	
}
