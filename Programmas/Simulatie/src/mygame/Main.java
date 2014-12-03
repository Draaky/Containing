package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import java.util.ArrayList;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Quaternion;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends SimpleApplication {

    public static void main(final String[] args) {
        Main app = new Main();
        app.start();
        // Create Client.
        Thread EchoClientSimmulator = new Thread(){
           public void run(){
               EchoClient.main(args);
           }
       };
       EchoClientSimmulator.start();
    }
    // zoek uit hoe een echte string buffer werkt. "roundedbuffer."
    static LinkedList<String> inputBuffer = new LinkedList();
    ArrayList<Truck> truckList = new ArrayList<Truck>();
    ArrayList<TruckCrane> truckCraneList = new ArrayList<TruckCrane>();
    ArrayList<ShipCrane> shipCraneList = new ArrayList<ShipCrane>();
    ArrayList<SeaShipCrane> seaShipCraneList = new ArrayList<SeaShipCrane>();
    ArrayList<Container> containerList = new ArrayList<Container>();
    ArrayList<AGV> AGVList = new ArrayList<AGV>();
    Box    floor;
    Quaternion pitch90 = new Quaternion();
    
    @Override
    public void simpleInitApp() {
        viewPort.setBackgroundColor(ColorRGBA.LightGray);
        flyCam.setMoveSpeed(100);
        flyCam.setRotationSpeed(10f);
        //cam.setLocation(new Vector3f(10f,20f,-35f));
        
        // create floor.
        initFloor();
        
        // spawn 10 trucks, agv's and trucks.
        for(int i = 0; i < 10; i++)
        {
            containerList.add( new Container(this.rootNode, this.assetManager));
            truckList.add( new Truck(this.rootNode, this.assetManager));
            truckList.get(i).addContainer(containerList.get(i));
            AGVList.add(new AGV(this.rootNode, this.assetManager));
        }
        // spawn 10 truckcranes. Place AGV's and trucks at a location.
        for(int k=0; k < 10;k++)
        {
            truckCraneList.add( new TruckCrane(this.rootNode, this.assetManager, 
                           new Vector3f(20*(k+1),0,0)));
            /*truckCraneList.get(k).setAGV(AGVList.get(k));
            truckCraneList.get(k).setTruck(truckList.get(k));*/
            truckList.get(k).truckNode.setLocalTranslation(-150+(10*k), - 11.45f, 150);
            AGVList.get(k).agvNode.setLocalTranslation(150+(10*k), - 11.45f, -150);
            //useCommand("tc " + k + " 1");
        }
        /*truckCraneList.add( new TruckCrane(this.rootNode, this.assetManager, 
                           new Vector3f(0,0,0)));
        
       /* shipCraneList.add( new ShipCrane(this.rootNode, this.assetManager, 
                           new Vector3f(100,0,0)));
        
        seaShipCraneList.add( new SeaShipCrane(this.rootNode, this.assetManager, 
                           new Vector3f(-100,0,0)));*/
        
       // input commands for testing use.
       inputManager.addMapping("shoot", 
               new MouseButtonTrigger(MouseInput.BUTTON_LEFT) );
       
       inputManager.addMapping("play", 
       new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
       inputManager.addListener(actionListener, "shoot", "play");
       
        
       // useCommand("tc 2 1");
        
       
       // Thread  for using commands 
        Thread thread;
        thread = new Thread(){
            @Override
            public void run(){
                System.out.println("Thread Running, checking inputbuffer.");
                while(true){
                    if (!inputBuffer.isEmpty())
                    {
                        System.out.println("pop");
                        useCommand(inputBuffer.pop());
                    }
                    else{
                        // do nothing // this is bug fix.
                        System.out.print("");
                    }
                }
            }
        };
        thread.start();
    }
    public void initFloor() {
        Material floor_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");  
        floor = new Box(600f, 0.1f, 19.2f);
        
        Geometry floor_geo = new Geometry("Floor", floor);
        floor_geo.setMaterial(floor_mat);
        floor_geo.setLocalTranslation(0, -15f, 0);
        this.rootNode.attachChild(floor_geo);
  }
    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
        
        for(AGV agv : AGVList)
        {
            agv.move();
        }
        for(Truck truck : truckList)
        {
            truck.move();
        }
        for(TruckCrane tc: truckCraneList)
        {
            tc.setContainerLoc();
        }
        for(ShipCrane sc: shipCraneList)
        {
            sc.moveMagnet();
        }
        for(SeaShipCrane ssc: seaShipCraneList)
        {
            ssc.moveMagnet();
        }
    }
    public void useCommand(String command){
        
        String[] parts = command.split(" ");
        //if(parts.length == 3){
            String cmd = parts[1];
            int id = 0;
            int action = 0;
            try{
                 id = Integer.parseInt(parts[2]);// id
                 action = Integer.parseInt(parts[3]);// command
            }
            catch(Exception e){System.out.println(e + "\n cannot convert string part to int.");}
            
            if(cmd.equals("tc")) // TruckCrane 
            {
                try{
                    System.out.println(truckCraneList.get(id));
                    truckCraneList.get(id).setMotion(action);
                    truckCraneList.get(id).playMotion();
                }
                catch(Exception e){System.out.println(e);}
            }
            else if(cmd.equals("sc")) // ShipCrane
            {
                try{
                    System.out.println(shipCraneList.get(id));
                    shipCraneList.get(id).setMotion(action);
                    shipCraneList.get(id).playMotion();
                }
                catch(Exception e){System.out.println(e);}
            }
            else if(cmd.equals("ssc")) // SeaShipCrane
            {
                try{
                    System.out.println(seaShipCraneList.get(id));
                    seaShipCraneList.get(id).setMotion(action);
                    seaShipCraneList.get(id).playMotion();
                }
                catch(Exception e){System.out.println(e);}
            }
            else if(cmd.equals("tcAddTruck")){
                try{
                    System.out.println(truckList.get(action));
                    truckCraneList.get(id).setTruck(truckList.get(action));
                }
                catch(Exception e){System.out.println(e);}
            }
            else if(cmd.equals("tcAddAGV")){
                try{
                    System.out.println(AGVList.get(action));
                    truckCraneList.get(id).setAGV(AGVList.get(action));
                }
                catch(Exception e){System.out.println(e);}
            }
        //}
        //else
           // System.out.println("Invalid String : size = " + parts.length + ", must be 3");
    }
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf) {
          if (name.equals("play") && !keyPressed) {
              
              //System.out.println("START  Truck AGV");
              for(TruckCrane truckC : truckCraneList)
              {
                    System.out.println("\n" +truckC.truckCraneNode.getLocalTranslation());
                    truckC.setMotion(1);
                    truckC.playMotion();
              }
          }
          if (name.equals("shoot") && !keyPressed) {
                //System.out.println("START AGV Truck");
               /* for(TruckCrane truckC : truckCraneList)
                {
                      truckC.setMotion(2);
                      truckC.playMotion();
                }*/
              System.out.println(inputBuffer.size());
              for(String line: inputBuffer)
              {
                  System.out.println("comand : " + line);
              }
            }
        }
    };
    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
