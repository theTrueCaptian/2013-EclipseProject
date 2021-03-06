package maeda.killergame;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.tapfortap.AdView;
import com.tapfortap.AppWall;
import com.tapfortap.Interstitial;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

public class LoadingScreen extends View{

	Context context;
	
	public LoadingScreen(Context context) {
		super(context);
		this.context = context;
		
		
		
		Stuff.gameover=load("gameover.png");
		Stuff.help=load("help.png");
		Stuff.highscore=load("highscore.png");
		Stuff.resumebutton=load("resumebutton.png");
		Stuff.pausebutton=load("pausebutton.png");
		Stuff.scoreingame=load("scoreingame.png");
		Stuff.start=load("start.png");
		Stuff.taptobegin=load("taptobegin.png");
		Stuff.instruction1=load("instruction1.png"); 
		Stuff.instruction2=load("instruction2.png");
		Stuff.instruction3=load("instruction3.png");
		Stuff.instruction4=load("instruction4.png");
		Stuff.highscoreacheived = load("highscoreachieved.png");
		Stuff.proceed = load("proceed.png");
		Stuff.proceed1 = load("proceed1.png");
		Stuff.proceed2 = load("proceed2.png");
		Stuff.proceed3 = load("proceed3.png");
		Stuff.proceed4 = load("proceed4.png");
		Stuff.proceed5 = load("proceed5.png");
		//add the proceeds to the array
		Stuff.proceedList.add(Stuff.proceed);
		Stuff.proceedList.add(Stuff.proceed1);
		Stuff.proceedList.add(Stuff.proceed2);
		Stuff.proceedList.add(Stuff.proceed3);
		Stuff.proceedList.add(Stuff.proceed4);
		Stuff.proceedList.add(Stuff.proceed5);
		
		Stuff.title=load("title.png");
		Stuff.pauseingame = load("pauseingame.png");
		getscores();
		//Stuff.ppkresurection = R.raw.ppkresurection;
		//System.out.println(""+Stuff.ppkresurection);//loadINT("ppkresurection.ogg");
		Stuff.explosion = R.raw.ain;
		Stuff.menubackground = load("menubackground.png");
		Stuff.menubackground1 = load("menubackground1.png");
		Stuff.menubackground2 = load("menubackground2.png");
		Stuff.lifelost = load("lifelost.png");
		Stuff.player = load("player.png");
		Stuff.star = load("star.png");
		Stuff.star2 = load("star2.png");
	}
	
	public void onDraw(Canvas canvas){
		
		Paint paint=new Paint();
		paint.setAntiAlias(true);       //for smooth rendering
		paint.setARGB(255, 0, 255, 0);    //setting the paint color
		
		canvas.drawText("Loading...", canvas.getWidth()/2, canvas.getHeight()/2, paint);
	}
	
	public void getscores(){
		/*AssetManager assetManager = context.getAssets();
		InputStream inputStream = null;
		try {
			inputStream = assetManager.open("scores.txt");
			Stuff.topscores = convertScores(loadTextFile(inputStream));
			inputStream.close();
			
		} catch (IOException e) {
			System.out.println("Unable to load");
		} finally {
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (IOException e) {
					System.out.println("Couldn't close file");
				}
		}
		*/
		BufferedReader in = null;
		String externalStoragePath= Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator;
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(externalStoragePath + "scores.txt")));
            Stuff.topscores = convertScores(in.readLine());
           
            if(Stuff.topscores!=null){
	            for(int i=0; i<Stuff.topscores.length; i++)
	        		System.out.println(Stuff.topscores[i]);
            
            }else{
            	Stuff.topscores = new int[5];
            	for(int i=0; i<Stuff.topscores.length; i++)
            		Stuff.topscores[i] = 0;
            }
            

        } catch (IOException e) {
            // :( It's ok we have defaults
        } catch (NumberFormatException e) {
            // :/ It's ok, defaults save our day
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
            }
        }
	}
	
	public String loadTextFile(InputStream inputStream) throws IOException {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		byte[] bytes = new byte[4096];
		int len = 0;
		while ((len = inputStream.read(bytes)) > 0)
		byteStream.write(bytes, 0, len);
		return new String(byteStream.toByteArray(), "UTF8");
	}
	
	public int[] convertScores(String inScores){
		String[] tokens = inScores.split(" ");
		//convert to integer
		int[] scores = new int[tokens.length];
		for(int i=0; i<tokens.length; i++){
			scores[i] = Integer.parseInt(tokens[i]);
			System.out.println("score"+i+" "+scores[i]);
		}
		return scores;
	}
	
	public Bitmap load(String file){
		InputStream inputStream = null;
		try {
            AssetManager assetManager = context.getAssets();
            inputStream = assetManager.open(file);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
                   
            return bitmap;
        } catch (IOException e) {
            // silently ignored, bad coder monkey, baaad!
        } finally {
            // we should really close our input streams here.
        	if (inputStream != null)
				try {
					inputStream.close();
				} catch (IOException e) {
					System.out.println("Couldn't close file");
				}
        }
		return null;
	}
	
	public int loadINT(String file){
		InputStream inputStream = null;
		try {
            AssetManager assetManager = context.getAssets();
            inputStream = assetManager.open(file);
            //Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            int num =  inputStream.read();
            System.out.println("num:"+num);
            inputStream.close();
                   
            return num;
        } catch (IOException e) {
            // silently ignored, bad coder monkey, baaad!
        	System.out.println("cant find the file");
        } finally {
            // we should really close our input streams here.
        	if (inputStream != null)
				try {
					inputStream.close();
				} catch (IOException e) {
					System.out.println("Couldn't close file");
				}
        }
		return 0;
	}

	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		
	}

}
