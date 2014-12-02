package containing;

import java.io.*;
import java.net.*;

/**
 *
 * @author Hugo
 */
public class SOK_1_CLIENT {
    
    private static SOK_1_CLIENT_CLIENT ChatClient;
    public static String Username = "Simulatie/Tablet";
    
    //void main to run it
    public static void main(String[] args) throws Exception
    //{
    //public SOK_1_CLIENT()
    {
    Connect();
    }
    
    public static void Connect()
    {
        try
        {
            final int port = 444;
            final String host = "localhost";
            Socket sock = new Socket(host,port);
            System.out.println("Connected to: " + host);
            
            ChatClient = new SOK_1_CLIENT_CLIENT(sock);
             System.out.println("OS: " + sock.getOutputStream());
            PrintWriter out = new PrintWriter(sock.getOutputStream());
            out.println(Username);
            out.flush();
            
            Thread Z = new Thread(ChatClient);
            Z.start();
            //ChatClient.Send("testing");
            //for (int i = 0; i < 10000; i++) {
            //    Send("nr." + i);
           // }
           
            
        }
        catch(Exception Z)
        {
            System.out.print(Z);
            System.exit(0);
        }
    }
    
    public static void Send(String msg)
    {
        if(!msg.equals(""))
        {
            ChatClient.Send(msg);
        }
    }
    
}
