package maeda.medeivalmaze.android;

import java.util.Random;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

/*
 * individual weapon object thrown at player
 */
public class Weapon{
	private Sprite weapons;
	private GameActivity activity;
	
	private float mGravityX=1;
	private float mGravityY=1;
	private GameWorld world;
	private EnemyBalls manager;
	private GameScene scene;
	private Body body;
	private FixtureDef objectFixtureDef;
	
	private final TimerHandler handler;

	//id is an int
	private int id;
	
	public Weapon(float pX, float pY, GameActivity activity, GameWorld world, final EnemyBalls manager, final int id, GameScene scene){
		this.world = world;
		this.activity = activity;
		this.manager= manager;
		this.id = id;
		this.scene = scene;
		
		addFace(pX,pY);
		
		//this timer ticks when its time for this weapon's disappearance
		final ITimerCallback timer = new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				//System.out.println("Unregistering handler for "+id);
				//unregisterTimer();
				//System.out.println("We will call removeWeapon for"+id);
				manager.removeWeapon(id);
			}
		};
		handler=new TimerHandler(5f, true, timer);
		weapons.registerUpdateHandler(handler);
		
		
	}
	
	public void unregisterTimer(){
		weapons.unregisterUpdateHandler(handler);
	}
	
	
	
	private void addFace(final float pX, final float pY) {
		objectFixtureDef = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f);

		ITextureRegion datface;
		Random rand = new Random();
		int tile = rand.nextInt(2);
		if(tile==0 ){
			datface = activity.enemyball1;
		}else{
			datface = activity.enemyball2;
		}
		
		weapons = new Sprite(pX, pY, datface, activity.getVertexBufferObjectManager());
		body = PhysicsFactory.createBoxBody(this.world.getWorld(), weapons, BodyType.DynamicBody, objectFixtureDef);
		

		this.world.getWorld().registerPhysicsConnector(new PhysicsConnector(weapons, body, true, true));

		//weapons.animate(new long[]{200,200}, 0, 1, true);
		weapons.setUserData(body);
		body.setUserData("monster");
		//this..registerTouchArea(face);
				
		jumpFace(weapons);
	}
	
	private void jumpFace(final Sprite face) {
		final Body faceBody = (Body)face.getUserData();

		final Vector2 velocity = Vector2Pool.obtain(this.mGravityX * -50, this.mGravityY * -50);
		faceBody.setLinearVelocity(velocity);
		Vector2Pool.recycle(velocity);
	}
	
	public void removeFace() {
		final PhysicsConnector facePhysicsConnector = this.world.getWorld().getPhysicsConnectorManager().findPhysicsConnectorByShape(this.weapons);

		this.world.getWorld().unregisterPhysicsConnector(facePhysicsConnector);
		this.world.getWorld().destroyBody(facePhysicsConnector.getBody());

		//final EngineLock engineLock = activity.getEngine().getEngineLock();
		//engineLock.lock();
		//System.out.println("About to detach Self and dispose for "+id);
		//this.weapons.detachSelf();
		//this statement makes the exception this statement must be run into the RunnableHandler.postRunnable() which is registered to the Scene or Engine itself
		//this.scene.detach(weapons);
		this.weapons.setVisible(false);
		this.weapons.dispose();
		System.gc();
		//weapons = null;
		//engineLock.unlock();
		
	}
	
	public Sprite getSprite(){
		return weapons;
	}
	public int getWeaponId(){
		return this.id;
	}
	
	public void setGravityX(float x){	this.mGravityX = x;	}
	public void setGravityY(float y){	this.mGravityY = y;	}
	
	public float getGravityX(){	return this.mGravityX ;	}
	public float getGravityY(){	return this.mGravityY ;	}
}
