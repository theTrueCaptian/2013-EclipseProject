package maeda.qisas;

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
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
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
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;
import org.andengine.util.modifier.ease.EaseLinear;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.opengl.GLES20;
import android.util.DisplayMetrics;


enum AppStates{QUIZ, STORY}
enum QuizStates{NARRATE, QUESTION, CORRECT,WRONG}
public class GameActivity extends SimpleBaseGameActivity implements IOnMenuItemClickListener {

	private int CAMERA_WIDTH = 720;
	private int CAMERA_HEIGHT = 480;

	private Resources res;
	private XmlResourceParser xpp;
	private int eventType;
	
	private Font mFont;
	private Text leftText;
	private Text scoreBoard;
	private int answeredQuestions = 0;
	
	private Camera camera;
	private ITexture mTexture;
	private BitmapTextureAtlas mBitmapTextureAtlas;
	private Scene mScene;
	private Scene questionScene;
	private Sound correct;
	private Sound wrong;
	private TextureRegion narrateBox;
	private Sprite narrateBoxSprite;
	private TextureRegion choiceBox;
	private Sprite choiceBoxSprite,choiceBoxSprite1,choiceBoxSprite2;
	
	private BuildableBitmapTextureAtlas mBuildableBitmapTextureAtlas;
	
	private ArrayList<SpriteItem> spriteList = new ArrayList<SpriteItem>();
	
	private ArrayList<SpriteItem> playlist = new ArrayList<SpriteItem>();
			
	//the arraylist to the choices available to the user during question mode
	private ArrayList<String> choices = new ArrayList<String>();
	int correctIndex=0;
	private int score = 0;
	
	private AppStates state = AppStates.STORY;
	private QuizStates quizstate = QuizStates.NARRATE;

	@Override
	public EngineOptions onCreateEngineOptions() {
		
		
		DisplayMetrics metrics = this.getBaseContext().getResources().getDisplayMetrics();
		CAMERA_WIDTH = metrics.widthPixels;
		CAMERA_HEIGHT = metrics.heightPixels;
		
		
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineOptions= new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		engineOptions.getAudioOptions().setNeedsSound(true);
		return engineOptions;
	}

	@Override
	public void onCreateResources() {
		if(!GlobalVariables.prophadam){
			for(int i=0; i<GlobalVariables.soundFileYusuf.length; i++){
				SoundFactory.setAssetBasePath("mfx/");
				try {
					this.playlist.add(new SpriteItem(SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, GlobalVariables.soundFileYusuf[i]), GlobalVariables.soundFileYusuf[i]));
				} catch (final IOException e) {
					Debug.e(e);
				}
			}
		}else if(GlobalVariables.prophadam){
			for(int i=0; i<GlobalVariables.soundFileAdam.length; i++){
				SoundFactory.setAssetBasePath("mfx/");
				try {
					this.playlist.add(new SpriteItem(SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, GlobalVariables.soundFileAdam[i]), GlobalVariables.soundFileAdam[i]));
				} catch (final IOException e) {
					Debug.e(e);
				}
			}
		}
		SoundFactory.setAssetBasePath("mfx/");
		try {
			this.correct = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "correct.wav");
			this.wrong = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "wrong.wav");
		} catch (final IOException e) {
			Debug.e(e);
		}
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
		
		try {
			this.mTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
				@Override
				public InputStream open() throws IOException {
					return getAssets().open("gfx/choice_box.png");
				}
		});

			this.mTexture.load();
			this.choiceBox = TextureRegionFactory.extractFromTexture(this.mTexture);
		} catch (IOException e) {
			Debug.e(e);
		}
		
		this.mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		this.mFont.load();
		//init lefttext nessecasry before using it to narrate
		leftText = new Text(10, this.CAMERA_HEIGHT/3*2+10, this.mFont, "", new TextOptions(HorizontalAlign.LEFT), getVertexBufferObjectManager());
		this.scoreBoard = new Text(10, 10, this.mFont, score+"", new TextOptions(HorizontalAlign.LEFT), getVertexBufferObjectManager());
	}

	@Override
	public Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		
		this.questionScene = this.createMenuScene();

		this.setReader();
			
		this.mScene = new Scene();
		this.mScene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));		
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.mBitmapTextureAtlas  = new BitmapTextureAtlas(this.getTextureManager(), 300, 200, TextureOptions.DEFAULT);
		ITextureRegion faceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "introbg.png", 0, 0);
		this.mBitmapTextureAtlas.load();
		Sprite introbg = new Sprite(0, 0, this.CAMERA_WIDTH, this.CAMERA_HEIGHT, faceTextureRegion, this.getVertexBufferObjectManager());
		mScene.attachChild(introbg);
		
		this.mScene.setChildScene(this.questionScene, false, true, true);
		
		return this.mScene;
	}
	
	//called after quiz or story is pressed
	private void setScene(){
		System.out.println("Setting the scene");
		if(this.mScene.hasChildScene()) {
			System.out.println("Clearing the question scene");
			questionScene.back();
		}
		//touch listener to go to next page
		this.mScene.setOnSceneTouchListener(new IOnSceneTouchListener() {
			@Override
			public boolean onSceneTouchEvent(final Scene pScene, final TouchEvent pSceneTouchEvent) {
				if(pSceneTouchEvent.isActionDown()) {
					//StoryActivity.this.loadNewTexture();
					//StoryActivity.this.setSound("explosion.ogg").play();
					GameActivity.this.readItem();
				}

				return true;
			}
		});
		
		choiceBoxSprite = new Sprite(0, this.CAMERA_HEIGHT/3*2, this.choiceBox,this.getVertexBufferObjectManager());
		choiceBoxSprite1 = new Sprite(0, this.CAMERA_HEIGHT/3*2, this.choiceBox,this.getVertexBufferObjectManager());
		choiceBoxSprite2 = new Sprite(0, this.CAMERA_HEIGHT/3*2, this.choiceBox,this.getVertexBufferObjectManager());
		
		narrateBoxSprite = new Sprite(0, this.CAMERA_HEIGHT/3*2, this.narrateBox,this.getVertexBufferObjectManager());
		narrateBoxSprite.setSize(this.CAMERA_WIDTH, this.CAMERA_HEIGHT/3);
		mScene.attachChild(narrateBoxSprite);
		
		this.readItem();
	}
	
	private void readItem(){
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
					if(xpp.getName().equals("background")){
						eventType = xpp.next();
						loadBackground(xpp.getText());
						mScene.detachChild(this.narrateBoxSprite);
						mScene.attachChild(this.narrateBoxSprite);
						
						
					}else if(xpp.getName().equals("narrate") || xpp.getName().equals("answer")){
						if(this.mScene.hasChildScene()) {
							/* Remove the menu and reset it. */
							this.questionScene.back();
						}
						if(this.state==AppStates.QUIZ){
							//display score board
							mScene.detachChild(this.scoreBoard);
							this.scoreBoard = new Text(10, 10, this.mFont, "Correct: "+score+"/"+answeredQuestions, new TextOptions(HorizontalAlign.LEFT), getVertexBufferObjectManager());
							mScene.attachChild(scoreBoard);
						}
						this.quizstate = QuizStates.NARRATE;
						eventType = xpp.next();
						String text = xpp.getText();
						mScene.detachChild(leftText);
						leftText = new Text(10, this.CAMERA_HEIGHT/3*2+10, this.mFont, cleanText(text), new TextOptions(HorizontalAlign.LEFT), getVertexBufferObjectManager());
						mScene.attachChild(leftText);
					}else if(xpp.getName().equals("repeatingbackground")){
						eventType = xpp.next();
						fileName = xpp.getText();
						RepeatingSpriteBackground rep = repeatingBackground(fileName);
						//this.spriteList.add(new SpriteItem((Sprite)rep, fileName));
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
						String soundfile = xpp.getText();
						for(int i=0; i<this.playlist.size(); i++){
							if(this.playlist.get(i).fileName.equals(soundfile)){
								this.playlist.get(i).sound.play();
								break;
							}
						}
						//this.setSound(xpp.getText()).play();
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
					}//quiz related info
					else if(this.state==AppStates.QUIZ && xpp.getName().equals("question")){
						eventType = xpp.next();	
						this.quizstate = QuizStates.QUESTION;
						String text = xpp.getText();
						System.out.println("QUESTION TEXT:"+text);
						mScene.detachChild(leftText);
						leftText = new Text(10, this.CAMERA_HEIGHT/3*2+10, this.mFont, cleanText(text), new TextOptions(HorizontalAlign.LEFT), getVertexBufferObjectManager());
						mScene.attachChild(leftText);
						//renew the choice list
						choices = new ArrayList<String>();
					}else if(this.state==AppStates.QUIZ && xpp.getName().equals("wrong")){
						eventType = xpp.next();
						choices.add(xpp.getText());
					}else if(this.state==AppStates.QUIZ && xpp.getName().equals("correct")){						
						eventType = xpp.next();
						choices.add(xpp.getText());
						correctIndex = choices.size() - 1;
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
					if(this.state==AppStates.QUIZ && xpp.getName().equals("question")){
						//display question with choices
						//draw menu
						this.displayMenu();
					}
					if(xpp.getName().equals("picy") || xpp.getName().equals("picx") || xpp.getName().equals("x") || xpp.getName().equals("y")|| xpp.getName().equals("animatesprite")){
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
					}else if(xpp.getName().equals("story")){
						this.finish();
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
	
	private void displayMenu(){
		if(this.mScene.hasChildScene()) {
			questionScene.back();
		}
		
		
		MenuScene menuScene = new MenuScene(this.camera);
		
				
		IMenuItem firstMenuItem = new ColorMenuItemDecorator(new TextMenuItem(0 , this.mFont, cleanText(this.choices.get(0)), this.getVertexBufferObjectManager()), new Color(1,0,0), new Color(0,0,0));
		firstMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		firstMenuItem.setPosition(camera.getWidth()/2-firstMenuItem.getWidth()/2, camera.getHeight()/2-firstMenuItem.getHeight()/2-this.narrateBoxSprite.getHeight());
		menuScene.addMenuItem(firstMenuItem);
		choiceBoxSprite.setSize(camera.getWidth(), firstMenuItem.getHeight());
		choiceBoxSprite.setPosition(0, firstMenuItem.getY());
		mScene.attachChild(choiceBoxSprite);
		
		IMenuItem secondMenuItem = new ColorMenuItemDecorator(new TextMenuItem(1 , this.mFont, cleanText(this.choices.get(1)), this.getVertexBufferObjectManager()), new Color(1,0,0), new Color(0,0,0));
		secondMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		secondMenuItem.setPosition(camera.getWidth()/2-secondMenuItem.getWidth()/2, camera.getHeight()/2-secondMenuItem.getHeight()/2+40*2-this.narrateBoxSprite.getHeight());
		menuScene.addMenuItem(secondMenuItem);
		choiceBoxSprite1.setSize(camera.getWidth(), secondMenuItem.getHeight());
		choiceBoxSprite1.setPosition(0, secondMenuItem.getY());
		mScene.attachChild(choiceBoxSprite1);
		
		IMenuItem thirdMenuItem = new ColorMenuItemDecorator(new TextMenuItem(2 , this.mFont, cleanText(this.choices.get(2)), this.getVertexBufferObjectManager()), new Color(1,0,0), new Color(0,0,0));
		thirdMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		thirdMenuItem.setPosition(camera.getWidth()/2-thirdMenuItem.getWidth()/2, camera.getHeight()/2-thirdMenuItem.getHeight()/2+40*2+40*2-this.narrateBoxSprite.getHeight());
		menuScene.addMenuItem(thirdMenuItem);
		choiceBoxSprite2.setSize(camera.getWidth(), thirdMenuItem.getHeight());
		choiceBoxSprite2.setPosition(0, thirdMenuItem.getY());
		mScene.attachChild(choiceBoxSprite2);

		menuScene.setBackgroundEnabled(false);

		menuScene.setOnMenuItemClickListener(this);
		
		questionScene = menuScene;
		//questionScene.attachChild(menuScene);
		this.mScene.setChildScene(this.questionScene, false, true, true);
	}
	
	protected MenuScene createMenuScene() {
		final MenuScene menuScene = new MenuScene(this.camera);
		
		IMenuItem firstMenuItem = new ColorMenuItemDecorator(new TextMenuItem(0 , this.mFont, "View Story", this.getVertexBufferObjectManager()), new Color(1,0,0), new Color(0,0,0));
		firstMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		firstMenuItem.setPosition(camera.getWidth()/2-firstMenuItem.getWidth()/2, camera.getHeight()/2-firstMenuItem.getHeight()/2);
		menuScene.addMenuItem(firstMenuItem);

		IMenuItem secondMenuItem = new ColorMenuItemDecorator(new TextMenuItem(1 , this.mFont, "Do Quiz", this.getVertexBufferObjectManager()), new Color(1,0,0), new Color(0,0,0));
		secondMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		secondMenuItem.setPosition(camera.getWidth()/2-secondMenuItem.getWidth()/2, camera.getHeight()/2-secondMenuItem.getHeight()/2+firstMenuItem.getHeight()*2);
		menuScene.addMenuItem(secondMenuItem);
		
		menuScene.setBackgroundEnabled(false);
		
		menuScene.setOnMenuItemClickListener(new IOnMenuItemClickListener(){
			@Override
			public boolean onMenuItemClicked(MenuScene pMenuScene,IMenuItem pMenuItem, float pMenuItemLocalX,	float pMenuItemLocalY) {
				switch(pMenuItem.getID()) {
				case 0:
					state=AppStates.STORY;
					System.out.println("STORY");
					//questionScene.back();
					setScene();
					return true;
				case 1:
					state=AppStates.QUIZ;
					System.out.println("QUIZ");
					//questionScene.back();
					setScene();
					return true;
				default:
					return false;
				}
			}		
		});
		return menuScene;
	}
	
	@Override
	public boolean onMenuItemClicked(final MenuScene pMenuScene, final IMenuItem pMenuItem, final float pMenuItemLocalX, final float pMenuItemLocalY) {
		
		switch(pMenuItem.getID()) {
			case 0:
				if(this.correctIndex==0){
					this.quizstate=QuizStates.CORRECT;
					System.out.println("correct");
					score++;
					//Looper.prepare();
					//Toast.makeText(this, "CORRECT", Toast.LENGTH_LONG).show();
					//this.correct.play();
				}else{
					//Looper.prepare();
					//Toast.makeText(this, "WRONG", Toast.LENGTH_LONG).show();
					System.out.println("wrong");
					//this.wrong.play();
					this.quizstate=QuizStates.WRONG;
				}
				this.answeredQuestions++;
				this.mScene.detachChild(this.choiceBoxSprite);
				this.mScene.detachChild(this.choiceBoxSprite1);
				this.mScene.detachChild(this.choiceBoxSprite2);
				this.quizstate = QuizStates.NARRATE;
				readItem();

				return true;
			case 1:
				if(this.correctIndex==1){
					System.out.println("correct");
					//Toast.makeText(this, "CORRECT", Toast.LENGTH_LONG).show();
					//this.correct.play();
					this.quizstate=QuizStates.CORRECT;
					score++;
				}else{
					//Toast.makeText(this, "WRONG", Toast.LENGTH_LONG).show();
					System.out.println("wrong");
					//this.wrong.play();
					this.quizstate=QuizStates.WRONG;
				}
				this.answeredQuestions++;
				this.mScene.detachChild(this.choiceBoxSprite);
				this.mScene.detachChild(this.choiceBoxSprite1);
				this.mScene.detachChild(this.choiceBoxSprite2);
				this.quizstate = QuizStates.NARRATE;
				readItem();
				return true;
			case 2:
				if(this.correctIndex==2){
					System.out.println("correct");
					//this.correct.play();
					this.quizstate=QuizStates.CORRECT;
					score++;
				}else{
					System.out.println("wrong");
					//this.wrong.play();
					this.quizstate=QuizStates.WRONG;
				}
				this.answeredQuestions++;
				this.mScene.detachChild(this.choiceBoxSprite);
				this.mScene.detachChild(this.choiceBoxSprite1);
				this.mScene.detachChild(this.choiceBoxSprite2);
				this.quizstate = QuizStates.NARRATE;
				readItem();
				return true;
			default:
				return false;
		}
	}
	
	private void drawimage(String file, ArrayList<Point> point) {
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
	private void remove(String fileName){

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
	
	private AnimatedSprite alternatingimage(String file, ArrayList<Point> path){
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
	
	private Sprite easelinear(String file, ArrayList<Point> path){
		
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 256, 128, TextureOptions.BILINEAR);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		TextureRegion mBadgeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, this, file, 97, 0);
		this.mBitmapTextureAtlas.load();
		Sprite badge = new Sprite(path.get(0).x, path.get(0).y, mBadgeTextureRegion, this.getVertexBufferObjectManager());
		return badge;
	}
	
	private void setReader(){
		try {
        	res = this.getResources();
        	if(GlobalVariables.prophadam){
        		xpp = res.getXml(R.xml.adam);
        	}else if(!GlobalVariables.prophadam){
        		xpp = res.getXml(R.xml.yusuf5);
        	}
    		xpp.next();
        } catch (XmlPullParserException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }
	}
	
	private RepeatingSpriteBackground repeatingBackground(String filePath){
		return new RepeatingSpriteBackground(CAMERA_WIDTH, CAMERA_HEIGHT, getTextureManager(), AssetBitmapTextureAtlasSource.create(getAssets(), filePath), getVertexBufferObjectManager());
	}
	private Sound setSound(String file){
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
	private AnimatedSprite animatedSprite(String fileName, final ArrayList<Point> points){
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
	
	private void loadBackground(String fileName) {
		//check if there is a background
		for(int i=0;i<spriteList.size();i++){
			if(this.spriteList.get(i).isBackground){
				this.remove(this.spriteList.get(i).fileName);
			}
		}
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		this.mBitmapTextureAtlas  = new BitmapTextureAtlas(this.getTextureManager(), 300, 200, TextureOptions.DEFAULT);
		final ITextureRegion faceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, fileName, 0, 0);
		this.mBitmapTextureAtlas.load();
		//faceTextureRegion.setTextureSize(this.CAMERA_WIDTH, this.CAMERA_WIDTH);
		
		final Sprite clickToUnload = new Sprite(0, 0, this.CAMERA_WIDTH, this.CAMERA_HEIGHT, faceTextureRegion, this.getVertexBufferObjectManager());
		//clickToUnload.setScale(3);
		this.spriteList.add(new SpriteItem(clickToUnload, fileName));
		this.spriteList.get(spriteList.size()-1).isBackground = true;
		this.mScene.attachChild(clickToUnload);
	}
	
	private String cleanText(String string){
		if(string==null || string.length()<=0)
			return string;
		String newone = "";
		int numcharinline = 0;
		for(int i=0; i <string.length()-1;i++){
			String thing = string.charAt(i)+"";
			String nextthing = "";
				if(i<string.length()-2){nextthing = string.charAt(i+1)+"";}
			if(thing.equals("\\") || numcharinline >= getMaxNumChar() && nextthing.equals(" ")){
				if(thing.equals("\\")){
					newone = newone+"\n";
					i++;
				}else{
					newone = newone+ string.charAt(i)+"\n";
				}
				
				numcharinline = 0;
			}else{
				newone = newone+ string.charAt(i);
				numcharinline++;
			}
		}
		return newone;		
	}
	
	private int getMaxNumChar(){
		return (int)((this.CAMERA_WIDTH-60)/(this.leftText.getFont().getLineHeight()/2));
		
	}
}

