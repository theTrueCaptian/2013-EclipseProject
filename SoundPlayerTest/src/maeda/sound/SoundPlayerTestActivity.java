package maeda.sound;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;

public class SoundPlayerTestActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        initSounds();
        explode();
    }
    public static final int SOUND_EXPLOSION = 1;

    private SoundPool soundPool;
    private HashMap<Integer, Integer> soundPoolMap;

    private void initSounds() {
         soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
         soundPoolMap = new HashMap<Integer, Integer>();
         soundPoolMap.put(SOUND_EXPLOSION, soundPool.load(getBaseContext(), R.raw.ain, 1));
    }
              
    public void playSound(int sound) {
        /* Updated: The next 4 lines calculate the current volume in a scale of 0.0 to 1.0 */
        AudioManager mgr = (AudioManager)getBaseContext().getSystemService(Context.AUDIO_SERVICE);
        float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
        float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);    
        float volume = streamVolumeCurrent / streamVolumeMax;
        
        /* Play the sound with the correct volume */
        soundPool.play(soundPoolMap.get(sound), volume, volume, 1, 0, 1f);     
    }

    public void explode() {
        playSound(SOUND_EXPLOSION);
    } 
}