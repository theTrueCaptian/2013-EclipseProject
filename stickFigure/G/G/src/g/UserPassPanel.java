
package g;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 *
 * @author maeda hanafi
 */
enum UserPassState{REQUEST_USER_NAME_PASSWORD, AUTHENTICATING, ERROR_USER_PASS, SUCCESS}
public class UserPassPanel extends JPanel{
    JTextField user;
    JPasswordField pass;
    JLabel userLabel;
    JLabel passLabel;
    JLabel title;
    JButton loginButton;
    Game game;
    GSocket socket;
    UserPassState state  = UserPassState.REQUEST_USER_NAME_PASSWORD;
    String enteredUser = null;
    String enteredPass = null;
    boolean clickedButton = false;
    boolean success = false;
    
    public UserPassPanel(Game game, GSocket socket){
        this.game = game;
        this.socket = socket;

        setBorder(new LineBorder(Color.green, 1));
        setLayout(new FlowLayout());
        setBackground(Color.red);
        setSize(100,100);
        user = new JTextField(20);
        user.setSize(10, 10);
        pass = new JPasswordField(20);
        pass.setSize(1, 20);

        userLabel = new JLabel("Username");
        passLabel = new JLabel("Password");
        
        loginButton = new JButton("Enter");

        add(userLabel);
        add(user);
        add(passLabel);
        add(pass);
        add(loginButton);

        loginButton.addActionListener(new loginListener());
        initialize();
        
    }

    //won work
    @Override
    public void paintComponent(Graphics g){
        if(state==UserPassState.AUTHENTICATING){
            System.out.println("Please wait...");
            g.drawString("Please wait...", 150, 150);
        }
        //g.drawString("Hello", 100, 100);
    }

    class loginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("Login!");
            enteredUser = user.getText();
            enteredPass = pass.getText();
            if(!enteredUser.equals("") || !enteredPass.equals(""))
                authenticate();
            else{
                System.out.println("Please enter something");
            }
        }
    }

    public void authenticate(){
        state = UserPassState.AUTHENTICATING;
        initialize();
        String result;
        result = null;
        socket.sendMessage("userPass "+enteredUser+" "+enteredPass);

        result = socket.recieve();
        System.out.println("result: "+result);

        if(result!=null){
            if(result.equalsIgnoreCase("success") ){
                state = UserPassState.SUCCESS;
            }else{
                state = UserPassState.ERROR_USER_PASS;
                System.out.println("ERORUSERPASS");
            }
        }else{
            state = UserPassState.ERROR_USER_PASS;
            System.out.println("ERORUSERPASS");
        }
        initialize();
    }

    private void initialize(){
        if(state == UserPassState.REQUEST_USER_NAME_PASSWORD || state == UserPassState.ERROR_USER_PASS){
            loginButton.setEnabled(true);
            loginButton.setText("Enter");

        }else if(state == UserPassState.AUTHENTICATING){
            loginButton.setText("Please Wait");
            loginButton.setEnabled(false);            
            System.out.println("please wait");
        }else if(state == UserPassState.SUCCESS){
            loginButton.setEnabled(false);
            loginButton.setText("Success!");
            success = true;
        }
    }

    public UserPassState getState(){
        return state;
    }

}
