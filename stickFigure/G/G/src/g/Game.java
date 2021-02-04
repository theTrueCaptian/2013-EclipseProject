package g;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;
import java.util.StringTokenizer;
/**
 *
 * @author maeda hanafi
 * Jan 11
 * Online 
 */
enum STATE { REQUEST_USER_NAME_PASSWORD, JEOPARDY, CONNECTING, CHAT_ROOM, READ };
public class Game {
    //socket
    GSocket socket=null;
    //client data
    int clientID = -1;
    int x, y, z;
    long time;
    
    //frame
    Frame frame;
    //states
    STATE state;
    //user/pass
    String user = new String();
    String pass = new String();
    
   public Game(){
        start();
              
    }

    public void start(){
        state = STATE.CONNECTING;

        socket = new GSocket(this);
        socket.start();

        System.out.println("Create frame");
        frame = new Frame(this, socket);
        System.out.println("Creating exit");
        WinClose quit = new WinClose();
        frame.addWindowListener( quit );


        state = STATE.REQUEST_USER_NAME_PASSWORD;
        requestUserPass();
        System.out.println("Welcome");

        state = STATE.CHAT_ROOM;
        receiveUsersOnline();
        while(true){
            //
        }
    }

    private void requestUserPass(){
        state = STATE.REQUEST_USER_NAME_PASSWORD;
        while(frame.UPPanel==null){ }
        while(frame.UPPanel.state !=UserPassState.SUCCESS){ }
    }

    private void receiveUsersOnline(){
        String result = socket.recieve();
        StringTokenizer st1 = new StringTokenizer(result, " |");
        if(st1.nextToken().equals("online")){
            System.out.println("Available online:");
            //format online name|name|name
            while(st1.hasMoreTokens()){
                System.out.println(""+st1.nextToken());
            }
        }
    }

    public static void main(String args[]){
        System.out.println("HELLO THERE");
        Game g = new Game();
    }



  public class WinClose extends WindowAdapter {
        @Override
     public void windowClosing( WindowEvent e ) {
        if(socket.state==SocketState.CONNECTED){
            socket.sendMessage("exit");
            socket.close();
        }
        System.exit(0);
    }
}

   
}