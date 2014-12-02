package containing;

import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;
import java.io.*;
import java.net.*;
import java.util.Scanner;
/**
 *
 * @author Hugo
 */
public class SOK_1_SERVER {
    //Public void main to be able to make an instance of server
    public static ArrayList<Socket> ConnectionArray = new ArrayList<Socket>();
    public static ArrayList<String> CurrentUsers = new ArrayList<String>();
 
   // public static void main(String[] args) throws IOException //throws Exception to work around try catch
   //{
    public SOK_1_SERVER()
    {
        try
        {
            final int port = 444;
            ServerSocket server = new ServerSocket(port);
            System.out.println("Waiting for Connections");
            
            while(true)
            {
                Socket sock = server.accept();
                ConnectionArray.add(sock);
                
                System.out.println("Connection established with:" + sock.getLocalAddress().getHostName());
                
                AddUserName(sock);
                
                SOK_1_SERVER_RETURN chat = new SOK_1_SERVER_RETURN(sock);
                Thread Y = new Thread(chat);
                Y.start();
            }
        }
        catch(Exception Y){
            System.out.print(Y);
        }
    }
    
    public static void AddUserName(Socket Y) throws IOException
    {
        Scanner input = new Scanner(Y.getInputStream());
        String username = input.nextLine();
        CurrentUsers.add(username);
        
        for (int i = 1; i <= SOK_1_SERVER.ConnectionArray.size(); i++) {
            Socket tempsock = (Socket) SOK_1_SERVER.ConnectionArray.get(i-1);
            PrintWriter out = new PrintWriter(tempsock.getOutputStream());
            out.println("#?!" + CurrentUsers);
            out.flush();
        }
    }
    
}

        
