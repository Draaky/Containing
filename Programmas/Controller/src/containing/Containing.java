/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author HUGO!!
 */
public class Containing extends Thread {

public static List<Container> ZeeContainers = new ArrayList<Container>();
public static List<Container> VrachtContainers = new ArrayList<Container>();
public static List<Container> TreinContainers = new ArrayList<Container>();
public static List<Container> BinnenContainers = new ArrayList<Container>();
    /**
     * @param args the command line arguments
     */
    public static void main(final String[] args) throws Exception {
        
        //Creating a thread for the EchoServer
        Thread EchoServertest = new Thread() {
            public void run() {
                EchoServer.main(args);
            }
        };
        EchoServertest.start();
        
        //Creating a thread for the EchoClient
        Thread EchoClientController = new Thread() {
            public void run() {
                EchoClient.main(args);
            }
        };
        EchoClientController.start();

        //Creating a thread for the Clock
        Thread Clockuh = new Thread() {
            public void run() {
             Clock.main(args);
            }
        };
        Clockuh.start();
        
        //Running the XML Parser
        XML_Parser X = new XML_Parser();
        BinnenContainers = X.BinnenContainers;
        ZeeContainers = X.ZeeContainers;
        VrachtContainers = X.VrachtContainers;
        TreinContainers = X.TreinContainers;
        

        
        
        int test = ZeeContainers.size();
        int test2 = BinnenContainers.size();
        int test3 = VrachtContainers.size();
        int test4 = TreinContainers.size();
        int d;
        int m;
        float x = 0;
        float y = 0;
        float z = 0;
        float tempz;
        float tempy;
        float tempx;

        int totaal = test + test2 + test3 + test4;
        System.out.println("Zee container: " + test);
        System.out.println("Binnen container: " + test2);
        System.out.println("Vracht container: " + test3);
        System.out.println("Trein container: " + test4);
        System.out.println("---------------------------");
        System.out.println("Totaal: " + totaal);
        System.out.println("Errors: " + X.error);
        
        //Sorting the respective container lists on arrival time and date
        Collections.sort(VrachtContainers, new ContComparator.CompaVan(false));
        Collections.sort(VrachtContainers, new ContComparator.CompaTot(false));
        Collections.sort(VrachtContainers, new ContComparator.CompadatumD());
        Collections.sort(VrachtContainers, new ContComparator.CompadatumM());
        Collections.sort(VrachtContainers, new ContComparator.CompadatumJ());

        Collections.sort(ZeeContainers, new ContComparator.CompaVan(false));
        Collections.sort(ZeeContainers, new ContComparator.CompaTot(false));
        Collections.sort(ZeeContainers, new ContComparator.CompadatumD());
        Collections.sort(ZeeContainers, new ContComparator.CompadatumM());
        Collections.sort(ZeeContainers, new ContComparator.CompadatumJ());

        Collections.sort(TreinContainers, new ContComparator.CompaVan(false));
        Collections.sort(TreinContainers, new ContComparator.CompaTot(false));
        Collections.sort(TreinContainers, new ContComparator.CompadatumD());
        Collections.sort(TreinContainers, new ContComparator.CompadatumM());
        Collections.sort(TreinContainers, new ContComparator.CompadatumJ());

        Collections.sort(BinnenContainers, new ContComparator.CompaVan(false));
        Collections.sort(BinnenContainers, new ContComparator.CompaTot(false));
        Collections.sort(BinnenContainers, new ContComparator.CompadatumD());
        Collections.sort(BinnenContainers, new ContComparator.CompadatumM());
        Collections.sort(BinnenContainers, new ContComparator.CompadatumJ());
        Thread AankomstZee = new Thread() {
            public void run() {
             Aankomstzee.main(args);
            }
        };
        AankomstZee.start();
        
        Thread AankomstBinnen = new Thread() {
            public void run() {
             Clock.main(args);
            }
        };
        //AankomstBinnen.start();
        
        Thread AankomstVracht = new Thread() {
            public void run() {
             Clock.main(args);
            }
        };
        //AankomstVracht.start();
        
        Thread AankomstTrein = new Thread() {
            public void run() {
             Clock.main(args);
            }
        };
        //AankomstTrein.start();
        System.out.println("Biggest X value of Train");

        for (Container C : ZeeContainers) {
            tempx = C.x;
            if (x < tempx) {
                x = tempx;
            }

            tempy = C.y;
            if (y < tempy) {
                y = tempy;
            }

            tempz = C.z;
            if (z < tempz) {
                z = tempz;
            }

            // System.out.println(" " + d + " / " + m + " / " + j + " Van:  " + C.aVan + "   Tot: " + C.aTot);
            //EchoServer T1 = new EchoServer( "Testserver");
            //T1.start();
            //EchoClient t = new EchoClient();      
        }
        System.out.println(x);
        System.out.println(y);
        System.out.println(z);
       
        
        //EchoClient.os.println("doe iets hier");
        
        //stress test method
       //send("stress test nr. ");


    }

    public void test() {
        //XML_Parser l = new XML_Parser();

    }
    //send method to send information via sockets to the other connected programms
    public static void send(String msg) {
        for (int i = 0; i < 10000; i++) {
            EchoClient.os.println(msg + " " + i);
        }

    }

}
