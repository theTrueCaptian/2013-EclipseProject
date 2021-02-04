package g;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

/**
 *
 * @author SMART User
 */
enum ChatState{CHAT, NORMAL_MODE}
public class ChatRoomPanel extends JPanel implements ActionListener, KeyListener {
     ChatState state = ChatState.NORMAL_MODE;
     Timer timer;
     Frame frame;
     GSocket socket;
     JTextField chatField;
     JButton chatEnter;
     JPanel chatPanel;
     boolean displayUserChat = false;
     int x = 50, y = 50;
     
     public ChatRoomPanel(Frame frame, GSocket socket){
        this.frame = frame;
        this.socket = socket;
        
        System.out.println("Chat Room Panel");
        
        setLayout(new BorderLayout(5, 10));
        setBackground(Color.red);
        setBorder(new LineBorder(Color.BLUE, 1));

        chatField = new JTextField("Press enter to chat", 50);
        chatField.addKeyListener(this);

        /*chatEnter = new JButton("Send");
        chatEnter.addActionListener(new ChatListener());
        chatEnter.addKeyListener(this);*/
        
        chatPanel = new JPanel();

        add(chatPanel, BorderLayout.SOUTH);
        chatPanel.add(chatField);
        //chatPanel.add(chatEnter);

        chatPanel.addKeyListener(this);
        chatPanel.setFocusable(true);

        //register keybord events
        System.out.println("Registering events");
        addKeyListener(this);
        setFocusable(true);
        //this.setFocusTraversalKeysEnabled(true);
        System.out.println("isFocusable:"+isFocusable());
        //this wont take effect until i resize

        timer = new Timer(1000,this);
        timer.start();

        //while(true){
        //}
        
     }

     class ChatListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            displayUserChat = true;
        }
     }

     
     public void keyTyped(KeyEvent e) {
        System.out.println("Typed somethin");
        processInput(e.getKeyChar());
     }

     public void keyPressed(KeyEvent e) { }

     public void keyReleased(KeyEvent e) { }

      

      public void processInput(char input){
        System.out.println("DETECTED!");

        if(input == KeyEvent.VK_ENTER){
            if(state == ChatState.NORMAL_MODE){
                state = ChatState.CHAT;
                chatField.setFocusable(true);
                chatField.requestFocus(true);
                chatField.setEditable(true);
                chatField.setEnabled(true);

            }else if(state == ChatState.CHAT){
                state = ChatState.NORMAL_MODE;
                displayUserChat = true;
                chatField.setFocusable(false);
            }
        }
      }
      
      public void actionPerformed(ActionEvent e) {
        //System.out.println("Maeda");
        
        repaint();
      }

      
     

      @Override
      public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.drawString("Welcome to chat room", 300, 300);

        g.drawString("Enter chat here", 100, 100);
        if(displayUserChat){
            g.drawString(chatField.getText(), x, y);
            chatField.setText("");
            displayUserChat = false;
       }
            
     }

 }
