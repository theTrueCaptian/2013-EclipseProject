package maeda.medeivalmaze.android;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import com.badlogic.gdx.math.Vector2;

import android.opengl.GLES20;

/*
 * Player input and control comes in from here and processed here
 */
public class PlayerControl {
	private final AnalogOnScreenControl analogOnScreenControl;
	private GameActivity activity;
	private PlayerSprite player;
	private GameScene scene;
	
	public PlayerControl(final PlayerSprite player, GameActivity activity, GameScene scene){
		this.activity = activity;
		this.player = player;
		this.scene = scene;
		
		analogOnScreenControl = new AnalogOnScreenControl(0, activity.height - activity.mOnScreenControlBaseTextureRegion.getHeight(), activity.mCamera, activity.mOnScreenControlBaseTextureRegion, activity.mOnScreenControlKnobTextureRegion, 0.1f, 200, activity.getVertexBufferObjectManager(), new IAnalogOnScreenControlListener() {
		//this.analogOnScreenControl = new DigitalOnScreenControl(0, activity.height - activity.mOnScreenControlBaseTextureRegion.getHeight(), activity.mCamera, activity.mOnScreenControlBaseTextureRegion, activity.mOnScreenControlKnobTextureRegion, 0.1f, activity.getVertexBufferObjectManager(), new IOnScreenControlListener() {
			
			@Override
			public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
				
				
				//player.getPlayerBody().applyForce(new Vector2(pValueX*100, pValueY*100), player.getPlayerBody().getPosition());
				if(pValueX<0  && !player.isAnimLeft()){
					player.animateLeft();
				}else if(pValueX>0 && !player.isAnimRight()){//dont need to repeat an animation command
					player.animateRight();
					
				}else if (pValueX==0 && !player.isAnimStop()){
					player.stopAnim();
				}
				//this code actually moves the spirte since we are moving its body
				player.getPlayerBody().setLinearVelocity(new Vector2(pValueX*10, pValueY*2));
			}

			@Override
			public void onControlClick(final AnalogOnScreenControl pAnalogOnScreenControl) {
			}
		});
		analogOnScreenControl.getControlBase().setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		analogOnScreenControl.getControlBase().setAlpha(0.5f);
		analogOnScreenControl.getControlBase().setScaleCenter(0, 128);
		analogOnScreenControl.getControlBase().setScale(1.25f);
		analogOnScreenControl.getControlKnob().setScale(1.25f);
		analogOnScreenControl.refreshControlKnobPosition();
			
		
		createControllers();
	}
	
	private void createControllers(){
	    final TiledSprite right = new TiledSprite((activity.width-activity.mOnScreenControlBaseTextureRegion.getWidth()), activity.height-activity.mOnScreenControlBaseTextureRegion.getHeight(), 60f, 60f, (ITiledTextureRegion) activity.actionbutton, this.activity.getVertexBufferObjectManager())
	    {
	        public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y)
	        {
	            if (touchEvent.isActionUp())
	            {
	                //change the tile and do the action
	            	this.setCurrentTileIndex(0);
	            }
	            if (touchEvent.isActionDown())
	            {
	                //change the tile and do the action
	            	this.setCurrentTileIndex(1);
	            }
	            return true;
	        };
	        
	    };
	    right.setAlpha(0.5f);
		right.setScale(2f);
		right.setPosition((activity.width-(2*right.getWidth())), activity.height-(2*right.getHeight()));
	    this.scene.getHud().registerTouchArea(right);
	    this.scene.getHud().attachChild(right);
	    
	}
	
	/*public Sprite getAction(){
		return this.actionButton;
	}*/
	
	public AnalogOnScreenControl getControl(){
		return this.analogOnScreenControl;
	}
	/*public DigitalOnScreenControl getControl(){
		return this.analogOnScreenControl;
	}*/

}
