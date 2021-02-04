package dodgeball.popthat.android;

import org.andengine.util.color.Color;

public class GlobalVariables {
	public static boolean SPLASHSHOWN = false;
	public static String[] enemyballfiles = {"enemyball.png","enemyball2.png", "enemyball3.png", "enemyball4.png"
		};
	public static String[] backgroundfiles = {"forestbg.png","pondbg.png", "beachbg.png", "tundrabg.png",
			"palacebg.png", "royaltybg.png", "firebg.png", "rainbowdancefloorbg.png"};
	public static Color[] dispcolors ={Color.BLACK, Color.YELLOW, Color.BLUE, Color.CYAN, Color.GREEN, Color.PINK, Color.WHITE};
	public static String[] messages = {"Faster!", "Hurry!!!", "Good Job!", "Superb!", "Awesome!", "Keep it up!", "Fantastic!", "No Way!", "Impossible!!!!!"};
	public static int[] hotPoints = {100, 300, 600, 1000, 2000, 4000, 7000, 10000, 20000,40000,70000,100000};
	public static String menuSoundString = "buttonmenu.wav";
	public static String killSound[] = {"objecttouch.wav", "gameobject2.wav","killobject.wav"};
	public static String knighthurt = "objecttouch2.wav";
}
