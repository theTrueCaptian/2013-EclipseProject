package server;

/**
 *
 * @author SMART User
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.*;
import java.util.StringTokenizer;
import javax.swing.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
enum STATE{UNSUCCESSFUL_REQUEST, CONNECTED, DISCONNECTED}
public class ServerThread extends Thread {

    int msgNumber = 0;
    PrintWriter out;
    BufferedReader in;
    int clientID;
    int x, y, z;
    long time;
    boolean update = false;
    boolean sleep = false;
    String messageHeader;
    Socket socket = null;
    SSocket coordinator;
    STATE state = STATE.DISCONNECTED;
    Timer socketChecker;
    String clientUser = new String(), clientPass = new String();

    public ServerThread(SSocket coordinator, Socket socket,  int clientID) {
	super("ServerThread");
	this.coordinator = coordinator;
        this.clientID = clientID;
        this.socket =socket;

        socketChecker = new Timer(30000, new SocketChecker());
        socketChecker.start();
        System.out.println("handling client "+clientID);
    }

    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            out.flush();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            state = STATE.CONNECTED;
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            state = STATE.UNSUCCESSFUL_REQUEST;
        }
        if(state==STATE.UNSUCCESSFUL_REQUEST){
            killProcess();
        }
        sendClientID();
        authenticateUserPass();
        sendUsersAvailable();
        recieveFromClient();

    }

    private void sendClientID() {
        System.out.println("clientID to send to client:"+clientID);

        String stringID = ""+clientID;
        System.out.println(stringID);
        out.println(stringID);
    }

    private void authenticateUserPass(){
        try{
            String result;
            do{
                result = null;
                
                String message;
                System.out.println("authenticating client");
                message = in.readLine();
                if(message.equalsIgnoreCase("exit")){
                    killProcess();
                }
                StringTokenizer st = new StringTokenizer(message, " ");
                 if(st.nextToken().equals("userPass")){
                    String user = st.nextToken();
                    String pass = st.nextToken();
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    result = authenticate(user,pass);

                    out.println(result);
                    System.out.println("result "+result);
                }
            }while( result.equalsIgnoreCase("error") );

        }catch(IOException ioException){
            state = STATE.UNSUCCESSFUL_REQUEST;
        }
    }

    synchronized private String authenticate(String user, String pass){
        boolean success = false;
        boolean isAlreadyOnline = false;
        //check if the user is online already
        for(int i=0; i<coordinator.threads.size(); i++){
            System.out.println("userOnline: "+coordinator.threads.get(i).clientUser+", user: "+user);
            if(coordinator.threads.get(i).clientUser.equals(user)){
                isAlreadyOnline = true;
            }
        }
        if(!isAlreadyOnline)
            //if not then check if the user and pass is correct
            for(int i=0; i<coordinator.userPassArray.size(); i++){

                System.out.println("userRd: " + coordinator.userPassArray.get(i).user + ", passRd: " + coordinator.userPassArray.get(i).user + ", user: " + user + ", pass: " + pass);

                if (coordinator.userPassArray.get(i).user.equals(user) && coordinator.userPassArray.get(i).pass.equals(pass)) {
                    success = true;
                    break;
                }
            }
       
        if(success){
            clientUser = user;
            clientPass = pass;
            return "success";
        }else
            return "error";

    }

    
    private void sendUsersAvailable(){
        String string2Send = new String();
        string2Send = "online ";
        for(int i = 0; i<coordinator.threads.size(); i++){
            string2Send = string2Send+ coordinator.threads.get(i).clientUser + "|";
            System.out.println("string2Send: "+string2Send);
        }
        System.out.println("final string2Send: "+string2Send);
        out.println(string2Send);
    }

    private void recieveFromClient(){
    	try{
            String message;
            System.out.println("reading message from client");
            while((message=in.readLine())!=null){

                process(message);
                message = "";

            }

        }catch(IOException ioException){
            ioException.printStackTrace();
            state = STATE.UNSUCCESSFUL_REQUEST;
        }

     }

     private  void process(String string){
        StringTokenizer st = new StringTokenizer(string, " ");
        messageHeader = st.nextToken();
        if(messageHeader.equals("update")){
            int inClientID = Integer.parseInt(st.nextToken());
            if(clientID==inClientID){
                time = Long.parseLong(st.nextToken());

                x = Integer.parseInt(st.nextToken());
                y = Integer.parseInt(st.nextToken());
                z = Integer.parseInt(st.nextToken());
                update = true;
                System.out.println("client #"+clientID+": "+x+" "+y+" "+z+" "+time);
            }
        }else if(messageHeader.equals("exit")){
            killProcess();
        }

      }

     private void killProcess(){
         System.out.println("Kill thread!!!!!");
        out.close();
        try {
            in.close();
            socket.close();
            state = STATE.DISCONNECTED;
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            state = STATE.UNSUCCESSFUL_REQUEST;
        }
        coordinator.threads.remove(this);
        stop();

     }
     class SocketChecker implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if(state==STATE.UNSUCCESSFUL_REQUEST){
                System.out.println("UNSUCESSFUL REQUEST");
                killProcess();
            }
            if(state==STATE.CONNECTED){
                if(out==null || in==null || socket==null){
                    System.out.println("something is null while CONNECTED");
                    killProcess();
                }
            }
        }

     }

}
