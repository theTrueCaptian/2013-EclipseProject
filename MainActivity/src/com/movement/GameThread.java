package com.movement;

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

	@Override
	public void run() {
		Canvas canvas;
		while (running) {
			canvas = null;
			try {
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder) {
					this.gameController.update();
					this.gameController.render(canvas);
				}
			} finally {
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}

}
