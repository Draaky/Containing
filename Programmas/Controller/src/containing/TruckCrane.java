package containing;
/**
 *
 * @author Azad
 */

public class TruckCrane extends Crane {

    public void detectTruck() {

    }
    /*
     load the containers from the AGV's to trucks.
     */

    public void load(String contanerID, int aantalContainers) {
        //AGV detection ??
        // Truck.add(container); ??
        String craneID = "";
        System.out.println("load methode to divide the amount of continers over the the 20 truckcrains");
        // based on the number of containers decide which container belong to which crane
        for (int nextCrane = 0; nextCrane < aantalContainers; nextCrane++) {

            craneID = "craneID" + nextCrane;
            System.out.println("craneID is: " + craneID);

        }
    }
    /*
     unload the containers from the trucks to the AGV's
     */

    public void unload(String contanerID, int aantalContainers) {
       
        String craneID = "";
         System.out.println("unload methode to divide the amount of continers over the the 20 truckcrains");
        // based on the number of containers decide which container belong to which crane
        for (int nextCrane = 0; nextCrane < aantalContainers; nextCrane++) {
            craneID = "craneID" + nextCrane;
            System.out.println("craneID is: " + craneID);
        }
    }
}
