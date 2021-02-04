package prophet.yusuf;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class FileIO {
	Context context;
	
	public FileIO(Context context){
		this.context  = context;
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
}
