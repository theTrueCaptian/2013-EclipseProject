package maeda.qisas;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;

public class GameScene extends Scene {
	public Runner runner;
	Camera mCamera;
	//Sprite face;
	//PhysicsHandler physicsHandler;
	public BaseActivity activity;
	//DigitalOnScreenControl mDigitalOnScreenControl = null;
	//IOnScreenControlListener listener;
	
	public GameScene(){
		activity = BaseActivity.getSharedInstance();
		
		setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		mCamera = BaseActivity.getSharedInstance().mCamera;
		//runner = Runner.getSharedInstance();
		
		//attachChild(runner.sprite);
		
		this.attachChild(onCreateScene());
		
		
	}
	
	public Scene onCreateScene() {
		//mEngine.registerUpdateHandler(new FPSLogger());
		System.out.println("googse frappa");
		final Scene scene = new Scene();
		scene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		/*final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
		final VertexBufferObjectManager vertexBufferObjectManager = activity.getVertexBufferObjectManager();
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(0.0f, new Sprite(0, activity.CAMERA_HEIGHT - activity.mParallaxLayerBack.getHeight(), activity.mParallaxLayerBack, vertexBufferObjectManager)));
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-5.0f, new Sprite(0, 80, activity.mParallaxLayerMid, vertexBufferObjectManager)));
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-10.0f, new Sprite(0, activity.CAMERA_HEIGHT - activity.mParallaxLayerFront.getHeight(), activity.mParallaxLayerFront, vertexBufferObjectManager)));
		scene.setBackground(autoParallaxBackground);

		//Calculate the coordinates for the face, so its centered on the camera. 
		final float playerX = (activity.CAMERA_WIDTH - activity.mPlayerTextureRegion.getWidth()) / 2;
		final float playerY = activity.CAMERA_HEIGHT - activity.mPlayerTextureRegion.getHeight() - 5;

		//Create two sprits and add it to the scene. 
		final AnimatedSprite player = new AnimatedSprite(playerX, playerY, activity.mPlayerTextureRegion, vertexBufferObjectManager);
		player.setScaleCenterY(activity.mPlayerTextureRegion.getHeight());
		player.setScale(2);
		player.animate(new long[]{200, 200, 200}, 3, 5, true);

		final AnimatedSprite enemy = new AnimatedSprite(playerX - 80, playerY, activity.mEnemyTextureRegion, vertexBufferObjectManager);
		enemy.setScaleCenterY(activity.mEnemyTextureRegion.getHeight());
		enemy.setScale(2);
		enemy.animate(new long[]{200, 200, 200}, 3, 5, true);*/
		
		/*final float centerX = (activity.CAMERA_WIDTH - activity.mFaceTextureRegion.getWidth()) / 2;
		final float centerY = (activity.CAMERA_HEIGHT - activity.mFaceTextureRegion.getHeight()) / 2;
		face = new Sprite(centerX, centerY, activity.mFaceTextureRegion, activity.getVertexBufferObjectManager());
		physicsHandler = new PhysicsHandler(face);
		face.registerUpdateHandler(physicsHandler);*/
		

		//scene.attachChild(activity.face);
		/*listener = new IOnScreenControlListener(){
			@Override
			public void onControlChange(BaseOnScreenControl pBaseOnScreenControl, float pValueX,float pValueY) {
				System.out.println("pValueX:"+pValueX+" pValueY:"+pValueY);
				//physicsHandler.setVelocity(pValueX * 100, pValueY * 100);
				if(pValueX == 1) {
					face.setX(pValueX+face.getX());
				} else if(pValueX == -1) {
					face.setX(pValueX+face.getX());
				} else if(pValueY == 1) {
					face.setY(pValueY+face.getY());
				} else if(pValueY == -1) {
					face.setY(pValueY+face.getY());
				}				
			}			
		};

		this.mDigitalOnScreenControl = new DigitalOnScreenControl(0, activity.CAMERA_HEIGHT - activity.mOnScreenControlBaseTextureRegion.getHeight(), this.mCamera, activity.mOnScreenControlBaseTextureRegion, activity.mOnScreenControlKnobTextureRegion, 0.1f, activity.getVertexBufferObjectManager(), listener);
		this.mDigitalOnScreenControl.getControlBase().setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		this.mDigitalOnScreenControl.getControlBase().setAlpha(0.5f);
		this.mDigitalOnScreenControl.getControlBase().setScaleCenter(0, 128);
		this.mDigitalOnScreenControl.getControlBase().setScale(1.25f);
		this.mDigitalOnScreenControl.getControlKnob().setScale(1.25f);
		this.mDigitalOnScreenControl.refreshControlKnobPosition();
		*/
		
		//scene.setChildScene(activity.mDigitalOnScreenControl);

		//scene.attachChild(player);
		//scene.attachChild(enemy);
		//scene.setChildScene(analogOnScreenControl);
		
		return scene;
	}


	
}
