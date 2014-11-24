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

/**
 *
 * @author Pieter
 */
public class ShipCrane {
    
    public Node rootNode;
    public Node shipCraneNode = new Node("shipCrane");
    public AssetManager assetManager;
    
    Container container;
    Truck truck;
    AGV agv;
    
    private boolean active = true;
    private boolean playing = false;
    private MotionPath path;
    private MotionEvent motionControl;
    Vector3f spawnLoc;
    Vector3f posShip;
    Vector3f posAGV;
    
    
    public ShipCrane(Node rootNode, AssetManager assetManager, Vector3f spawnLoc)
    {
        this.rootNode = rootNode;
        this.assetManager = assetManager;
        
        
        container = null;
        truck = null;
        agv = null;
        
        posShip = new Vector3f(spawnLoc);
        posAGV  = new Vector3f(spawnLoc);
        
        posAGV.z = posAGV.z - 90;
        posShip.z += 60;
        
        System.out.println(spawnLoc);
        System.out.println(posAGV);
        System.out.println(posShip);
        createShipCrane();
    }
    
    private void createShipCrane()
    {
        Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.Yellow);
        
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Black);
       /* Box b = new Box(2.5f, 15f, 2.5f);
        Geometry pole1 = new Geometry("Box", b);
        pole1.move(-15f, 0, 0);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Red);
        pole1.setMaterial(mat);
        
        Geometry pole2 = new Geometry("Box", b);
        pole2.move(15f, 0, 0);
        pole2.setMaterial(mat);
        
        Box b3 = new Box(17.5f, 2.5f, 2.5f);
        Geometry beam = new Geometry("Box", b3);
        beam.move(0, 15f, 0);
        beam.setMaterial(mat);
        
        Box b4 = new Box(2.5f, 1f, 45f);
        Geometry rails = new Geometry("Box", b4);
        rails.move(0, 14f, -45f);
        rails.setMaterial(mat);*/
        Spatial crane = assetManager.loadModel("Models/dockingcrane/crane.j3o");
        crane.setMaterial(mat);
        crane.scale(1.5f);
        crane.setLocalTranslation(new Vector3f(0,-15,0));
        
        Quaternion pitch90 = new Quaternion();
        pitch90.fromAngleAxis(FastMath.PI/2, new Vector3f(0,1,0));
        crane.rotate(pitch90);
        
        Cylinder w = new Cylinder(20, 50, 2, 1, true);
        Geometry superMagnet = new Geometry("magnet", w);
        superMagnet.rotate((float)(0.5*Math.PI),0,0);
        superMagnet.move(posAGV.x, posAGV.y + 25, posAGV.z);
        
        superMagnet.setMaterial(mat2);

        //add parts to node.
        //shipCraneNode.attachChild(pole1);
        //shipCraneNode.attachChild(pole2);
        //shipCraneNode.attachChild(beam);
        //shipCraneNode.attachChild(rails);
        shipCraneNode.attachChild(crane);
        shipCraneNode.attachChild(superMagnet);
        rootNode.attachChild(shipCraneNode);
    }
    public void moveMagnet(){
        if(container != null)
            container.containerNode.setLocalTranslation(setContainerLoc());
    }
    public float getMagnetPosX(){
        
        return  shipCraneNode.getChild("magnet").getLocalTranslation().x;
    }
    public float getMagnetPosY(){
        
        return  shipCraneNode.getChild("magnet").getLocalTranslation().y;
    }
    public float getMagnetPosZ(){
        
        return  shipCraneNode.getChild("magnet").getLocalTranslation().z;
    }
    public Vector3f setContainerLoc(){
        try{
            return new Vector3f(getMagnetPosX(),getMagnetPosY() - 4.5f, getMagnetPosZ() );
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
        truck.truckNode.setLocalTranslation(posShip.x, posShip.y - 11.45f, posShip.z);
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
    public void setMotion(){
        path = new MotionPath();
        path.addWayPoint(new Vector3f(posAGV.x, posAGV.y + 25, posAGV.z));
        path.addWayPoint(new Vector3f(posShip.x, posShip.y + 25, posShip.z));
        path.enableDebugShape(assetManager, rootNode);
        
        motionControl = new MotionEvent(shipCraneNode.getChild("magnet"),path);
        motionControl.setDirectionType(MotionEvent.Direction.PathAndRotation);
       // motionControl.setRotation(new Quaternion().fromAngleNormalAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y));
        motionControl.setInitialDuration(10f);
        motionControl.setSpeed(2f);     
        path.setCycle(true);
        
        path.addListener(new MotionPathListener() {

            public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                if (path.getNbWayPoints() == wayPointIndex + 1) {
                    //wayPointsText.setText(control.getSpatial().getName() + "Finished!!! ");
                    // give container crane to agv
                    //remove agv
                    if(agv != null){
                        agv.addContainer(removeContainer()); 
                        agv = null;
                    }
                } else {
                    //wayPointsText.setText(control.getSpatial().getName() + " Reached way point " + wayPointIndex);
                    // get container from truck
                    //remove truck
                    if(truck != null){
                        addContainer(truck.removeContainer());
                        truck = null;
                    }
                }
            }
        });
    }
    public void playMotion() {
        
        if (playing) {
            playing = false;
            motionControl.stop();
        } else {
            playing = true;
            motionControl.play();
        }
        
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
 