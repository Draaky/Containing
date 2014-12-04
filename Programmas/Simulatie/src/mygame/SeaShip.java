/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;
import jme3tools.optimize.GeometryBatchFactory;

/**
 *
 * @author Jos
 */
/*      WORK IN PROGRESS        */

public class SeaShip {
    
    Node rootNode;
    Node shipNode = new Node("ship");
    AssetManager assetManager;
    Container container;
    private MotionPath path;
    private MotionEvent motionControl;
    private boolean active = true;
    
    public SeaShip(Node rootNode, AssetManager assetManager)
    {
        this.rootNode = rootNode;
        this.assetManager = assetManager;
        createShip();
        container = null;
    }
    
    private void createShip()
    {
        Spatial s = assetManager.loadModel("Models/ship/seaship.j3o");
        s.scale(1.20f, 1.20f, 1.20f);
        //schip.rotate(0.0f, -3.0f, 0.0f);      //eventueel
        s.setLocalTranslation(880f, -
                
                15.1f, 15f);
        // You must add a light to make the model visible
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
        rootNode.addLight(sun);

        shipNode.attachChild(s);
        rootNode.attachChild(shipNode);
    } 
    
    public void addContainer(Container container)
    {
        System.out.println("SHIP HAS A CONTAINER");
        this.container = container;  
    }
    
    public Container removeContainer()
    {
        System.out.println("SHIP LOST A CONTAINER");
        Container result = container;  //save container in new value;
        container = null;              //clean container.
        return result;
    }
    
//    public void move(){
//        if(container != null)
//            container.containerNode.setLocalTranslation(
//                shipNode.getLocalTranslation().x,
//                shipNode.getLocalTranslation().y + 3.5f,
//                shipNode.getLocalTranslation().z + 3f);
//    }
    
    public void arrived(){
        container.isMoving = false;
        container = null;
    }
    
     public void setMotion(){                       //int status
        path = new MotionPath();
        
        path.addWayPoint(new Vector3f(0f, 0f, 1000f));
        path.addWayPoint(new Vector3f(0f, 0f, 0f));
        //path.addWayPoint(new Vector3f());
        path.enableDebugShape(assetManager, rootNode);
        
        motionControl = new MotionEvent(shipNode,path);
        motionControl.setDirectionType(MotionEvent.Direction.PathAndRotation);
        //motionControl.setRotation(new Quaternion().fromAngleNormalAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y));
        motionControl.setInitialDuration(10f);
        motionControl.setSpeed(2f);     
        path.setCycle(true);
        
        //path.addListener(new MotionPathListener() {
                
           
            
//            public void onWayPointReach(MotionEvent control, int wayPointIndex) {
//                System.out.println(wayPointIndex);
//                 
//                if (path.getNbWayPoints() == wayPointIndex + 1) {
//                    System.out.println(control.getSpatial().getName() + "Finished!!! ");
//                } else {
//                    System.out.println(control.getSpatial().getName() + " Reached way point " + wayPointIndex);
//                }
//            }
//        });
        
    }
     
    // play the Motion Path
    public void playMotion() {
        motionControl.play();
    }
    //Show the motion path.
    public void showMotion(){
        if (active) {
            active = false;
            path.disableDebugShape();
        } else {
            active = true;
            path.enableDebugShape(assetManager, rootNode);
        }
    } 
    
}
