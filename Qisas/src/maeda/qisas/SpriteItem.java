package maeda.qisas;

import org.andengine.audio.sound.Sound;
import org.andengine.entity.sprite.Sprite;

public class SpriteItem {
	Sprite sprite;
	String fileName;
	Sound sound;
	boolean isBackground = false;

	
	public SpriteItem(Sprite sprite, String fileName){
		this.sprite = sprite;
		this.fileName = fileName;
	}
	
	public SpriteItem(Sound sound, String filename){
		this.sound =sound;
		this.fileName=filename;
	}
	
}
