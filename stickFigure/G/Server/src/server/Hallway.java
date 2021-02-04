
package server;

import java.util.ArrayList;

/**
 *
 * @author user
 */
//an instance of a hallway
public class Hallway {
    ArrayList<ServerThread> usersInRoom;
    
    Hallway(){
        usersInRoom = new ArrayList<ServerThread>();
    }

    public void addUser(ServerThread inUser){
        usersInRoom.add(inUser);
    }

    public void deleteUser(int index){
        usersInRoom.remove(index);
    }
}
