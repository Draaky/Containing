package containing;


import java.io.*;
import java.net.*;
/**
 *
 * @author Hugo
 */
public class SOK_1_SERVER {
    //Public void main to be able to make an instance of server
    public static void main(String[] args) throws Exception //throws Exception to work around try catch
    {
        SOK_1_SERVER SERVER = new SOK_1_SERVER(); //making an instance of server
        SERVER.run();                             //run the server
    }
    
    public void run() throws Exception
    {
        ServerSocket SRVSOCK = new ServerSocket(444); //making a new instance of serversocket(444) is port number
        Socket SOCK = SRVSOCK.accept();               //making a new socket with accept method
        InputStreamReader IR = new InputStreamReader(SOCK.getInputStream()); //making a new instance of the input reader which get his data from the socket
        BufferedReader BR = new BufferedReader(IR); //making a reader that reads the input
        
        String MESSAGE = BR.readLine(); //makes a string from 
        System.out.println(MESSAGE);
        
        if(MESSAGE != null)
        {
            PrintStream PS = new PrintStream(SOCK.getOutputStream());
            PS.println("MESSAGE received");
        }
    }
}
