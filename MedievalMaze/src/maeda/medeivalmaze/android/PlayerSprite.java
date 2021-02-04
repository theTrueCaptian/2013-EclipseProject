package maeda.medeivalmaze.android;


import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.EntityModifier;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.util.modifier.IModifier;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Shape;

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
	
	private int life, maxhealth;
	private boolean right=false, left=false, stop=true;
	
	public PlayerSprite(GameActivity activity, GameWorld world, int life){
		this.activity = activity;
		this.world = world;
		this.life=life;
		this.maxhealth = life;
		
		final float centerX = 2 * (activity.width - activity.mFaceTextureRegion.getWidth()) / 3;
		final float centerY = (activity.height - activity.mFaceTextureRegion.getHeight());
		//face = new Sprite(centerX, centerY, activity.mFaceTextureRegion, activity.getVertexBufferObjectManager());
		face = new AnimatedSprite(centerX, centerY, activity.mKnightTextureRegion, activity.getVertexBufferObjectManager());
		//face.animate(new long[]{200,200,200}, 0, 2, true);
		
		//make camera chase this guy
		activity.mCamera.setChaseEntity(face);
		
		//some physics here
		//physicsHandler = new PhysicsHandler(face);		
		//face.registerUpdateHandler(physicsHandler);
		
		//add world physics to this body		
		//this setup player's desity, elasticity, friction
		final FixtureDef objectFixtureDef = PhysicsFactory.createFixtureDef(2, 0, 0f);
		body = PhysicsFactory.createBoxBody(this.world.getWorld(), face, BodyType.DynamicBody, objectFixtureDef);
		//to make sure physics worlds is aware of player's physics: parms sprite, body, update position?, and rotation?
		this.world.getWorld().registerPhysicsConnector(new PhysicsConnector(face, body, true, false));
		face.setUserData(body);
		body.setUserData("player");
		//add a sword
		//this.sword = new PlayerWeapon(activity, this, world);
		//body.createFixture(sword.getBody(), 1);

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
	
	public void loseLife(int damage){
		this.life=this.life - damage;
		final Text loselife = new Text(this.face.getX(), this.face.getY(),  activity.scorehudFont, "-"+damage, activity.getVertexBufferObjectManager());
		loselife.setColor(1, 0, 0);
		activity.mCurrentScene.attachChild(loselife);
		DelayModifier dMod = new DelayModifier(2){
			@Override
			protected void onModifierFinished(IEntity pItem) {
			  	
			    loselife.setVisible(false);
			}};
		loselife.registerEntityModifier(dMod);
	}
	/*public PhysicsHandler getSpritePhysics(){
		return physicsHandler;
	}*/

	
	
	public int changeLifeStat(int change){
		this.life = this.life+change;
		
		return this.life;
	}
	
	public int getLifeState(){
		return this.life;
	}
	
	public int getMaxHealth(){
		return this.maxhealth;
	}

}
