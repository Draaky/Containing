/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

/**
 *
 * @author Hugo
 */
public class Aankomstbinnen extends Thread {
    static Boolean month = false;
    static Boolean day = false;
    static Boolean time = false;
    static String x;
    static String y;
    static String z;
    static String id;
    public static void main(String[] args)
    {
        while (Containing.BinnenContainers.get(0) != null)
                {
                    if (Containing.BinnenContainers.get(0).adatumM == Clock.month)
                    {
                        month = true;
                    }
                    else month = false;
                    if (Containing.BinnenContainers.get(0).adatumD == Clock.day)
                    {
                        day = true;
                    }
                    else day = false;
                    if (Containing.BinnenContainers.get(0).aVan == Clock.tijd)
                    {
                        time = true;
                    }
                    else time = false;
                    
                    
                    if (time == true && day == true && month == true)
                    {
                        x = Float.toString(Containing.BinnenContainers.get(0).x);
                        y = Float.toString(Containing.BinnenContainers.get(0).y);
                        z = Float.toString(Containing.BinnenContainers.get(0).z);
                        id = Containing.BinnenContainers.get(0).containerID;
                        
                        EchoClient.os.println("Binnenkraan pak " + id + "van: " + x + "," + y + "," + z);
                        
                        Storage.storage.add(Containing.BinnenContainers.get(0));
                        Containing.BinnenContainers.remove(0);
                        time = false;
                        day = false;
                        month = false;
                    }
                }
        
    }
}
