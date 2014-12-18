package containing;

import java.util.ArrayList;
import java.util.List;
import org.javatuples.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * @author Freerk
 */

public class Opslag {
    int count = 0;
    float rowDepth = 19.2f; //Standardised depth of storageRow
    float containerWidth = 2.4f;
    float containerHeight = 2.9f;
    float containerLength = 13.7f;
    float containerStorage = 14.4f;
    float storageWidth = 589.1f;
    float storageOriginX = 0;
    float storageOriginZ = 0;
    Container[][] yCoordinates;
    ArrayList<Container> container = new ArrayList<>();

    public Triplet<Float, Float, Float> getStoragePosition(Container c, int rowNumber, List<Container> containers)
   {
        List<Container> isUsed = container;
        Container[][] topLayer = getTopLayer(isUsed, rowNumber);
        Triplet<Float, Float, Float> vector = getFreePosition(topLayer, rowNumber);
            if(containers != null && vector.getValue0() != null)
            {
                for(Container con : isUsed)
                {
                    int zAxis = (int)((con.z-(rowNumber*rowDepth+containerWidth))/containerWidth);
                    int xAxis = (int) (con.x / containerLength);

                    try{
                        if(topLayer[xAxis][zAxis].y == (float)con.y && topLayer[xAxis][zAxis].y < (containerHeight*6))
                        {
                            double storageDT = con.vVan;
                            int storageDD = con.getDepartureDate();
                            double pickedDT = c.vVan;
                            int pickedDD = c.getDepartureDate();

                            if(pickedDD < storageDD)
                            {
                                //Add the values of the float to a Vector
                                vector = new Triplet(topLayer[xAxis][zAxis].x+storageOriginX, (topLayer[xAxis][zAxis].y+containerHeight), topLayer[xAxis][zAxis].z+storageOriginZ);
                                break;
                            }
                            else if(pickedDD == storageDD)
                            {
                                if(pickedDT < storageDT)
                                {
                                    //Add the values of the float to a Vector
                                    vector = new Triplet(con.x+storageOriginX, (con.y+containerHeight), con.z+storageOriginZ);
                                    break;
                                }
                            }
                            else
                            {
                                 break;
                            }
                        }
                    }
                    catch(Exception e){
                    //System.out.println("getStoragePosition: "+e);
                    }
                }
            }
            
            if(vector.getValue0() != null)
            {
                c.x = vector.getValue0();
                c.y = vector.getValue1();
                c.z = vector.getValue2();
                container.add(c);   
                count++;
            }
        return vector;
    }
    
    public Container[][] getTopLayer(List<Container> usedSpaces, int rowNumber)
    {
        yCoordinates= new Container[6][43];
        for(int i = 0; i < 6; i++) {
            for (int j = 0; j < 43; j++) {
                yCoordinates[i][j] = null;
            }
        }
        
        for(Container c : usedSpaces)
        {
            int zAxis = (int)(((c.z-(rowNumber*rowDepth+containerWidth))/containerWidth)+0.1f);
            int xAxis = (int) (c.x / containerLength);
            try{
                if(yCoordinates[zAxis][xAxis] == null)
                {
                    yCoordinates[zAxis][xAxis] = c;
                }
                else if(yCoordinates[zAxis][xAxis].y < c.y)
                {
                    yCoordinates[zAxis][xAxis] = c;
                }
                else
                {
                    //do nothing
                }
            }
            catch(Exception e){
                //System.out.println("getTopLayer: "+e);
            }
        }
        
        return yCoordinates;
    }
    
    public Triplet<Float, Float, Float> getFreePosition(Container[][] topLayer, int rowNumber)
    {
        boolean foundFree = false;
        int z = 0;
        int x = 0;
        float xCoord = 0;
        float zCoord = 0;
        Triplet<Float, Float, Float> freeSpace = new Triplet(null, null, null);
        while(foundFree == false)
        {
            if(x < 43)
            {
                if(z < 6)
                {
                    if(topLayer[z][x] == null)
                    {
                        xCoord = (Math.round((x*containerLength)*100));
                        xCoord = xCoord / 100;
                        zCoord = (Math.round(((z*containerWidth+containerWidth)+(rowDepth*rowNumber))*100));
                        zCoord = zCoord / 100;
                        freeSpace = new Triplet(xCoord, 0.0f, (zCoord));
                        foundFree = true;
                    }
                    else
                    {
                        z++;
                    }
                }
                else
                {
                   z = 0;
                   x++;
                }
            }
            else
            {
                break;
            }
        }
        return freeSpace;
    }

   public List<Container> getRowList(int rowNumber, List<Container> container)
   {
       List<Container> rowContains = new ArrayList<>();
       ArrayList<Float> rC = getRowCoordinates(rowNumber);
       for(Container cont : container)
       {
           //If the position of a container is in the square of the row, add it to rowContains
           if(cont.z < rC.get(3) && cont.z >= rC.get(2) && cont.x < rC.get(1) && cont.x >= rC.get(0))
           {  
               rowContains.add(cont);
           }
       }
       return rowContains;
   }
   
   public ArrayList<Float> getRowCoordinates(int rowNumber)
   {
       ArrayList<Float> rowCoordinates = new ArrayList();
       //storageOrigins are made, because at the beginning the specific coordinates 
       //of X and Z of the storage are unknown
       float minimumZ = storageOriginZ + (rowNumber*rowDepth)+containerWidth; 
       /*   The depth of the storage is found using this formula
       *    storageOrigin + 2.4f is the start of the first row of containers
       *    So every following row skips 19.2f (the depth of a row) and 2.4f.
       *    The 2.4f skip is for the Crane movement*/
       float maximumZ = minimumZ + containerStorage;
       // Containers have a depth of 2.4, so 2.4f*6 is 14.4 which is the space where containers can be
       float minimumX = storageOriginX;
       //minimalX coordinates is always storageOriginX
       float maximumX = minimumX + storageWidth;
       //Width of the row is 43*13.7f, which is 43 times the length of a container
       rowCoordinates.add(minimumX);
       rowCoordinates.add(maximumX);
       rowCoordinates.add(minimumZ);
       rowCoordinates.add(maximumZ);
       
       return rowCoordinates;
   }
}