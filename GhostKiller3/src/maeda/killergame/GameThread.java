package maeda.killergame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {	

	private SurfaceHolder surfaceHolder;
	private GameController gameController;
	private boolean running;
	public void setRunning(boolean running) {
		this.running = running;
		
		
	}

	public GameThread(SurfaceHolder surfaceHolder, GameController gameController) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gameController = gameController;
	}
	public boolean isRunning(){
		return running;
	}

	@Override
	public void run() {
		Canvas canvas = null;
		while (running) {
			canvas = null;
			try {
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder) {
					this.gameController.update();
					if(canvas!=null)
						this.gameController.render(canvas);
					if(gameController.isGameover() ){
						
						running=false;
						
					}
				}
				
			} finally {
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
		/*if (canvas != null && surfaceHolder) {
			surfaceHolder.unlockCanvasAndPost(canvas);
		}*/
	}

}
