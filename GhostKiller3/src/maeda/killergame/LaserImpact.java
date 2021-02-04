package maeda.killergame;

public class LaserImpact {
	public float laserx, lasery, radius;
	public long laserlife=System.currentTimeMillis();
	public LaserImpact(float laserx, float lasery, float radius){
		this.laserx = laserx;
		this.lasery = lasery;
		this.radius = radius;
		laserlife = System.currentTimeMillis();
	}
	
}
