/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author Hugo
 */
public class SOK_1_CLIENT_CLIENT implements Runnable {
    
    Socket sock;
    Scanner input;
    Scanner send = new Scanner(System.in);
    PrintWriter out;
    
    public SOK_1_CLIENT_CLIENT(Socket Z)
    {
        //pointer
        this.sock = Z;
    }
    
    public void run()
    {
        try
        {
            try
            {
                input = new Scanner(sock.getInputStream());
                out = new PrintWriter(sock.getOutputStream());
                out.flush();
                CheckStream();
            }
            finally
            {
               sock.close();
            }
        }
        catch(Exception Z)
        {
            System.out.print(Z);
        }
    }
    
    public void Disconnect() throws IOException
    {
        out.println(SOK_1_CLIENT.Username + "has disconnected.");
        out.flush();
        sock.close();
        System.exit(0);
    }
    
    public void CheckStream()
    {
        while(true)
        {
            Receive();
        }
    }
    
    public void Receive()
    {
        if(input.hasNext())
        {
            String msg = input.nextLine();
            
            if(msg.contains("#?!"))
            {
                String temp1 = msg.substring(3);
                       temp1 = temp1.replace("[", "");
                       temp1 = temp1.replace("", "]");
                       
                String[] CurrentUsers = temp1.split(", ");
                System.out.println("Connections to: " + CurrentUsers[0]);
            }
            else
            {
                System.out.println("ENTER ORDERS HERE LATER!!");
            }
        }
    }
    
    public void Send(String Z)
    {
        out.println("Testing mofo: " + Z);
        out.flush();
        
    }
    
}
