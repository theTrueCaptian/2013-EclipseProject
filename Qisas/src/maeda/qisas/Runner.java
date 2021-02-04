package maeda.qisas;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;

public class Runner {
	public Rectangle sprite;
	public static Runner instance;
	Camera mCamera;
	
	public static Runner getSharedInstance(){
		if(instance == null){
			instance = new Runner();
		}
		return instance;
	}
	
	private Runner(){
		sprite = new Rectangle(0, 0, 70, 30, BaseActivity.getSharedInstance().getVertexBufferObjectManager());
		mCamera = BaseActivity.getSharedInstance().mCamera;
		sprite.setPosition(mCamera.getWidth()/2-sprite.getWidth()/2, mCamera.getHeight()-sprite.getHeight()-10);
		
	}
}
