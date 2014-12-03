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
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }
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
        
        initFloor();
        for(int i = 0; i < 10; i++)
        {
            containerList.add( new Container(this.rootNode, this.assetManager));
            truckList.add( new Truck(this.rootNode, this.assetManager));
            truckList.get(i).addContainer(containerList.get(i));
            AGVList.add(new AGV(this.rootNode, this.assetManager));
        }
        
        for(int k=0; k<10;k++)
        {
            truckCraneList.add( new TruckCrane(this.rootNode, this.assetManager, 
                           new Vector3f(0+(k * 10),0,0+(k * 10))));
            truckCraneList.get(k).setAGV(AGVList.get(k));
            truckCraneList.get(k).setTruck(truckList.get(k));
        }
        /*truckCraneList.add( new TruckCrane(this.rootNode, this.assetManager, 
                           new Vector3f(0,0,0)));*/
        
       /* shipCraneList.add( new ShipCrane(this.rootNode, this.assetManager, 
                           new Vector3f(100,0,0)));
        
        seaShipCraneList.add( new SeaShipCrane(this.rootNode, this.assetManager, 
                           new Vector3f(-100,0,0)));*/
        
        
       inputManager.addMapping("shoot", 
               new MouseButtonTrigger(MouseInput.BUTTON_LEFT) );
       
       inputManager.addMapping("play", 
       new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
       inputManager.addListener(actionListener, "shoot", "play");
       
       
        initFloor();
        //useCommand("tc 1 1");
        
        
        /*Thread thread = new Thread(){
            public void run(){
                System.out.println("Thread Running");
                Scanner scan = new Scanner(System.in);
                while(true){
                    String myLine = scan.nextLine();
                    if(myLine == "stop"){
                        this.stop();
                    }
                    else if(myLine != ""){
                        try{
                            this.sleep(1000);
                        }catch(Exception e){}
                    System.out.println("command = "+myLine);
                    useCommand(myLine);
                    myLine = "";}
                }
            }
        };

        thread.start();*/
        
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
        if(parts.length == 3){
            String part1 = parts[0]; // object
            int id = 0;
            int action = 0;
            try{
                 id = Integer.parseInt(parts[1]);// id
                 action = Integer.parseInt(parts[2]);// command
            }
            catch(Exception e){System.out.println(e + "\n cannot convert string part to int.");}
            if(parts[0].equals("tc")) // TruckCrane 
            {
                try{
                    truckCraneList.get(id).setMotion(action);
                    truckCraneList.get(id).playMotion();
                }
                catch(Exception e){System.out.println(e);}
            }
            else if(parts[0].equals("sc")) // ShipCrane
            {
                try{
                    shipCraneList.get(id).setMotion(action);
                    shipCraneList.get(id).playMotion();
                }
                catch(Exception e){System.out.println(e);}
            }
            else if(parts[0].equals("ssc")) // SeaShipCrane
            {
                try{
                    seaShipCraneList.get(id).setMotion(action);
                    seaShipCraneList.get(id).playMotion();
                }
                catch(Exception e){System.out.println(e);}
            }
        }
        else
            System.out.println("Invalid String : size = " + parts.length + ", must be 3");
    }
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf) {
          if (name.equals("play") && !keyPressed) {
              
              System.out.println("START  Truck AGV");
              for(TruckCrane truckC : truckCraneList)
              {
                    truckC.setMotion(1);
                    truckC.playMotion();
              }
          }
          if (name.equals("shoot") && !keyPressed) {
                System.out.println("START AGV Truck");
                for(TruckCrane truckC : truckCraneList)
                {
                      truckC.setMotion(2);
                      truckC.playMotion();
                }
            }
        }
    };
    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
