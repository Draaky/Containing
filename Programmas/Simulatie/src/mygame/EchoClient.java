package mygame;
 
import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoClient implements Runnable {

  // The client socket
  private static Socket clientSocket = null;
  // The output stream
  static PrintStream os = null;
  // The input stream
  private static DataInputStream is = null;

  private static BufferedReader inputLine = null;
  private static boolean closed = false;
  public static String test;
  
  private static String msg = "Controller";
  //public static int i = 0;
  public static String getmsg()
  {
  return msg;
  }
  static void setmsg(String x)
  { 
      EchoClient.msg = x; 
  }
  
  
  public static void main(String[] args) {
//public EchoClient() {
    // The default port.
      
    int portNumber = 2222;
    // The default host.
    String host = "localhost";

    if (true) {
      System.out
          .println("Usage: java MultiThreadChatClient <host> <portNumber>\n"
              + "Now using host=" + host + ", portNumber=" + portNumber);
    } else {
      //host = args[0];
      //portNumber = Integer.valueOf(args[1]).intValue();
    }

    /*
     * Open a socket on a given host and port. Open input and output streams.
     */
    try {
      clientSocket = new Socket(host, portNumber);
      inputLine = new BufferedReader(new InputStreamReader(System.in));
      os = new PrintStream(clientSocket.getOutputStream());
      is = new DataInputStream(clientSocket.getInputStream());
    } catch (UnknownHostException e) {
      System.err.println("Don't know about host " + host);
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to the host "
          + host);
    }

    /*
     * If everything has been initialized then we want to write some data to the
     * socket we have opened a connection to on the port portNumber.
     */
    if (clientSocket != null && os != null && is != null) {
   //   try {

        /* Create a thread to read from the server. */
        new Thread(new EchoClient()).start();
        //Thread Containing = new Containing();
        ///Containing.start();
        while (!closed) {
            test = EchoClient.getmsg();
            //System.out.println(test);
            //i ++;
            if (test != "")
            {
                os.println(test.trim());
                EchoClient.setmsg("");
            }
            
        
          //if (is.readLine() == "Connection withController established")
         // {
         //    for (int i = 0; i < 10; i++) {
         //        os.println("Testing nr." + i);
         //  }
              
          //}
          //}
        /*
         * Close the output stream, close the input stream, close the socket.
         */
       // os.close();
       // is.close();
        //clientSocket.close();
     // } //catch (IOException e) {
        //System.err.println("IOException:  " + e);
      }
    }
         
  }
   //public void send(String message)
       // {
      //      os.println(message.trim());
       //     System.out.println("komt in de send!");
           
       // }
  /*
   * Create a thread to read from the server. (non-Javadoc)
   * 
   * @see java.lang.Runnable#run()
   */
  public void run() {
    /*
     * Keep on reading from the socket till we receive "Bye" from the
     * server. Once we received that then we want to break.
     */
    String responseLine;
    try {
      while (true) {
        //System.out.println("test");
        responseLine = is.readLine();
        System.out.println(responseLine);
        if (responseLine.indexOf("*** Bye") != -1)
        {
          System.exit(1);
          break;
        }
      }
      closed = true;
    } catch (IOException e) {
     System.err.println("IOException:  " + e);
    }
  }
}