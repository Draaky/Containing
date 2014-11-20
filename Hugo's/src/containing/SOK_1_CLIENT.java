package containing;

import java.io.*;
import java.net.*;

/**
 *
 * @author Hugo
 */
public class SOK_1_CLIENT {
    
    //void main to run it
    public static void main(String[] args) throws Exception
    {
        //Making a new client socket instance
        SOK_1_CLIENT CLIENT = new SOK_1_CLIENT();
        CLIENT.run();
    }
    
    public void run() throws Exception
    {
        //making a new socket with ip en port number which will 
        Socket SOCK = new Socket("localhost",444);
        // making a new printstream that prints to the socket
        PrintStream PS = new PrintStream(SOCK.getOutputStream());
        //printing in socket
        PS.println("Hello to SERVER from CLIENT.");
        //reading socket
        InputStreamReader IR = new InputStreamReader(SOCK.getInputStream());
        BufferedReader BR = new BufferedReader(IR);
        //printing in console
        String MESSAGE = BR.readLine();
        System.out.println(MESSAGE);
    }
}
