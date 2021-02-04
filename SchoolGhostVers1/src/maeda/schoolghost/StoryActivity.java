package maeda.schoolghost;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine.EngineLock;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;
import org.andengine.util.math.MathUtils;
import org.andengine.util.modifier.ease.EaseLinear;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.widget.Toast;

public class StoryActivity extends  SimpleBaseGameActivity {
	private int CAMERA_WIDTH = 720;
	private int CAMERA_HEIGHT = 480;

	private Resources res;
	private XmlResourceParser xpp;
	private int eventType;
	
	private Font mFont;
	private Text leftText;
	
	private ITexture mTexture;
	private BitmapTextureAtlas mBitmapTextureAtlas;
	private Scene mScene;
	private Sound mExplosionSound;
	private TextureRegion narrateBox;
	
	private BuildableBitmapTextureAtlas mBuildableBitmapTextureAtlas;
	
	private ArrayList<SpriteItem> spriteList = new ArrayList<SpriteItem>();

	@Override
	public EngineOptions onCreateEngineOptions() {
		DisplayMetrics metrics = this.getBaseContext().getResources().getDisplayMetrics();
		CAMERA_WIDTH = metrics.widthPixels;
		CAMERA_HEIGHT = metrics.heightPixels;
		
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineOptions= new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		engineOptions.getAudioOptions().setNeedsSound(true);
		return engineOptions;
	}

	@Override
	public void onCreateResources() {
		
		try {
			this.mTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
				@Override
				public InputStream open() throws IOException {
					return getAssets().open("gfx/narrate_box.png");
				}
			});

			this.mTexture.load();
			this.narrateBox = TextureRegionFactory.extractFromTexture(this.mTexture);
		} catch (IOException e) {
			Debug.e(e);
		}
		
		this.mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		this.mFont.load();
	}

	@Override
	public Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		this.setReader();
			
		this.mScene = new Scene();
		this.mScene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));		
		
		this.mScene.setOnSceneTouchListener(new IOnSceneTouchListener() {
			@Override
			public boolean onSceneTouchEvent(final Scene pScene, final TouchEvent pSceneTouchEvent) {
				if(pSceneTouchEvent.isActionDown()) {
					//StoryActivity.this.loadNewTexture();
					//StoryActivity.this.setSound("explosion.ogg").play();
					StoryActivity.this.readItem();
				}

				return true;
			}
		});
		
		final Sprite narrateBoxSprite = new Sprite(0, this.CAMERA_HEIGHT/3*2, this.narrateBox,this.getVertexBufferObjectManager());
		narrateBoxSprite.setSize(this.CAMERA_WIDTH, this.CAMERA_HEIGHT/3);
		mScene.attachChild(narrateBoxSprite);
		
		this.readItem();
		
		return this.mScene;
	}

	
	private void loadNewTexture() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		this.mBitmapTextureAtlas  = new BitmapTextureAtlas(this.getTextureManager(), 32, 32, TextureOptions.BILINEAR);
		final ITextureRegion faceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "face_box.png", 0, 0);
		this.mBitmapTextureAtlas.load();

		final float x = (CAMERA_WIDTH - faceTextureRegion.getWidth()) * MathUtils.RANDOM.nextFloat();
		final float y = (CAMERA_HEIGHT - faceTextureRegion.getHeight()) * MathUtils.RANDOM.nextFloat();
		final Sprite clickToUnload = new Sprite(x, y, faceTextureRegion, this.getVertexBufferObjectManager());
		this.mScene.attachChild(clickToUnload);
	}
	
	public void readItem(){
		boolean animFlag = false;
		boolean easelinFlag = false;
		boolean altimg = false;
		boolean imageFlag = false;
		ArrayList<Point> path = new ArrayList<Point>();
		String fileName = "";
		
		try {
			eventType = xpp.getEventType();
			//reset the choices array
			while (eventType != XmlPullParser.END_DOCUMENT ){
				if(eventType == XmlPullParser.START_DOCUMENT){
					
				}else if(eventType == XmlPullParser.START_TAG){
					if(xpp.getName().equals("narrate")){
						eventType = xpp.next();
						String text = xpp.getText();
						mScene.detachChild(leftText);
						leftText = new Text(10, this.CAMERA_HEIGHT/3*2+10, this.mFont, cleanText(text), new TextOptions(HorizontalAlign.LEFT), getVertexBufferObjectManager());
						mScene.attachChild(leftText);
					}else if(xpp.getName().equals("repeatingbackground")){
						eventType = xpp.next();
						RepeatingSpriteBackground rep = repeatingBackground(xpp.getText());
						mScene.setBackground(rep);
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
					}else if((xpp.getName().equals("picx")) && animFlag){
						eventType = xpp.next();
						path.get(path.size()-1).picx = Integer.parseInt(xpp.getText());
						System.out.println("picx:"+path.get(path.size()-1).picx);
					}else if((xpp.getName().equals("picy")) && animFlag){
						eventType = xpp.next();
						path.get(path.size()-1).picy = Integer.parseInt(xpp.getText());
						System.out.println("picy:"+path.get(path.size()-1).picy);
					}else if(xpp.getName().equals("image")){
						eventType = xpp.next();
						fileName = xpp.getText();
						imageFlag = true;
					}else if(imageFlag && xpp.getName().equals("x")){
						eventType = xpp.next();
						path.add(new Point());
						
						path.get(path.size()-1).x = Integer.parseInt(xpp.getText());
					}else if(imageFlag && xpp.getName().equals("y")){						
						eventType = xpp.next();	
						path.get(path.size()-1).y = Integer.parseInt(xpp.getText());
					}else if((xpp.getName().equals("picsizex")) && imageFlag){
						eventType = xpp.next();
						path.get(path.size()-1).picsizex = Integer.parseInt(xpp.getText());
						//System.out.println("picx:"+path.get(path.size()-1).picx);
					}else if((xpp.getName().equals("picsizey")) && imageFlag){
						eventType = xpp.next();
						path.get(path.size()-1).picsizey = Integer.parseInt(xpp.getText());
						//System.out.println("picy:"+path.get(path.size()-1).picy);
					}else if(xpp.getName().equals("sound")){						
						eventType = xpp.next();
						this.setSound(xpp.getText()).play();
					}else if(xpp.getName().equals("easelinear")){						
						eventType = xpp.next();	
						easelinFlag = true;
						fileName = xpp.getText();
					}else if(easelinFlag && xpp.getName().equals("x")){						
						eventType = xpp.next();	
						path.add(new Point());
						path.get(path.size()-1).x = Integer.parseInt(xpp.getText());
					}else if(easelinFlag && xpp.getName().equals("y")){						
						eventType = xpp.next();	
						path.get(path.size()-1).y = Integer.parseInt(xpp.getText());
					}else if(xpp.getName().equals("alternatingimage")){
						eventType = xpp.next();	
						altimg = true;
						fileName = xpp.getText();
						//AnimatedSprite face = alternatingimage();
						//mScene.attachChild(face);
					}else if(altimg && xpp.getName().equals("x")){						
						eventType = xpp.next();	
						path.add(new Point());
						path.get(path.size()-1).x = Integer.parseInt(xpp.getText());
					}else if(altimg && xpp.getName().equals("y")){						
						eventType = xpp.next();	
						path.get(path.size()-1).y = Integer.parseInt(xpp.getText());
					}else if(xpp.getName().equals("remove")){
						eventType = xpp.next();	
						fileName = xpp.getText();
						this.remove(fileName);
					}
				}else if(eventType == XmlPullParser.END_TAG){
					//only stop reading if these end tags are found
					if(xpp.getName().equals("path")){
						System.out.println("set animation sprite on scene");
						spriteList.add(new SpriteItem(animatedSprite(fileName, path), fileName));
						mScene.attachChild(spriteList.get(spriteList.size()-1).sprite);
						animFlag =false;
					}
					if(xpp.getName().equals("easepoints")){
						System.out.println("set easelinear sprite on scene");
						Sprite face = this.easelinear(fileName, path);
						spriteList.add(new SpriteItem(face, fileName));
						mScene.attachChild(face);
						//float x = face.getX();
						//face.setPosition(x, 0);
						face.registerEntityModifier(new MoveModifier(3, path.get(0).x, path.get(1).x, path.get(0).y, path.get(1).y, EaseLinear.getInstance()));
						easelinFlag = false;
					}
					if(xpp.getName().equals("altimgpoints")){
						System.out.println("set altpoint sprite on scene");
						Sprite face = this.alternatingimage(fileName, path);
						spriteList.add(new SpriteItem(face, fileName));
						mScene.attachChild(face);
						altimg = false;
					}
					if(xpp.getName().equals("imageinfo")){
						System.out.println("set imageinfo sprite on scene");
						this.drawimage(fileName,path);
						
						imageFlag = false;
					}
					/*if(xpp.getName().equals("touch")){
						System.out.println("return touch");
					}else*/ if(xpp.getName().equals("picy") || xpp.getName().equals("picx") || xpp.getName().equals("x") || xpp.getName().equals("y")|| xpp.getName().equals("animatesprite")){
						System.out.println("detect x,y,animate");
					}else if(xpp.getName().equals("touch")){
						eventType = xpp.next();
						System.out.println("break from this loop");
						break;
					}else if(xpp.getName().equals("scene")){
						//same as touch except must clear all items on screen
						eventType = xpp.next();
						//
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
	
	}
	
	public void drawimage(String file, ArrayList<Point> point) {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		this.mBitmapTextureAtlas  = new BitmapTextureAtlas(this.getTextureManager(), point.get(0).picsizex, point.get(0).picsizey, TextureOptions.BILINEAR);
		final ITextureRegion faceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, file, 0, 0);
		this.mBitmapTextureAtlas.load();

		//final float x = (CAMERA_WIDTH - mPlayerTextureRegion.getWidth()) * MathUtils.RANDOM.nextFloat();
		//final float y = (CAMERA_HEIGHT - mPlayerTextureRegion.getHeight()) * MathUtils.RANDOM.nextFloat();
		final Sprite sprite = new Sprite(point.get(0).x, point.get(0).y, faceTextureRegion, this.getVertexBufferObjectManager());
		this.spriteList.add(new SpriteItem(sprite, file));
		this.mScene.attachChild(sprite);
	}
	public void remove(String fileName){

		final EngineLock engineLock = this.mEngine.getEngineLock();
		engineLock.lock();
		
		for(int i=0; i<this.spriteList.size(); i++){
			if(spriteList.get(i).fileName.equals(fileName)){
				/* Now it is save to remove the entity! */
				mScene.detachChild(spriteList.get(i).sprite);
				spriteList.get(i).sprite.dispose();
				spriteList.get(i).sprite = null;
				spriteList.remove(i);
				break;
			}
		}
		engineLock.unlock();
	}
	
	public AnimatedSprite alternatingimage(String file, ArrayList<Point> path){
		this.mBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(), 512, 256, TextureOptions.NEAREST);
		TiledTextureRegion mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBuildableBitmapTextureAtlas, this, file, 2, 1);

		try {
			this.mBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
			this.mBuildableBitmapTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		/* Quickly twinkling face. */
		final AnimatedSprite face = new AnimatedSprite(path.get(0).x, path.get(0).y, mFaceTextureRegion, this.getVertexBufferObjectManager());
		face.animate(500);
		return face;
	}
	
	public Sprite easelinear(String file, ArrayList<Point> path){
		
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 256, 128, TextureOptions.BILINEAR);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		TextureRegion mBadgeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, this, file, 97, 0);
		this.mBitmapTextureAtlas.load();
		Sprite badge = new Sprite(path.get(0).x, path.get(0).y, mBadgeTextureRegion, this.getVertexBufferObjectManager());
		return badge;
	}
	
	public void setReader(){
		try {
        	res = this.getResources();
    		xpp = res.getXml(R.xml.adam);
    		xpp.next();
        } catch (XmlPullParserException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }
	}
	
	public RepeatingSpriteBackground repeatingBackground(String filePath){
		return new RepeatingSpriteBackground(CAMERA_WIDTH, CAMERA_HEIGHT, getTextureManager(), AssetBitmapTextureAtlasSource.create(getAssets(), filePath), getVertexBufferObjectManager());
	}
	public Sound setSound(String file){
		SoundFactory.setAssetBasePath("mfx/");
		Sound sound = null;
		try {
			sound = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, file);
			if(sound.isLoaded()){
				System.out.println("LOADED SOUND");
			}else{
				System.out.println("sound not loaded");
			}
		} catch (final IOException e) {
			Debug.e(e);
		}
		return sound;
	}
	public AnimatedSprite animatedSprite(String fileName, final ArrayList<Point> points){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 128, 128);
		final TiledTextureRegion mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, fileName, 0, 0, 3, 4);
		this.mBitmapTextureAtlas.load();
		
		final AnimatedSprite player = new AnimatedSprite(0, 0, 48, 64, mPlayerTextureRegion, getVertexBufferObjectManager());

		Path path = new Path(points.size());
		for(int i=0; i<points.size();i++){
			path.to(points.get(i).x, points.get(i).y);
			System.out.println("path:"+i+", pts:"+points.get(i).x+", "+points.get(i).y);
		}		
		
		player.registerEntityModifier(new LoopEntityModifier(new PathModifier(30, path, null, new IPathModifierListener() {
			@Override
			public void onPathStarted(final PathModifier pPathModifier, final IEntity pEntity) {}

			@Override
			public void onPathWaypointStarted(final PathModifier pPathModifier, final IEntity pEntity, final int pWaypointIndex) {
				player.animate(new long[]{200, 200, 200}, points.get(pWaypointIndex).picx, points.get(pWaypointIndex).picy, true);
			}

			@Override
			public void onPathWaypointFinished(final PathModifier pPathModifier, final IEntity pEntity, final int pWaypointIndex) {	}

			@Override
			public void onPathFinished(final PathModifier pPathModifier, final IEntity pEntity) {

			}
		})));
		return player;  
	}
	
	public String cleanText(String string){
		String newone = "";
		for(int i=0; i <string.length()-1;i++){
			String thing = string.charAt(i)+"";
			if(thing.equals("\\")){
				newone = newone+"\n";
				i++;
			}else{
				newone = newone+ string.charAt(i);
			}
		}
		return newone;		
	}
	/*public static final int CAMERA_WIDTH = 480;
		public  static final int CAMERA_HEIGHT = 320;

		public BoundCamera mBoundChaseCamera;
		
		//A reference to the current scene
		public Scene mCurrentScene;
		public static StoryActivity instance;
		
		
		
		@Override
		public EngineOptions onCreateEngineOptions() {
			instance = this;
			//Toast.makeText(this, "The tile the player is walking on will be highlighted.", Toast.LENGTH_LONG).show();

			this.mBoundChaseCamera = new BoundCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

			return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mBoundChaseCamera);
		}

		@Override
		public Scene onCreateScene() {
			this.mEngine.registerUpdateHandler(new FPSLogger());
			mCurrentScene = new AnimationScene();
			
			return mCurrentScene;
		}
		
		public void setCurrentScene(Scene scene) {
		    mCurrentScene = scene;
		    getEngine().setScene(mCurrentScene);
		}
		
		public static StoryActivity getSharedInstance() {
			return instance;
		}

		@Override
		protected void onCreateResources() {
			// TODO Auto-generated method stub
			
		}
		
		
*/
		

}
