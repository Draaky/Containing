package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import java.util.ArrayList;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.scene.Node;

public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }
    ArrayList<Truck> truckList = new ArrayList<Truck>();
    ArrayList<ShipCrane> shipCraneList = new ArrayList<ShipCrane>();
    ArrayList<Container> containerList = new ArrayList<Container>();
    Box    floor;
    RigidBodyControl    floor_phy;
    private BulletAppState bulletAppState;
    Quaternion pitch90 = new Quaternion();
    AGV agv;
    
    @Override
    public void simpleInitApp() {
        viewPort.setBackgroundColor(ColorRGBA.LightGray);
        flyCam.setMoveSpeed(50);
        flyCam.setRotationSpeed(10f);
        //cam.setLocation(new Vector3f(10f,20f,-35f));
        
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        
        //initFloor();
        truckList.add( new Truck(this.rootNode, this.assetManager));
        shipCraneList.add( new ShipCrane(this.rootNode, this.assetManager, 
                           new Vector3f(0,0,0)));
        truckList.get(0).truckNode.move(truckList.get(0).truckNode.getWorldTranslation().x, 
                truckList.get(0).truckNode.getWorldTranslation().y -11.5f, 
                truckList.get(0).truckNode.getWorldTranslation().z - 90f);
        
        inputManager.addMapping("shoot", 
            new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("play", 
            new KeyTrigger(KeyInput.KEY_N));
        inputManager.addListener(actionListener, "shoot", "play");
       
        containerList.add( new Container(this.rootNode, this.assetManager));
        containerList.get(0).setXYZ(0, 6,-80f);
        
        pitch90 = new Quaternion();
        pitch90.fromAngleAxis(FastMath.PI/2, new Vector3f(0,1,0));
        //containerList.get(0).containerNode.rotate(pitch90);
        
         agv = new AGV(this.rootNode, this.assetManager);
         agv.agvNode.move(agv.agvNode.getWorldTranslation().x, 
                agv.agvNode.getWorldTranslation().y -11.5f, 
                agv.agvNode.getWorldTranslation().z );
        truckList.get(0).addContainer(containerList.get(0));
        
        shipCraneList.get(0).setTruck(truckList.get(0));
        shipCraneList.get(0).setAGV(agv);
        
        initFloor();
        shipCraneList.get(0).setMotion();
    }
    public void initFloor() {
        Material floor_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");  
        floor = new Box(1000f, 0.1f, 1000f);
        
        Geometry floor_geo = new Geometry("Floor", floor);
        floor_geo.setMaterial(floor_mat);
        floor_geo.setLocalTranslation(0, -15f, 0);
        this.rootNode.attachChild(floor_geo);
        /* Make the floor physical with mass 0.0f! */
        floor_phy = new RigidBodyControl(0.0f);
        floor_geo.addControl(floor_phy);
        bulletAppState.getPhysicsSpace().add(floor_phy);
  }
    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
        shipCraneList.get(0).moveMagnet();
        truckList.get(0).move();
        agv.move();
    }
    private ActionListener actionListener = new ActionListener() {
    public void onAction(String name, boolean keyPressed, float tpf) {
      if (name.equals("play") && !keyPressed) {
       shipCraneList.get(0).showMotion();
      }
      if (name.equals("shoot") && !keyPressed) {
            shipCraneList.get(0).playMotion();
        }
    }
  };
    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
