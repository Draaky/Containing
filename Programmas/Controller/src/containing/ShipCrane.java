package containing;
/**
 *
 * @author Azad
 */
public class ShipCrane extends Crane {

    public void hasCargo() {

    }

    public void detectShip() {

    }
    
/*
    unload containers from the sea ship
    */
    @Override   //is contanerID needed?
    public void unload(String contanerID, int xLength, int yLength, int zLength) {
        //container plaatsen
        //AGV.add(container);

        //make for each crane 3 rows to work with
        for (int nextRow = 0; nextRow < 2; nextRow++) {
            // detect the depth of the rows and start the count from top to bottom
            for (int Z = zLength; Z >= 0; Z--) {
                // detect the width of the rows and unload all contaners of the current row 
                for (int Y = 0; Y <= yLength; Y++) {
                    // based on x-length decide which 3 rows belong to which crane
                    for (int X = nextRow; X <= xLength; X += 3) {
                        // call a finde crane method and give the 3 xyz as its argument 
                        callCrane("shipCrId", X, Y, Z);
                    }
                }
            }

        }
    }
    
    /*
    call the right sea crane to handle a load or unload action 
    */
    @Override
    public void callCrane(String craneID, int x, int y, int z) {
        switch (x) {
            case 0:
            case 1:
            case 2:
                craneID += "0";
                break;
            case 3:
            case 4:
            case 5:
                craneID += "1";
                break;
            case 6:
            case 7:
            case 8:
                craneID += "2";
                break;
            case 9:
            case 10:
            case 11:
                craneID += "3";
                break;
            case 12:
            case 13:
            case 14:
                craneID += "4";
                break;
            case 15:
            case 16:
            case 17:
                craneID += "5";
                break;
            case 18:
            case 19:
            case 20:
                craneID += "6";
                break;
            case 21:
            case 22:
            case 23:
                craneID += "7";
                break;
            case 24:
            case 25:
            case 26:
                craneID += "8";
                break;
            case 27:
            case 28:
            case 29:
                craneID += "9";
                break;
            default:
                System.out.println("Invalid x-axis");
        }
        System.out.println("ID=: " + craneID + "  container x,y,z =: " + x + "," + y + "," + z);
    }
    
    /*
    load devidsa all containers on 10 sea crane so that each crane gets a container to in turn
    */
    @Override
    public void load(String containerID, int numberOfContaners) {

        for (int i = 0; i <= numberOfContaners; i++) {
            {
                String CrID = "";
                CrID += i; // make a new id for the crane to change the crane in turn
                this.craneID = "shipCrId" + CrID; // use the instance variable of Class Crane to make a new shipCrane-id
              //  System.out.println(craneID); // testing the method load for printing the shipCrane-id's

                
                if (i == 9) {
                    numberOfContaners -= 10;
                    i -= 10;
                }
            }
        }

    }

}
