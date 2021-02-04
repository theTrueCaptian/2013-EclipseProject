/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package g;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SMART User
 */
enum SocketState{DISCONNECTED, REQUEST_CONNECTION, CONNECTED, UNSUCCESSFULL_REQUEST}
public class GSocket extends Thread {
    Socket socket = null;
    //streams
    PrintWriter out = null;
    BufferedReader in = null;

    Game game;

    SocketState state = SocketState.DISCONNECTED;
    boolean unsuccessful = false;
    
    public GSocket(Game game){
        this.game = game;
       
    }

    @Override
    public void run() {

        startConnection();
        if(state!=SocketState.UNSUCCESSFULL_REQUEST)
        receiveID();
    }

    private void startConnection(){
        state = SocketState.REQUEST_CONNECTION;
        
        try {
            socket = new Socket("localhost", 4444);System.out.println();

            if(socket!=null){
                out = new PrintWriter(socket.getOutputStream(), true);
                out.flush();
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            state = SocketState.UNSUCCESSFULL_REQUEST;
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            state = SocketState.UNSUCCESSFULL_REQUEST;
        }
        checkConnection();
    }

    private void checkConnection(){
        if(in!=null && out!=null && socket!=null){
            state = SocketState.CONNECTED;
            System.out.println("CONNECTED");
        }else{
            state = SocketState.UNSUCCESSFULL_REQUEST;
            System.out.println("UNSUCESSFULL_REQUEST");
            unsuccessful = true;
        }
    }
    
    private  void receiveID() {
        try {
            game.clientID = Integer.parseInt(in.readLine());
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            state = SocketState.UNSUCCESSFULL_REQUEST;
        }

        System.out.println("client ID:"+game.clientID);

    }

    public void sendMessage(String message){
        out.println(message);
        out.flush();
    }

    public String recieve() {
        try {
            if(in!=null)
            return in.readLine();
        } catch (IOException ex) {
            Logger.getLogger(GSocket.class.getName()).log(Level.SEVERE, null, ex);
            state = SocketState.UNSUCCESSFULL_REQUEST;
        }
        return null;
    }

    public void close(){
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(GSocket.class.getName()).log(Level.SEVERE, null, ex);
            state = SocketState.DISCONNECTED;
        }
        state = SocketState.DISCONNECTED;
    }

    

}
