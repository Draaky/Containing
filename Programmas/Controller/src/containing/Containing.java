/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

import static java.lang.Thread.sleep;
import java.util.Collections;
import java.util.List;
import org.javatuples.Triplet;
//import static java.util.Comparator.comparing;

/**
 *
 * @author Arjen
 */



public class Containing extends Thread {


   
    /**
     * @param args the command line arguments
     */
    public static void main(final String[] args) throws Exception {
        // TODO code application logic here
       
        //EchoServer server = new EchoServer();
        //sleep(1000);
       
       Thread EchoClientController = new Thread(){
           public void run(){
               EchoClient.main(args);
           }
       };
       EchoClientController.start();
    


        XML_Parser X = new XML_Parser();

        int test = X.ZeeContainers.size();
        int test2 = X.BinnenContainers.size();
        int test3 = X.VrachtContainers.size();
        int test4 = X.TreinContainers.size();
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

        Collections.sort(X.VrachtContainers, new ContComparator.CompaVan(false));
        Collections.sort(X.VrachtContainers, new ContComparator.CompaTot(false));
        Collections.sort(X.VrachtContainers, new ContComparator.CompadatumD());
        Collections.sort(X.VrachtContainers, new ContComparator.CompadatumM());
        Collections.sort(X.VrachtContainers, new ContComparator.CompadatumJ());

        Collections.sort(X.ZeeContainers, new ContComparator.CompaVan(false));
        Collections.sort(X.ZeeContainers, new ContComparator.CompaTot(false));
        Collections.sort(X.ZeeContainers, new ContComparator.CompadatumD());
        Collections.sort(X.ZeeContainers, new ContComparator.CompadatumM());
        Collections.sort(X.ZeeContainers, new ContComparator.CompadatumJ());

        Collections.sort(X.TreinContainers, new ContComparator.CompaVan(false));
        Collections.sort(X.TreinContainers, new ContComparator.CompaTot(false));
        Collections.sort(X.TreinContainers, new ContComparator.CompadatumD());
        Collections.sort(X.TreinContainers, new ContComparator.CompadatumM());
        Collections.sort(X.TreinContainers, new ContComparator.CompadatumJ());

        Collections.sort(X.BinnenContainers, new ContComparator.CompaVan(false));
        Collections.sort(X.BinnenContainers, new ContComparator.CompaTot(false));
        Collections.sort(X.BinnenContainers, new ContComparator.CompadatumD());
        Collections.sort(X.BinnenContainers, new ContComparator.CompadatumM());
        Collections.sort(X.BinnenContainers, new ContComparator.CompadatumJ());

        System.out.println("Biggest X value of Train");

        for (Container C : X.ZeeContainers) {
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
        sleep (2000);
        EchoClient.os.println(" tcAddTruck 1 2");
        EchoClient.os.println(" tcAddAGV 1 2");
        EchoClient.os.println(" tc 1 2");
        
        
    //EchoServer ES = new EchoServer("Localhost");
 
        
//EchoServer ES = new EchoServer();
   // Z.test();
        //SOK_1_CLIENT SC = new SOK_1_CLIENT();
        //  for (int i = 0; i < 10000; i++) {
        //    Z.testsend(i);
        // }
            //for (int i = 0; i < 10000; i++) {
        // SS.run("Testing nr.");
        //SC.run();  
    }


    public void test() {
        //XML_Parser l = new XML_Parser();

    }

    public static void send(String msg) {
        EchoClient.os.println(msg);
    }

    
    public void freerkTesting()
    {
        Opslag opslag = new Opslag();
    XML_Parser X2 = new XML_Parser();
    StorageRowNumber trw = new StorageRowNumber();
    
    for(Container c : X2.ZeeContainers)
    {
            List<Container> added = opslag.container;
            int i = added.size();
            Triplet newPlek = new Triplet(null, null, null);
            System.out.println(c.vsoortvervoer);
            
            for (int j = 0; j < 20; j++) {
                if(newPlek.getValue0() == null)
                {
                    newPlek = opslag.getStoragePosition(c, trw.returnRowNumber(c.vsoortvervoer)+j, added);
                }
            }

              if(newPlek.getValue0() == null)
              {
                break;
              }
              
            //System.out.println(i);
            System.out.println("newPlek: "+newPlek);
    }
    
    System.out.println(opslag.count);
    System.out.println(opslag.container.size());
    
    
        System.out.println("---------------------------------------------------");

        for (int i = 0; i < 80; i++) {
            List<Container> tempcheck = opslag.getRowList(i, opslag.container);
            System.out.println(i+": "+tempcheck.size());
        }
    }
    
    
}
