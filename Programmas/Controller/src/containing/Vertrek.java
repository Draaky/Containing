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
public class Vertrek extends Thread {

    static Boolean month = false;
    static Boolean day = false;
    static Boolean time = false;
    static String x;
    static String y;
    static String z;
    static String id;
    static String vervoersoort;

    public static void main(String[] args) {
        while (true) {
            for (Container c : Storage.storage) {
                if (Storage.storage.get(0).vdatumM == Clock.month) {
                    month = true;
                } else {
                    month = false;
                }
                if (Storage.storage.get(0).vdatumD == Clock.day) {
                    day = true;
                } else {
                    day = false;
                }
                if (Storage.storage.get(0).vVan == Clock.tijd) {
                    time = true;
                } else {
                    time = false;
                }

                if (time == true && day == true && month == true) {
                    x = Float.toString(Storage.storage.get(0).x);
                    y = Float.toString(Storage.storage.get(0).y);
                    z = Float.toString(Storage.storage.get(0).z);
                    id = Storage.storage.get(0).containerID;
                    vervoersoort = Storage.storage.get(0).vsoortvervoer;

                    EchoClient.os.println("Opslagkraan pak " + id + "van: " + x + "," + y + "," + z + "en vervoer naar: " + vervoersoort);

                    Storage.storage.remove(0);
                    time = false;
                    day = false;
                    month = false;
                }
            }
        }

    }
}
