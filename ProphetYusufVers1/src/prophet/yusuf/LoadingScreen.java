package prophet.yusuf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Environment;
import android.view.View;

public class LoadingScreen extends View{

	Context context;
	
	public LoadingScreen(Context context) {
		super(context);
		this.context = context;
		
		Stuff.background1=load("background1.png");
		//Stuff.background2=load("background2.png");
		Stuff.menubackground = load("bluedecoration.jpg");
		Stuff.choicebutton = load("choicebutton.png");
		Stuff.parchment = load("parchment.png");
				
		getscores();
	}
	
	public void onDraw(Canvas canvas){
		
		Paint paint=new Paint();
		paint.setAntiAlias(true);       //for smooth rendering
		paint.setARGB(255, 0, 255, 0);    //setting the paint color
		
		canvas.drawText("Loading...", canvas.getWidth()/2, canvas.getHeight()/2, paint);
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
	
	public void getscores(){
		Stuff.grades = new ArrayList<String>();
		BufferedReader in = null;
		String externalStoragePath= Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator;
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(externalStoragePath + "scores.txt")));
            String read;
            while((read=in.readLine()) != null){
            	Stuff.grades.add(read);
            }
            /*if(Stuff.grades!=null){
	            //for(int i=0; i<Stuff.grades.length; i++)
	        	//	System.out.println(Stuff.grades[i]);
            
            }else{
            	Stuff.topscores = new int[5];
            	for(int i=0; i<Stuff.topscores.length; i++)
            		Stuff.topscores[i] = 0;
            }*/
            

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
	
}
