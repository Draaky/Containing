/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hugo
 */
public class Bufferhandler extends Thread {
    public static List<String> buffer = new ArrayList<>();
    
    public static void main(String[] args) throws InterruptedException
    {
    while(true)
    {
        for(String s:buffer)
        {
            for (int i = 0; i < 20; i++) {
                EchoClient.os.println(s);
                buffer.remove(0);
                //sleep for how long it takes to handle all the containers
                Bufferhandler.sleep(1000);//needs to change
            }
        }
    }
    }
    
}
