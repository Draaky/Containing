package containing;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Azad
 */

public class Crane {

    public String sortCrane;
    public Integer craneId;
    Integer speed;
    String craneID;
    public long startTijd;
    public long eindTijd;

    List<Container> containers = new ArrayList<Container>();// a crane hase one or more containers to handle it
    List<Crane> cranes = new ArrayList<Crane>();

    
    public String getSortCrane() {
        return sortCrane;
    }

    public void setSortCrane(String sortCrane) {
        this.sortCrane = sortCrane;
        // sortCrane = <soort_vervoer> xxxx </soort_vervoer> from the containerList or database 
    }

    public Integer getID() {
        return craneId;
    }

    public void setID(Integer x) {
//    if(x % 10 != 0){   
//    }
    }

    public void callAGV() {

        //kijkt of een AGV vrij is
        //roep AGV
    }

    public void unload(String conId, int xLength, int yLength, int zLength) {
//        //container plaatsen
//        //AGV.add(container);
    }
    public void load(String id, int numberOfContaners){
        //place the container
    }

    public void detectAGV() {
        //
        // boolean AGVReady = false;
        //
        // if true, dan load/unload-actie
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        //the speed will depands on if a crane hase a container or not 
        //if crane = hase container , speed = xx, else speed is xx.
        this.speed = speed;
    }

    public void callCrane(String craneID ,int x, int y, int z) {
    }
    
    public static void main(String[] args) throws InterruptedException 
    {
//testing the crane classes(monkeytest)
      // ShipCrane C = new ShipCrane();
      //  TrainCrane t = new TrainCrane();
      //  TruckCrane tr = new TruckCrane();
      //  C.unload("id",29, 3, 4); // give the largest xyz on the ship to unload from 
      //  C.load("id", 37);
      //  C.unload(null, 29, 10, 2);
      //  C.startTijd();
      //  t.unload("id", 15, 0, 0);
      //  t.load("id", 15);
      //  tr.load(null, 18);
      //  tr.unload(null, 20);
    }
    
}


