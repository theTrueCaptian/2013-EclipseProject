package stickfigure;
import java.util.Random;

/**
 *
 * @author maeda
 */

public class RandomNumberGenerator
{
    int seed;
    int lowRange, highRange;
    Random rnd;
    public RandomNumberGenerator(int argSeed, int argLowRange, int argHighRange){
        seed  = argSeed;
        lowRange  = argLowRange;
        highRange = argHighRange;
        rnd = new Random();
        rnd.setSeed(seed);
    }
    public RandomNumberGenerator(){
        seed  = 10;
        lowRange = 0;
        highRange = 1000000;
        rnd = new Random();
        rnd.setSeed(seed);
    }

    public int getRandomNumber(){
        int range = (highRange -lowRange);
        int d = lowRange + rnd.nextInt(range);
        
        return d;

    }


}


