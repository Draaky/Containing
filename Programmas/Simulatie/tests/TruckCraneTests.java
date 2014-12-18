/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jme3.app.SimpleApplication;
import com.jme3.scene.Node;
import java.util.ArrayList;
import mygame.AGV;
import mygame.Container;
import mygame.Main;
import mygame.Truck;
import mygame.TruckCrane;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Pieter
 */
public class TruckCraneTests{
    
    ArrayList<Truck> truckList = new ArrayList<Truck>();
    ArrayList<TruckCrane> truckCraneList = new ArrayList<TruckCrane>();
    ArrayList<AGV> AGVList = new ArrayList<AGV>();
    ArrayList<Container> containerList = new ArrayList<Container>();
    
            
    public TruckCraneTests() {
        
       /* truckCraneList.get(0).setMotion(1);
        truckCraneList.get(0).playMotion();*/
        //truckCraneList.get(0).
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        for(int i = 0; i < 10; i++)
        {
            /*containerList.add( new Container(this.rootNode, this.assetManager));
            truckList.add( new Truck(this.rootNode, this.assetManager));
            truckList.get(i).addContainer(containerList.get(i));
            AGVList.add(new AGV(this.rootNode, this.assetManager));*/
        }
    }
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

}