
package g;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author user
 */
public class SystemMessagePanel extends JPanel{
    String message;
    JLabel messageLabel;
    public SystemMessagePanel(String message){
        this.message = message;

        setBorder(new LineBorder(Color.red, 1));

        messageLabel = new JLabel(message);
        add(messageLabel);


    }


}
