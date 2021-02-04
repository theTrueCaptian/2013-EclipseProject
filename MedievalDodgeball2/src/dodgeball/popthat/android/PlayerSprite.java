package dodgeball.popthat.android;


import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import com.badlogic.gdx.physics.box2d.Body;


/**
 * 
 * @author Maeda
 * This class contains all info related to player:
 * sprite and sprite physics
 *
 */
public class PlayerSprite{
	private GameActivity activity;
	private AnimatedSprite face;
	private GameWorld world;
	Body body;
	
	private int hearts;
	private boolean right=false, left=false, stop=true;
	
	public PlayerSprite(GameActivity activity, GameWorld world){
		this.activity = activity;
		this.world = world;

		this.hearts = 5;
		
		final float centerX = activity.width/2;
		final float centerY = activity.height/2;
		//face = new Sprite(centerX, centerY, activity.mFaceTextureRegion, activity.getVertexBufferObjectManager());
		face = new AnimatedSprite(centerX, centerY, activity.mKnightTextureRegion, activity.getVertexBufferObjectManager());
		face.animate(new long[]{200,200,200,200,200,200}, 0, 5, true);
		
		
		
		//add world physics to this body		
		//this setup player's desity, elasticity, friction
		/*final FixtureDef objectFixtureDef = PhysicsFactory.createFixtureDef(2, 0, 0f);
		body = PhysicsFactory.createBoxBody(this.world.getWorld(), face, BodyType.DynamicBody, objectFixtureDef);
		//to make sure physics worlds is aware of player's physics: parms sprite, body, update position?, and rotation?
		this.world.getWorld().registerPhysicsConnector(new PhysicsConnector(face, body, true, false));
		face.setUserData(body);
		body.setUserData("player");*/
		

	}
	
	public void animateRight(){
		face.animate(new long[]{200,200,200}, 0, 2, true);
		right = true;left=stop=false;
	}
	public void animateLeft(){
		face.animate(new long[]{200,200,200}, 3, 5, true);
		left = true;right=stop=false;
	}
	public void stopAnim(){
		face.stopAnimation();
		stop = true;left=right=false;
	}
	
	public boolean isAnimRight(){
		return right;
	}
	public boolean isAnimLeft(){
		return left;
	}
	public boolean isAnimStop(){
		return stop;
	}

	public Sprite getSprite(){
		return face;
	}
	
	public Body getPlayerBody(){
		return body;
	}
	
	public void lostpoints(int pointslost){
		
		final Text loselife = new Text(this.face.getX(), this.face.getY(),  activity.scorehudFont, "- "+pointslost+" pts", activity.getVertexBufferObjectManager());
		loselife.setPosition(face.getX()-10, face.getY()-10);
		loselife.setColor(1, 0, 0);
		activity.mCurrentScene.attachChild(loselife);
		loselife.registerEntityModifier(  new MoveYModifier(3, face.getY()-10, 0){ 
			@Override
			protected void onModifierFinished(IEntity pItem) {
			  	
			    loselife.setVisible(false);
			}
		});
		
		
	}
	
	public void losealife(){
		hearts-=1;
	}
	public void earnalife(){
		hearts=+1;
	}
	public int getnumhearts(){
		return hearts;
	}
}
