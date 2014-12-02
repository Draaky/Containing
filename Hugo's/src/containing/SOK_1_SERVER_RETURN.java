package containing;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Hugo
 */
public class SOK_1_SERVER_RETURN implements Runnable {
    
    Socket sock;
    private Scanner input;
    private PrintWriter out;
    String msg = "";
    
    public SOK_1_SERVER_RETURN(Socket Y)
    {
        //pointer
        this.sock = Y;
    }
    
    public void CheckConnection() throws IOException
    {
        if(!sock.isConnected())
        {
            for (int i = 1; i <= SOK_1_SERVER.ConnectionArray.size(); i++) {
                
                if(SOK_1_SERVER.ConnectionArray.get(i) == sock)
                {
                    SOK_1_SERVER.ConnectionArray.remove(i);
                }
            }
            
            for (int i = 1; i <= SOK_1_SERVER.ConnectionArray.size(); i++) {
                
                Socket tempsock = (Socket) SOK_1_SERVER.ConnectionArray.get(i-1);
                PrintWriter tempout = new PrintWriter(tempsock.getOutputStream());
                tempout.println(tempsock.getLocalAddress().getHostName() + " disconnected!");
                tempout.flush();
                
                System.out.println(tempsock.getLocalAddress().getHostAddress() + " disconnected");
            }
        }
    }
    
    public void run()
    {
        try
        {
            try
            {
                input = new Scanner(sock.getInputStream());
                out = new PrintWriter(sock.getOutputStream());
                
                while(true)
                {
                    CheckConnection();
                    
                    if(!input.hasNext())
                    {
                        return;
                    }
                    
                    msg = input.nextLine();
                    
                    System.out.println("Client said: " + msg);
                    
                    for (int i = 1; i <= SOK_1_SERVER.ConnectionArray.size(); i++) {
                        
                        Socket tempsock = (Socket) SOK_1_SERVER.ConnectionArray.get(i-1);
                        PrintWriter tempout = new PrintWriter(tempsock.getOutputStream());
                        tempout.println(msg);
                        tempout.flush();
                        System.out.println("Sent to:" + tempsock.getLocalAddress().getHostName());
                    }
                }
            }
            finally
            {
                sock.close();
            }
        }
        catch(Exception Y)
        {
            System.out.print(Y);
        }
    }
    
}
