/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionEvent;
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
import mygame.AGV;
import mygame.Container;
import mygame.Truck;

/**
 *
 * @author Pieter
 */
public class TruckCrane {
    
    public Node rootNode;
    public Node truckCraneNode = new Node("truckCrane");
    public AssetManager assetManager;
    
    Container container;
    Truck truck;
    AGV agv;
    
    private boolean active = true;
    private MotionPath path;
    private MotionEvent motionControl;
    private MotionEvent motionControlContainer;
    private Vector3f spawnLoc;
    private Vector3f posTruck;
    private Vector3f posAGV;
    
    public TruckCrane(Node rootNode, AssetManager assetManager, Vector3f spawnLoc)
    {
        this.rootNode = rootNode;
        this.assetManager = assetManager;
        
        
        container = null;   // container
        truck = null;
        agv = null;
        this.spawnLoc = spawnLoc;
        posTruck = new Vector3f(spawnLoc.x , spawnLoc.y - 11.45f, spawnLoc.z - 17);
        posAGV  = new Vector3f(spawnLoc.x, spawnLoc.y - 11.45f, spawnLoc.z + 17);
        
        System.out.println("spawn" + spawnLoc);
        System.out.println("AGV" + posAGV);
        System.out.println("Truck" + posTruck);
        createTruckCrane();
        
    }
    
    private void createTruckCrane()
    {
        Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.Yellow);
        
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Black);
        
        Spatial crane = assetManager.loadModel("Models/truckcrane/crane.j3o");
        crane.setMaterial(mat);
        //crane.scale(2.5f);
        //System.out.println(spawnLoc);
        crane.setLocalTranslation(new Vector3f(spawnLoc.x,spawnLoc.y-15,spawnLoc.z));
        
       /* Quaternion pitch90 = new Quaternion();
        pitch90.fromAngleAxis(FastMath.PI/2, new Vector3f(0,1,0));
        crane.rotate(pitch90);*/
        
        //Cylinder w = new Cylinder(20, 50, 2, 1, true);
        /*Geometry superMagnet = new Geometry("magnet", w);
        superMagnet.rotate((float)(0.5*Math.PI),0,0);
        superMagnet.move(posAGV.x, posAGV.y + 11.5f, posAGV.z);
        
        superMagnet.setMaterial(mat2);*/

        //add parts to node.
        truckCraneNode.attachChild(crane);
        //truckCraneNode.attachChild(superMagnet);
        rootNode.attachChild(truckCraneNode);
    }
    public void setContainerLoc(){
        if(container != null)
            container.containerNode.setLocalTranslation(
                new Vector3f(spawnLoc.x,
                    truckCraneNode.getLocalTranslation().y - 7.5f,
                    truckCraneNode.getLocalTranslation().z));
           
    }
    public void addContainer(Container container)
    {
        //System.out.println("CRANE HAS CONTAINER");
        this.container = container;   
    }
    public Container removeContainer()
    {
        //System.out.println("CRANE HAS LOST A CONTAINER");
        Container result = container;  //save container in new value;
        container = null;              //clean container.
        return result;
    }
    public void setTruck(Truck truck){
        this.truck = truck;
        truck.truckNode.setLocalTranslation(posTruck);
    }
    
    public void setAGV(AGV agv){
        this.agv = agv;
        agv.agvNode.setLocalTranslation(posAGV);
    }
    
    // Motion path
    public void setMotion(int status){ // status 1 agv -> truck // status 2 truck -> agv
        path = new MotionPath();
        
            path.addWayPoint(new Vector3f(0, spawnLoc.y, posAGV.z));
            path.addWayPoint(new Vector3f(0, spawnLoc.y, posTruck.z +5)); // + 5f));
            path.addWayPoint(new Vector3f(0, spawnLoc.y, posAGV.z));
            
        path.enableDebugShape(assetManager, rootNode);
        
        motionControl = new MotionEvent(truckCraneNode,path);
        
        motionControl.setDirectionType(MotionEvent.Direction.PathAndRotation);
       // motionControl.setRotation(new Quaternion().fromAngleNormalAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y));
        motionControl.setInitialDuration(10f);
        motionControl.setSpeed(2f);   
        
        
        path.setCycle(false);
        if(status == 2 && agv != null && agv.container != null){
            addContainer(agv.removeContainer()); 
            motionControl.setSpeed(motionControl.getSpeed() / 2);  
            //agv = null;
        }
        if(status == 1){ // agv -> truck
            path.addListener(new MotionPathListener() {
            public void onWayPointReach(MotionEvent control, int wayPointIndex) {
               //System.out.println(wayPointIndex);
                
               if (path.getNbWayPoints() == wayPointIndex + 1) {
                   //System.out.println("FINISH");
                   if(agv != null && container != null){
                        agv.addContainer(removeContainer()); 
                        motionControl.setSpeed(motionControl.getSpeed() * 2);  
                        //agv = null;
                   }
                } else {
                   //System.out.println("CP : " + wayPointIndex);
                    if(wayPointIndex == 1){
                       if(truck != null && truck.container != null){
                            addContainer(truck.removeContainer());
                            motionControl.setSpeed(motionControl.getSpeed() / 2);  
                            //truck = null;
                        }
                    }
                }
            }
        });
        } else { // truck -> agv
            path.addListener(new MotionPathListener() {

            public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                
               //System.out.println(wayPointIndex);
                
               if (path.getNbWayPoints() == wayPointIndex + 1) {
                   //System.out.println("FINISH");
                } else {
                    //System.out.println("CP : " + wayPointIndex);
                    if(wayPointIndex == 1){
                       if(truck != null && container != null){
                        truck.addContainer(removeContainer());
                        motionControl.setSpeed(motionControl.getSpeed() * 2);  
                        //truck = null;
                        }
                    }
                }
            }
        });
        }
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