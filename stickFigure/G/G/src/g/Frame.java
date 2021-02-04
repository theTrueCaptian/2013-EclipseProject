/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package g;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author maeda hanafi
 */

public class Frame extends JFrame implements ActionListener{
    Game game;
    GSocket socket;
    Timer timer;
    SocketState knownSocketState;
    STATE knownGameState;
    boolean initialize = true;

    UserPassPanel UPPanel=null;

    JPanel errorPanel;
    JPanel chatPanel;
    JPanel errorConnectionPanel;
    JPanel titlePanel ;

    int frameX = 500, frameY = 500;
    public Frame(Game game, GSocket socket){
        this.game = game;
        this.socket = socket;

        knownSocketState = socket.state;
        knownGameState = game.state;
        
        setResizable(false);
        setSize(frameX, frameY);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);

        timer = new Timer(500, this);
        timer.start();
        setVisible(true);
    }

    
    public void actionPerformed(ActionEvent e) {
        //System.out.println("Update panels");
        updatePanels();
    }

    public void updatePanels(){
        if(socket.state!=knownSocketState){
            setInitialize(true);
            knownSocketState = socket.state;
        }
        if(game.state!=knownGameState){
            setInitialize(true);
            knownGameState = game.state;
        }
        if(socket.state==SocketState.UNSUCCESSFULL_REQUEST){
            if(initialize){
                
                setSize(350,300);
                System.out.println("Error! Server not found. Please try again");
                errorConnectionPanel = new SystemMessagePanel("Error! Server not found. Please try again");
                add(errorConnectionPanel, BorderLayout.NORTH);
                initialize = false;
            }
        }
        if(game.state == STATE.REQUEST_USER_NAME_PASSWORD && socket.state==SocketState.CONNECTED){
            //System.out.println("Request user and password");
            if(initialize){
                System.out.println("Please Enter Username and password");

                titlePanel = new SystemMessagePanel("Welcome. Please Enter Username and Password");
                setSize(350,300);
                UPPanel = new UserPassPanel(game, socket);

                add(titlePanel, BorderLayout.NORTH);
                add(UPPanel, BorderLayout.CENTER);

                initialize = false;
            }
            
        }
        if(game.state == STATE.CHAT_ROOM && socket.state == SocketState.CONNECTED){
            setSize(700, 600);
            
            if(initialize){
                disableAllPanel();
                titlePanel = new SystemMessagePanel("Chat room");
                System.out.println("Welcome to chat room");
                chatPanel = new ChatRoomPanel(this, socket);
                setSize(700, 600);
                add(chatPanel, BorderLayout.CENTER);
                add(titlePanel, BorderLayout.NORTH);
                titlePanel.setVisible(true);
                chatPanel.setVisible(true);
                initialize = false;

                addKeyListener( (KeyListener) chatPanel);
            }
        }
        
            //displayErrorPanel("Can't Connect To Server! Please try again later.");
        
    }

    public void disableAllPanel(){
        UPPanel.setEnabled(false);
        UPPanel.setVisible(false);
        titlePanel.setVisible(false);
    }

    public void displayErrorPanel(String message) {
        errorPanel = new SystemMessagePanel(message);
        add(errorPanel, BorderLayout.NORTH);
    }

    public void setInitialize(boolean set){
        initialize = set;
    }

}
