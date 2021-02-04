/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;


import java.io.IOException;
import java.net.*;
import java.util.*;

/**
 *
 * @author SMART User
 */
public class SSocket extends Thread{
    ArrayList<ServerThread> threads;
    int clients=0;
    int portNumber = 4444;
    ArrayList<Node> userPassArray;

    public SSocket(ArrayList<ServerThread> inthreads, ArrayList<Node> userPassArray){
        this.userPassArray = userPassArray;
        threads = inthreads;
    }

    @Override
    public void run(){
        waitForclients();
    }

    public void waitForclients(){
        ServerSocket serverSocket = null;
        boolean listening = true;

        try {
            serverSocket = new ServerSocket(portNumber);
            while (listening){
                clients ++;
                ServerThread temp = new ServerThread(this, serverSocket.accept(),  clients);
                System.out.println("Client number "+clients);
                threads.add(temp);
                temp.start();
            }

        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }
    }



}
