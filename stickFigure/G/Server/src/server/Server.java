
package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * maeda hanafi
 */

public class Server {

    ArrayList<ServerThread> thread = new ArrayList<ServerThread>();
    ServerThread printed1 = null, printed2 = null;
    int printedIndex1, printedIndex2;
    boolean displayFlag = false;
    String filename = "C:\\Users\\user\\Desktop\\G\\userPass.txt";
    ArrayList<Node> userPassArray = new ArrayList<Node>();

    public Server(){
        scanUserPassFile();
        SSocket coordinator = new SSocket(thread, userPassArray );
        coordinator.start();
    }

    public static void main(String[] args)  {
        Server server = new Server();
    }

  
    private void scanUserPassFile(){
        FileReader fileRd = null;
        try {
            do{

                fileRd = new FileReader(filename);

            }while(fileRd == null);
            BufferedReader bufferRd = new BufferedReader(fileRd);
            String record = null;

            while ((record = bufferRd.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(record, " ");
                String userRd = st.nextToken();
                String passRd = st.nextToken();
                userPassArray.add(new Node(userRd, passRd));
                System.out.println("record: "+record);
                
            }
            fileRd.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                if(fileRd!=null)
                    fileRd.close();
            } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        
    }

}
