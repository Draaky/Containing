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
public class SeaShipCrane {
    
    public Node rootNode;
    public Node seaShipCraneNode = new Node("SeaShipCrane");
    public AssetManager assetManager;
    
    Container container;
    Truck truck;
    AGV agv;
    
    private boolean active = true;
    private boolean playing = false;
    private MotionPath path;
    private MotionEvent motionControl;
    Vector3f spawnLoc;
    Vector3f posSeaShip;
    Vector3f posAGV;
    
    
    public SeaShipCrane(Node rootNode, AssetManager assetManager, Vector3f spawnLoc)
    {
        this.rootNode = rootNode;
        this.assetManager = assetManager;
        
        container = null;
        truck = null;
        agv = null;
        this.spawnLoc = spawnLoc;
        posSeaShip = new Vector3f(spawnLoc);
        posAGV  = new Vector3f(spawnLoc);
        
        posAGV.z = posAGV.z - 90;
        posSeaShip.z += 60;
        
        System.out.println(spawnLoc);
        System.out.println(posAGV);
        System.out.println(posSeaShip);
        createShipCrane();
    }
    
    private void createShipCrane()
    {
        Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.Yellow);
        
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        Spatial crane = assetManager.loadModel("Models/dockingcrane/crane.j3o");
        crane.setMaterial(mat);
        crane.scale(1.5f);
        
        crane.setLocalTranslation(new Vector3f(spawnLoc.x,spawnLoc.y-15,spawnLoc.z));
        
        Quaternion pitch90 = new Quaternion();
        pitch90.fromAngleAxis(FastMath.PI/2, new Vector3f(0,1,0));
        crane.rotate(pitch90);
        
        Cylinder w = new Cylinder(20, 50, 2, 1, true);
        Geometry superMagnet = new Geometry("magnet", w);
        superMagnet.rotate((float)(0.5*Math.PI),0,0);
        superMagnet.move(posAGV.x, posAGV.y + 25, posAGV.z);
        
        superMagnet.setMaterial(mat2);

        seaShipCraneNode.attachChild(crane);
        seaShipCraneNode.attachChild(superMagnet);
        rootNode.attachChild(seaShipCraneNode);
    }
    public void moveMagnet(){
        if(container != null)
            container.containerNode.setLocalTranslation(setContainerLoc());
    }
    public float getMagnetPos(String pos){
        if(pos.equals("X"))
            return  seaShipCraneNode.getChild("magnet").getLocalTranslation().x;
        if(pos.equals("Y"))
            return  seaShipCraneNode.getChild("magnet").getLocalTranslation().y;
        if(pos.equals("Z"))
            return  seaShipCraneNode.getChild("magnet").getLocalTranslation().z;
        
        return 0f;
    }
    public Vector3f setContainerLoc(){
        try{
            return new Vector3f(getMagnetPos("X"),getMagnetPos("Y") - 4.5f, getMagnetPos("Z") );
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return new Vector3f(0,0,0);
    }
    public void addContainer(Container container)
    {
        System.out.println("CONTAINER HAS CONTAIENR");
        this.container = container;     
        
        //Quaternion pitch90 = new Quaternion();
        //pitch90.fromAngleAxis(FastMath.PI/2, new Vector3f(0,1,0));
        //container.containerNode.rotate(pitch90);
    }
    public Container removeContainer()
    {
        System.out.println("CONTAINER HAS LOST A CONTAIENR");
        Container result = container;  //save container in new value;
        container = null;              //clean container.
        return result;
    }
    public void setTruck(Truck truck){
        this.truck = truck;
        truck.truckNode.setLocalTranslation(posSeaShip.x, posSeaShip.y - 11.45f, posSeaShip.z);
    }
    public void giveContainer(Truck truck){
        if(truck.container == null){
            truck.addContainer(this.container);
            removeContainer();
        }
    }
    public void setAGV(AGV agv){
        this.agv = agv;
        agv.agvNode.setLocalTranslation(posAGV.x, posAGV.y - 11.45f, posAGV.z);
    }
    public void setMotion(int status){
        path = new MotionPath();
        path.addWayPoint(new Vector3f(posAGV.x, posAGV.y + 25, posAGV.z));
        path.addWayPoint(new Vector3f(posSeaShip.x, posSeaShip.y + 25, posSeaShip.z));
        path.enableDebugShape(assetManager, rootNode);
        
        motionControl = new MotionEvent(seaShipCraneNode.getChild("magnet"),path);
        motionControl.setDirectionType(MotionEvent.Direction.PathAndRotation);
       // motionControl.setRotation(new Quaternion().fromAngleNormalAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y));
        motionControl.setInitialDuration(10f);
        motionControl.setSpeed(2f);     
        path.setCycle(true);
        
        

        if(status == 2 && agv != null && agv.container != null){
        addContainer(agv.removeContainer()); 
        //agv = null;
        }
        if(status == 1){ // truck -> agv
            path.addListener(new MotionPathListener() {

            public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                
               System.out.println(wayPointIndex);
                
               if (path.getNbWayPoints() == wayPointIndex + 1) {
                   System.out.println("FINISH");
                   if(agv != null && container != null){
                        agv.addContainer(removeContainer()); 
                        //agv = null;
                   }
                } else {
                   System.out.println("CP : " + wayPointIndex);
                    if(wayPointIndex == 1){
                       if(truck != null && truck.container != null){
                            addContainer(truck.removeContainer());
                            //truck = null;
                        }
                    }
                    
                }
            }
        });
            } else { // agv -> ship
                path.addListener(new MotionPathListener() {

                public void onWayPointReach(MotionEvent control, int wayPointIndex) {

                   System.out.println(wayPointIndex);

                   if (path.getNbWayPoints() == wayPointIndex + 1) {
                       System.out.println("FINISH");
                    } else {
                        System.out.println("CP : " + wayPointIndex);
                        if(wayPointIndex == 1){
                           if(truck != null && container != null){
                            truck.addContainer(removeContainer());
                            //truck = null;
                            }
                        }
                    }
                }
            });
        }
    }
    public void playMotion() {
        
       /* if (playing) {
            playing = false;
            motionControl.stop();
        } else {
            playing = true;*/
            motionControl.play();
       // }
        
    }
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
 