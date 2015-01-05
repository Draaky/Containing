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
public class Aankomstvracht extends Thread{
    static Boolean month = false;
    static Boolean day = false;
    static Boolean time = false;
    static String x;
    static String y;
    static String z;
    static String id;
    static String msg;
    
    public static void main(String[] args)
    {
        while (Containing.VrachtContainers.get(0) != null)
                {
                    if (Containing.VrachtContainers.get(0).adatumM == Clock.month)
                    {
                        month = true;
                    }
                    else month = false;
                    if (Containing.VrachtContainers.get(0).adatumD == Clock.day)
                    {
                        day = true;
                    }
                    else day = false;
                    if (Containing.VrachtContainers.get(0).aVan == Clock.tijd)
                    {
                        time = true;
                    }
                    else time = false;
                    
                    
                    if (time == true && day == true && month == true)
                    {
                        x = Float.toString(Containing.VrachtContainers.get(0).x);
                        y = Float.toString(Containing.VrachtContainers.get(0).y);
                        z = Float.toString(Containing.VrachtContainers.get(0).z);
                        id = Containing.VrachtContainers.get(0).containerID;
                        
                        //EchoClient.os.println("Vrachtkraan pak " + id + "van: " + x + "," + y + "," + z);
                        msg = "Zeekraan pak " + id + "van: " + x + "," + y + "," + z;
                        Bufferhandler.buffer.add(msg);
                        Storage.storage.add(Containing.VrachtContainers.get(0));
                        Containing.VrachtContainers.remove(0);
                        time = false;
                        day = false;
                        month = false;
                    }
                }
        
    }
    
}
