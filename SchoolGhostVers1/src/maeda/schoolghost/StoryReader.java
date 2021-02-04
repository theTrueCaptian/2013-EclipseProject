package maeda.schoolghost;

import java.io.IOException;
import java.util.ArrayList;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;

public class StoryReader{
	private Resources res;
	private XmlResourceParser xpp;
	private int eventType;
	
	private Context context;
	private StoryActivity activity;
	//private Scene scene =new Scene();
	
	//dynamic stuff initialized on the way
	/*AnimatedSprite player;
	BitmapTextureAtlas mBitmapTextureAtlas;
	TiledTextureRegion mPlayerTextureRegion;
	RepeatingSpriteBackground rep;*/
	
	public StoryReader(Context context, StoryActivity activity, AnimationScene animscene){
		this.context = context;
		this.activity = activity;
		//TextView myXmlContent = (TextView)((Activity) context).findViewById(R.id.my_xml);
        try {
        	res = context.getResources();
    		xpp = res.getXml(R.xml.adam);
    		xpp.next();
        } catch (XmlPullParserException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }
	}

	public Scene readItem(){
		boolean animFlag = false;
		ArrayList<Point> path = new ArrayList<Point>();
		String fileName = "";
		Scene scene = new Scene();
		
		try {
			eventType = xpp.getEventType();
			//reset the choices array
			while (eventType != XmlPullParser.END_DOCUMENT ){
				if(eventType == XmlPullParser.START_DOCUMENT){
					
				}else if(eventType == XmlPullParser.START_TAG){
					if(xpp.getName().equals("narrate")){
						eventType = xpp.next();
						String text = xpp.getText();
						
					}else if(xpp.getName().equals("repeatingbackground")){
						eventType = xpp.next();
	//					RepeatingSpriteBackground rep = repeatingBackground(xpp.getText());
	//					scene.setBackground(rep);
					}else if(xpp.getName().equals("animatesprite")){
						eventType = xpp.next();
						animFlag = true;
						fileName = xpp.getText();
						System.out.println("fileName:"+fileName);
												
					}else if((xpp.getName().equals("x")) && animFlag){
						eventType = xpp.next();
						path.add(new Point());
						path.get(path.size()-1).x = Integer.parseInt(xpp.getText());
						System.out.println("x:"+path.get(path.size()-1).x);
					}else if((xpp.getName().equals("y")) && animFlag){
						eventType = xpp.next();
						path.get(path.size()-1).y = Integer.parseInt(xpp.getText());
						System.out.println("y:"+path.get(path.size()-1).y);
					}else if(xpp.getName().equals("image")){
						//find wihci file has that name and then change the background
						//FileIO input = new FileIO(context);
						eventType = xpp.next();
						//game.background = input.load(xpp.getText()+"");
					}
				}else if(eventType == XmlPullParser.END_TAG){
					//only stop reading if these end tags are found
					if(xpp.getName().equals("path")){
						System.out.println("set sprite on scene");
//						scene.attachChild(animatedSprite(fileName, path));
						animFlag =false;
					}
					if(xpp.getName().equals("scene")){
						System.out.println("return scene");
						return scene;
						//activity.mCurrentScene = scene;
						//return;
					}else if(xpp.getName().equals("x") || xpp.getName().equals("y")|| xpp.getName().equals("animatesprite")){
						System.out.println("detect x,y,animate");
					}else{
						eventType = xpp.next();
						System.out.println("break from this loop");
						break;
					}
				}
				eventType = xpp.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			System.out.println(e.getLocalizedMessage());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getLocalizedMessage());
		}
		return scene;
		//activity.mCurrentScene = scene;
		//return;
	}
	
	/*public RepeatingSpriteBackground repeatingBackground(String filePath){
		return new RepeatingSpriteBackground(activity.CAMERA_WIDTH, activity.CAMERA_HEIGHT, activity.getTextureManager(), AssetBitmapTextureAtlasSource.create(activity.getAssets(), filePath), activity.getVertexBufferObjectManager());
		
	}
	
	/*public AnimatedSprite animatedSprite(String fileName, ArrayList<Point> points){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		final TiledTextureRegion mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(new BitmapTextureAtlas(activity.getTextureManager(), 128, 128), activity, fileName, 0, 0, 3, 4);
		
		//* Calculate the coordinates for the face, so its centered on the camera. *
		float centerX = (activity.CAMERA_WIDTH - mPlayerTextureRegion.getWidth()) / 2;
		float centerY = (activity.CAMERA_HEIGHT - mPlayerTextureRegion.getHeight()) / 2;

		final AnimatedSprite player = new AnimatedSprite(centerX, centerY, 48, 64, mPlayerTextureRegion, activity.getVertexBufferObjectManager());

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
	*/
}
