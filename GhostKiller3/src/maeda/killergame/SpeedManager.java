package maeda.killergame;

public class SpeedManager {

	public static final int DIRECTION_RIGHT	= 1;
	public static final int DIRECTION_LEFT	= -1;
	public static final int DIRECTION_UP	= 1;
	public static final int DIRECTION_DOWN	= -1;

	private float velocity = 10;
	private int xDirection = DIRECTION_RIGHT;
	private int yDirection = DIRECTION_UP;

	public SpeedManager() {
		this.velocity = 1;

	}

	public SpeedManager(float velocity, float yv) {
		this.velocity = velocity;

	}

	public float getvelocity() {
		return velocity;
	}
	public void setvelocity(float velocity) {
		this.velocity = velocity;
	}	

	public int getxDirection() {
		return xDirection;
	}
	public void setxDirection(int xDirection) {
		this.xDirection = xDirection;
	}	

	public void toggleXDirection() {
		xDirection = xDirection * -1;
	}
	
	public int getyDirection() {
		return yDirection;
	}
	public void setyDirection(int yDirection) {
		this.yDirection = yDirection;
	}	

	public void toggleYDirection() {
		yDirection = yDirection * -1;
	}

}
