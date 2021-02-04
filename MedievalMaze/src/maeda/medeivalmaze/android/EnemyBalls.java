package maeda.medeivalmaze.android;

import java.util.ArrayList;

import org.andengine.engine.Engine.EngineLock;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

/*
 * Array of all the weapons throw at player 
 * as well as info regarding the enemyfrt
 */
public class EnemyBalls implements IAccelerationListener{
	private final int LIMIT = 10;
	private ArrayList<Weapon> enemies;
	private GameActivity activity;
	private GameWorld world;
	private GameScene scene;
	private int fortlife, fortMaxlife;
	private float x ,y;
	private Sprite enemyfort;
	private Body body;
	private Rectangle enemyDisp;
	private Text enemyinfo;
	
	public EnemyBalls(GameActivity activity, GameScene scene, GameWorld world, int fortMaxlife){
		this.activity = activity;
		this.world =  world;
		this.scene = scene;
		this.fortMaxlife = fortMaxlife;
		this.fortlife = this.fortMaxlife;
	
		 x = activity.WORLDWIDTH/10;
		 y = activity.WORLDHEIGHT-2*world.getGround().getHeight();
		
		
		enemies = new ArrayList<Weapon>();
		initfort();
		newEnemy();
	}
	
	private void initfort(){
		enemyfort = new Sprite(x,y-activity.enemyfort.getHeight(), activity.enemyfort, activity.getVertexBufferObjectManager());
		enemyfort.setScale(2, 2);
		enemyfort.setPosition(x,y-enemyfort.getHeight());
		
		//initialize physics
		final FixtureDef objectFixtureDef = PhysicsFactory.createFixtureDef(1, 0.5f, 0.0f);
		body = PhysicsFactory.createBoxBody(this.world.getWorld(), enemyfort, BodyType.StaticBody, objectFixtureDef);
		//to make sure physics worlds is aware of fort physics: parms sprite, body, update position?, and rotation?
		this.world.getWorld().registerPhysicsConnector(new PhysicsConnector(enemyfort, body, false, false));
		enemyfort.setUserData(body);
		scene.attachChild(enemyfort);
		
		final Rectangle lifebase2 = new Rectangle(0,0, activity.width/4, 10, activity.getVertexBufferObjectManager());
		lifebase2.setPosition(enemyfort.getX()-lifebase2.getWidth()/2+enemyfort.getWidth()/2, enemyfort.getY()-enemyfort.getHeight());
		
		lifebase2.setColor(1, 1, 1);
		enemyDisp = new Rectangle(0,0, activity.width/4, 10, activity.getVertexBufferObjectManager());
		enemyDisp.setPosition(lifebase2.getX(), lifebase2.getY());
		enemyDisp.setColor(1, 0, 0);
		scene.attachChild(lifebase2);
		scene.attachChild(this.enemyDisp);
		
		//i think this shud stick to the enemyfort
		this.enemyinfo = new Text(0,0, this.activity.scorehudFont, ""+this.fortlife+"/"+this.fortMaxlife, activity.getVertexBufferObjectManager());
		this.enemyinfo.setPosition(enemyDisp.getX()+enemyinfo.getWidth()/2, enemyDisp.getY()-enemyDisp.getHeight()-5);	
		scene.attachChild(this.enemyinfo);
		
	}
	
	public boolean newEnemy(){
		if(this.enemies.size()<LIMIT){ //continure to add new weapons as long as it doesn't exceed limits
			
			//System.out.println("Time to add a new enemy");
			int index = enemies.size();
			//System.out.println("The new enemy weapon id is "+index);
			enemies.add(new Weapon(x, y-enemyfort.getHeight(), activity, world, this, index, scene));
			return true;
		}
		//senemies.getLast().addFace(activity.width/2, activity.height/2);
		return false;
	}
	
	public void removeWeapon(int weaponIndex){
		//search for index==weapon.id 
		
		try{
			//enemies.get(0).removeFace();
			for(int i=0; i<enemies.size(); i++){
				if(enemies.get(i).getWeaponId()==weaponIndex){
						//unregister te death timer
						enemies.get(i).unregisterTimer();
						//remove physics and sprite
						enemies.get(i).removeFace();
						//System.out.println("Completed removing physics and sprite for "+weaponIndex+", aka "+enemies.get(i).getWeaponId());
						//here remove sprite
						/*final EngineLock engineLock = activity.getEngine().getEngineLock();
						engineLock.lock();
						scene.detachChild(i);
						
						enemies.get(i).getSprite().dispose();
						engineLock.unlock();*/
						
						//remove from array
						 enemies.remove(i);
						//System.out.println("Shud be Officially REMOVED!!! for "+weaponIndex);
						
						break;
				}
			}
		}catch(NullPointerException ex){
			System.out.println("EXCEPTION NIGGA!");
		}catch(IndexOutOfBoundsException ex){
			System.out.println("Array EXCEPTION NIGGA!");
		}
		//System.out.println("Done removing the sprite "+weaponIndex);
	}
	public Weapon getLast(){
		return enemies.get(enemies.size()-1);
	}
	
	@Override
	public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {
		
	}

	@Override
	public void onAccelerationChanged(AccelerationData pAccelerationData) {
		this.getLast().setGravityX(pAccelerationData.getX());
		this.getLast().setGravityY(pAccelerationData.getY());
		//this.mGravityX = pAccelerationData.getX();
		//this.mGravityY = pAccelerationData.getY();

		final Vector2 gravity = Vector2Pool.obtain(this.getLast().getGravityX(), this.getLast().getGravityY());
		this.world.getWorld().setGravity(gravity);
		Vector2Pool.recycle(gravity);
		
	}

	public void updateScore() {
		this.enemyinfo.setText(""+this.fortlife+"/"+this.fortMaxlife);
		
	}
	
	public ArrayList<Weapon> getAllWeapons(){
		return this.enemies;
	}
	

}
