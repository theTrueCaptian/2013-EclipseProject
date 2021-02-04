
package server;

import java.util.ArrayList;

/**
 *
 * @author user
 */
public class MapManager extends Thread{
    //a list of existing hallways
    ArrayList<Hallway> existingHallways;

    public MapManager(){
        existingHallways = new ArrayList<Hallway>();
    }
    
    @Override
    public void run(){

    }
}
