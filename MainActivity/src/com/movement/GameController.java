package com.movement;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameController extends SurfaceView implements
		SurfaceHolder.Callback {
	private GameThread thread;
	private Ball ballInstance;

	public GameController(Context context) {
		super(context);
		getHolder().addCallback(this);
		ballInstance = new Ball(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher), 80, 80);
		thread = new GameThread(getHolder(), this);
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {

			}
		}

	}	

	public void render(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		ballInstance.draw(canvas);
	}

	public void update() {
		if (ballInstance.getSpeed().getxDirection() == SpeedManager.DIRECTION_RIGHT
				&& ballInstance.getX() + ballInstance.getBitmap().getWidth() / 2 >= getWidth()) {
			ballInstance.getSpeed().toggleXDirection();
		}

		if (ballInstance.getSpeed().getxDirection() == SpeedManager.DIRECTION_LEFT
				&& ballInstance.getX() - ballInstance.getBitmap().getWidth() / 2 <= 0) {
			ballInstance.getSpeed().toggleXDirection();
		}

		ballInstance.update();
	}

}
