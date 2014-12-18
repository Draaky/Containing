package containing;
/**
 *
 * @author Azad
 */

public class SeaShipCrane extends Crane {

    public void Ship() {

    }

    public void hasCargo() {

    }

    public void detectShip() {

    }

    @Override
    public void unload(String conId, int xLength, int yLength, int zLength) { 
        //container plaatsen
        //AGV.add(container);

        //diside the start-row of each crane. after unloading all container of that row, jump to the next row
        for (int nextRow = 0; nextRow < 3; nextRow++) {
            // for all y-axis from top to bottom 
            for (int Y = yLength; Y >= 0; Y--) {
                // detect the depth of the rows
                for (int Z = 0; Z <= zLength; Z++) {
                    // based on x length decide which 3 rows belong to which crane
                    for (int X = nextRow; X <= xLength; X += 3) {
                        // call a finde crane method and give the 3 xyz as its argument 
                        callCrane("shipCrId", X, Y, Z);
//                        System.out.print( X + "," + Y +  "," + Z);
                    }
                }
            }

        }

    }

    @Override
    public void callCrane(String craneID, int x, int y, int z) {
        switch (x) {
            case 0:
            case 1:
            case 2:
                craneID += "1";
                break;
            case 3:
            case 4:
            case 5:
                craneID += "2";
                break;
            case 6:
            case 7:
            case 8:
                craneID += "3";
                break;
            case 9:
            case 10:
            case 11:
                craneID += "4";
                break;
            case 12:
            case 13:
            case 14:
                craneID += "5";
                break;
            case 15:
            case 16:
            case 17:
                craneID += "6";
                break;
            case 18:
            case 19:
            case 20:
                craneID += "7";
                break;
            case 21:
            case 22:
            case 23:
                craneID += "8";
                break;
            case 24:
            case 25:
            case 26:
                craneID += "9";
                break;
            case 27:
            case 28:
            case 29:
                craneID += "10";
                break;
            default:
                System.out.println("Invalid x-axis");
        }
        System.out.println("ID=: " + craneID + "  container x,y,z =: " + x + "," + y + "," + z);
    }

    @Override
    public void load(String containerID) {

        int countCont = 37; //example for the total containers which has to be loaded on the ship
        //through all containers to load them
        for (int i = 1; i <= countCont; i++) {
            {
                String CrID = "";
                CrID += i; // make a new id for the crane to change the crane in turn
                this.craneID = "shipCrId" + CrID; // use the instance variable of Class Crane to make a new shipCrane-id
                System.out.println(craneID); // testing the method load for printing the shipCrane-id's
		
		
                if (i == 10) {
                    countCont -= 10;
                    i -= 10;
                }
            }
        }

    }

  // public void loadShip()
}
