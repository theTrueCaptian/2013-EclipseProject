package maeda.schoolghost;

import java.util.ArrayList;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class AnimationScene extends Scene implements IOnSceneTouchListener{

	StoryActivity activity;
	StoryReader reader;
	///SOLUTION 1)MOVE ALL THIS STUFF IN ACTIIVTY TO SEE IF THIS IS ALL SCNE'S FAULT
		
	public AnimationScene(){
		setBackground(new Background(0.5f, 0.6274f, 0));
//		activity = StoryActivity.getSharedInstance();
		
		setOnSceneTouchListener(this);
		
		//storyreader
		reader = new StoryReader(activity.getBaseContext(), activity, this);
		
		//activity.setCurrentScene(activity.mCurrentScene);
		//this.setChildScene(reader.readItem());
		ArrayList<Point> lol = new ArrayList<Point>();
		Point point = new Point();
		point.x=9;point.y = 10;lol.add(point);
		point.x=9;point.y = 20;lol.add(point);
		point.x=10;point.y = 20;lol.add(point);
		point.x=10;point.y = 10;lol.add(point);
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		final TiledTextureRegion mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(new BitmapTextureAtlas(activity.getTextureManager(), 128, 128), activity, "player.png", 0, 0, 3, 4);
		
		//* Calculate the coordinates for the face, so its centered on the camera. *
//		float centerX = (activity.CAMERA_WIDTH - mPlayerTextureRegion.getWidth()) / 2;
//		float centerY = (activity.CAMERA_HEIGHT - mPlayerTextureRegion.getHeight()) / 2;

		final AnimatedSprite player = new AnimatedSprite(0, 0, 48, 64, mPlayerTextureRegion, activity.getVertexBufferObjectManager());

		//final Path path = new Path(5).to(10, 10).to(10, activity.CAMERA_HEIGHT - 74).to(activity.CAMERA_WIDTH - 58, activity.CAMERA_HEIGHT - 74).to(activity.CAMERA_WIDTH - 58, 10).to(10, 10);
		Path path = new Path(lol.size());//.to(x1, y1).to(x2, y2);
		for(int i=0; i<lol.size();i++){
			path.to(lol.get(i).x, lol.get(i).y);
			System.out.println("path:"+i+", pts:"+lol.get(i).x+", "+lol.get(i).y);
		}		
		
		player.registerEntityModifier(new LoopEntityModifier(new PathModifier(30, path, null, new IPathModifierListener() {
			@Override
			public void onPathStarted(final PathModifier pPathModifier, final IEntity pEntity) {

			}

			@Override
			public void onPathWaypointStarted(final PathModifier pPathModifier, final IEntity pEntity, final int pWaypointIndex) {
				switch(pWaypointIndex) {
					case 0:
						player.animate(new long[]{200, 200, 200}, 6, 8, true);
						break;
					case 1:
						player.animate(new long[]{200, 200, 200}, 3, 5, true);
						break;
					case 2:
						player.animate(new long[]{200, 200, 200}, 0, 2, true);
						break;
					case 3:
						player.animate(new long[]{200, 200, 200}, 9, 11, true);
						break;
				}
			}

			@Override
			public void onPathWaypointFinished(final PathModifier pPathModifier, final IEntity pEntity, final int pWaypointIndex) {

			}

			@Override
			public void onPathFinished(final PathModifier pPathModifier, final IEntity pEntity) {

			}
		})));
		this.attachChild(player);
		//this.attachChild(animatedSprite("player.png", lol));
		
	}
	
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		
		//activity.setCurrentScene(activity.mCurrentScene);
		//this.setChildScene(reader.readItem());
		return false;
	}
	
	public AnimatedSprite animatedSprite(String fileName, ArrayList<Point> points){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		final TiledTextureRegion mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(new BitmapTextureAtlas(activity.getTextureManager(), 128, 128), activity, fileName, 0, 0, 3, 4);
		
		//* Calculate the coordinates for the face, so its centered on the camera. *
//		float centerX = (activity.CAMERA_WIDTH - mPlayerTextureRegion.getWidth()) / 2;
//		float centerY = (activity.CAMERA_HEIGHT - mPlayerTextureRegion.getHeight()) / 2;

		final AnimatedSprite player = new AnimatedSprite(0, 0, 48, 64, mPlayerTextureRegion, activity.getVertexBufferObjectManager());

		//final Path path = new Path(5).to(10, 10).to(10, activity.CAMERA_HEIGHT - 74).to(activity.CAMERA_WIDTH - 58, activity.CAMERA_HEIGHT - 74).to(activity.CAMERA_WIDTH - 58, 10).to(10, 10);
		Path path = new Path(points.size());//.to(x1, y1).to(x2, y2);
		for(int i=0; i<points.size();i++){
			path.to(points.get(i).x, points.get(i).y);
			System.out.println("path:"+i+", pts:"+points.get(i).x+", "+points.get(i).y);
		}		
		
		player.registerEntityModifier(new LoopEntityModifier(new PathModifier(30, path, null, new IPathModifierListener() {
			@Override
			public void onPathStarted(final PathModifier pPathModifier, final IEntity pEntity) {

			}

			@Override
			public void onPathWaypointStarted(final PathModifier pPathModifier, final IEntity pEntity, final int pWaypointIndex) {
				switch(pWaypointIndex) {
					case 0:
						player.animate(new long[]{200, 200, 200}, 6, 8, true);
						break;
					case 1:
						player.animate(new long[]{200, 200, 200}, 3, 5, true);
						break;
					case 2:
						player.animate(new long[]{200, 200, 200}, 0, 2, true);
						break;
					case 3:
						player.animate(new long[]{200, 200, 200}, 9, 11, true);
						break;
				}
			}

			@Override
			public void onPathWaypointFinished(final PathModifier pPathModifier, final IEntity pEntity, final int pWaypointIndex) {

			}

			@Override
			public void onPathFinished(final PathModifier pPathModifier, final IEntity pEntity) {

			}
		})));
		return player;
	}
}
