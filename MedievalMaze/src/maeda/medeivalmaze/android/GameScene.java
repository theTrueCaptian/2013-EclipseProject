package maeda.medeivalmaze.android;


import java.util.Random;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;


public class GameScene extends Scene {
	private GameActivity activity;
	private PlayerSprite player;
	private PlayerControl controls;
	private EnemyBalls enemies;
	private GameWorld world;
	private float newEnemyDelay =  0.5f;
	
	//private ITextureRegion bg = activity.skybg;
	private Sprite bgsprite;
	
	//score hud
	private HUD scorehud;
	private Rectangle  playerDisp;
	private Text scoredisp, playerinfo;

	
	public GameScene(){
		activity = GameActivity.getSharedInstance();
		//attach background pic
		//bgsprite = new Sprite(0, 0,activity.skybg , activity.getVertexBufferObjectManager());
		//bgsprite.setSize(activity.WORLDWIDTH+activity.width, activity.WORLDHEIGHT);
		//this.attachChild(bgsprite );
		
		
		//creating and registering the physics and world to the scene
		world = new GameWorld(activity, this);
		this.registerUpdateHandler(world.getWorld());
				
		//setting up a bgcolor
		this.setBackground(new Background(0, 1, 0));

		
		
		//player object
		player = new PlayerSprite(activity, world, GlobalVariables.PLAYER_HEALTH);
		//attach the sword to scene
		//this.attachChild(player.getSword());
		this.attachChild(player.getSprite());
		
		//add hud for player info etc. make sure to call this after player has been init
		//also be sure to call this before calling the controls
		setupScoreLifeHUD();

		//settting user control object
		controls = new PlayerControl(player, activity, this);		
		this.setChildScene(controls.getControl());	
		
		//set enemies
		//enemies = new EnemyBalls(activity, this, world, GlobalVariables.ENEMY_HEALTH);		
		//this.attachChild(enemies.getLast().getSprite());
		
		//set up game play where the weapons are shot out from enemy side into the air every 0.5 sec
		//this timer also updates the score variable
		/*this.registerUpdateHandler(new TimerHandler(newEnemyDelay, true, new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				//System.out.println("Time handler called for a new enemy");
				if(enemies.newEnemy()){ //if succesful enemy add, then attach it to screen
					getInstance().attachChild(enemies.getLast().getSprite());
					//System.out.println("We attached the new child");
				}
				
				
				//update weapon position too
				//player.getPlayerWeaponClass().updatePosition();
				
				
			}
		}));*/
		
		this.registerUpdateHandler(new IUpdateHandler(){

			@Override
			public void onUpdate(float pSecondsElapsed) {
				//check if any collision occured
				//checkCollision();
				updateHud();
				//check if gameover
				if(player.getLifeState()<=0){
					scorehud.setVisible(false);
					
					activity.setCurrentScene(new GameOverScene(activity));
				}
			}

			@Override
			public void reset() {
				
			}
			
		});
		
	}
	
	public void setupScoreLifeHUD(){
		scorehud = new HUD();
		final Rectangle lifebase1 = new Rectangle(10, 10, activity.width/4, 10, activity.getVertexBufferObjectManager());
		lifebase1.setColor(1, 1, 1);
		
		playerDisp = new Rectangle(10, 10, activity.width/4, 10, activity.getVertexBufferObjectManager());
		playerDisp.setColor(1, 0, 0);
		
		//set text for indication of enemy and player
		this.playerinfo = new Text(0,0, this.activity.scorehudFont, "You:"+player.getLifeState()+"/"+player.getMaxHealth(), activity.getVertexBufferObjectManager());
		this.playerinfo.setPosition(playerDisp.getX(), playerDisp.getY()+playerDisp.getHeight()+5);		
		
		//set score text
		this.scoredisp = new Text(0, 0, this.activity.scorehudFont, "Score:"+activity.SCORE, activity.getVertexBufferObjectManager());		
		this.scoredisp.setPosition(activity.mCamera.getWidth()/2-this.scoredisp.getWidth(), 10);		
		
				
		scorehud.attachChild(lifebase1);
		scorehud.attachChild(this.playerDisp);
		scorehud.attachChild(this.scoredisp);
	    scorehud.attachChild(this.playerinfo);
	    
	    this.activity.mCamera.setHUD(scorehud);
	
	    //this.createContactListener();
	    //this.world.getWorld().setContactListener(createContactListener());

	}
	
	public GameScene getInstance(){
		return this;
	}
	/*public void detach(AnimatedSprite kid){
		this.detachChild(kid);
	}*/

	public HUD getHud(){
		return this.scorehud;
	}
	
	public void updateHud(){
		try{
			scoredisp.setText("Score:"+activity.SCORE);
			playerinfo.setText("You:"+player.getLifeState()+"/"+player.getMaxHealth());
			//update score variable
			scoredisp.setText("Score:"+activity.SCORE);
			//scoredisp.setPosition(activity.mCamera.getWidth()/2-scoredisp.getWidth(), 10);		
			//enemies.updateScore();
		}catch(ArrayIndexOutOfBoundsException ex){
			System.out.println("Eception niggay");
		}
	}
	
	
	
	/*public void checkCollision(){
		for(int i=0; i<this.enemies.getAllWeapons().size(); i++){
			if(this.enemies.getAllWeapons().get(i).getSprite().collidesWith(player.getSprite())){
				//if this weapon collides with the player then player loses a few life points and the wweapon dies
				player.loseLife(new Random(20).nextInt(20));
				this.enemies.removeWeapon(this.enemies.getAllWeapons().get(i).getWeaponId());
				enemies.updateScore();
				
			}
			//if however this weapon collides with the player weapon then we got a diff story
		}
		
	}*/
}
