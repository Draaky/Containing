/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;


 
import java.net.*;
import java.io.*;
 
public class EchoServer extends Thread {
    private Thread t;
    private final String threadName;
    
  EchoServer( String name){
       threadName = name;
       System.out.println("Creating " +  threadName );
   }
    
    
    public void run(String[] args)
    {
        
            if (args.length != 1) {
                System.err.println("Usage: java EchoServer <port number>");
                System.exit(1);
            }
         
        int portNumber = Integer.parseInt(args[0]);
         
            try (
                ServerSocket serverSocket =
                    new ServerSocket(Integer.parseInt(args[0]));
                Socket clientSocket = serverSocket.accept();     
                PrintWriter out =
                    new PrintWriter(clientSocket.getOutputStream(), true);                   
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
                ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
    
    @Override
       public void start ()
   {
      System.out.println("Starting " + threadName);
      if (t == null)
      {
         t = new Thread (this, threadName);
         t.start ();
      }
   }
  
}
