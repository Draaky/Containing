package containing;
/**
 *
 * @author Azad
 */

public class TrainCrane extends Crane {
    // private int x=0; // initalize x value for load methode. the y and z are inside load methode because they don't change

    public void detectTrain() {

    }

    public void hasCargo() {

    }
/*
    load maximum 16 containers on the train, the two traincranes places the containers to in turn, so they can work at the same time
    */
    public void load(String contanerID, int aantalContainers) {
        //AGV detection ??
        int Y = 0;
        int Z = 0;
        System.out.println("load methode to divide the amount of continers over the the two crains");
        //each crane may place 8 containers 
        for (int nextRow = 0; nextRow <= 7; nextRow++) {
            // based on the number of containers decide which 8 containers belong to which crane
            for (int X = nextRow; X <= aantalContainers; X += 8) {
                // call a finde crane method and give the x,y,z value as argument 
                callCrane("trainCrId", X, Y, Z);
            }
        }
    }

    /*
     unload containers from the train
     */
    @Override   //is contanerID needed?
    public void unload(String contanerID, int xLength, int yLength, int zLength) {
        //AGV.add(container); ?? 

        //make for each crane 8 rows to work with
        for (int nextRow = 0; nextRow < 7; nextRow++) {
            // detect the depth of the rows and start the count from top to bottom if it is possible
            for (int Z = zLength; Z >= 0; Z--) {
                // detect the width of the rows and unload all contaners of the current row 
                for (int Y = 0; Y <= yLength; Y++) {
                    // based on x-length decide which 8 rows belong to which crane
                    for (int X = nextRow; X <= xLength; X += 8) {
                        // call a finde crane method and give the x,y,z value as argument 
                        callCrane("trainCrId", X, Y, Z);
                    }
                }
            }

        }
    }

    /*
     call the right train crane to handle a load or unload action 
     */
    @Override
    public void callCrane(String craneID, int x, int y, int z) {
        switch (x) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                craneID += "0";
                break;
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                craneID += "1";
                break;
            default:
                System.out.println("Invalid x-axis");
        }
        //test to see if the methode works
       // System.out.println("ID=: " + craneID + "  container x,y,z =: " + x + "," + y + "," + z);
    }

}
