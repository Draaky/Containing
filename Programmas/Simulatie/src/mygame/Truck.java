

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
import com.jme3.math.Spline;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;
import jme3tools.optimize.GeometryBatchFactory;

/**
 *
 * @author Janco
 */
public class Truck {
    
    Node rootNode;
    public Node truckNode;
    AssetManager assetManager;
    public Container container;
    boolean isMoving;
    boolean isArrived = false;
    boolean isLeaving = false;
    
    private boolean active = true;
    private boolean playing = false;
    private MotionPath path;
    private MotionEvent motionControl;
    private Vector3f deSpawnLoc = new Vector3f(-850, - 20000, -312);
    public Vector3f destination = new Vector3f();
    
    public Truck(Node rootNode, AssetManager assetManager)
    {
        this.rootNode = rootNode;
        this.assetManager = assetManager;
        createTruck();
        container = null;
        isMoving = false;
    }
    
    private void createTruck()
    {
        Box b = new Box(2.4f, 2.9f, 2.5f);
        Geometry cabin = new Geometry("Box", b);
        cabin.move(0, 3.4f, -13.7f);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        cabin.setMaterial(mat);
        
        Box b2 = new Box(2.4f, 0.5f, 16.2f);
        Geometry flat = new Geometry("Box2", b2);
        Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.Brown);
        flat.setMaterial(mat2);

        Cylinder w = new Cylinder(20, 50, 2, 1, true);
        Geometry wheel = new Geometry("Wheel", w);
        wheel.rotate(0,(float)(0.5*Math.PI),0);
        wheel.move(2.1f, -1.5f, 12.5f);
        Material mat3 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat3.setColor("Color", ColorRGBA.DarkGray);
        wheel.setMaterial(mat3);
        Geometry wheel2 = new Geometry("Wheel2", w);
        wheel2.rotate(0,(float)(0.5*Math.PI),0);
        wheel2.move(-2.1f, -1.5f, 12.5f);
        wheel2.setMaterial(mat3);
        Geometry wheel3 = new Geometry("Wheel3", w);
        wheel3.rotate(0,(float)(0.5*Math.PI),0);
        wheel3.move(2.1f, -1.5f, -12);
        wheel3.setMaterial(mat3);
        Geometry wheel4 = new Geometry("Wheel4", w);
        wheel4.rotate(0,(float)(0.5*Math.PI),0);
        wheel4.move(-2.1f, -1.5f, -12);
        wheel4.setMaterial(mat3);

        Node truck = new Node("truck");
        truck.attachChild(cabin);
        truck.attachChild(flat);
        truck.attachChild(wheel);
        truck.attachChild(wheel2);
        truck.attachChild(wheel3);
        truck.attachChild(wheel4);
        truckNode = GeometryBatchFactory.optimize(truck, true);
        truckNode.scale(0.5f);
        rootNode.attachChild(truckNode);
    }    
    public void addContainer(Container container) // add container to crane.
    {
        System.out.println("TRUCK HAS CONTAINER");        
//        container.containerNode.rotate(0,(float)(0.5*Math.PI),0);
        container.containerNode.setLocalTranslation(0, 3.25f, 3.5f);        
        this.container = container;
        truckNode.attachChild(container.containerNode);
        container.containerNode.scale(2f);
    }
    public Container removeContainer()                  // remove container from crane.
    {
        Container result = container;  //save container in new value;
        container = null;              //clean container.
        return result;
    }
    public void removeContainerPerm()
    {
        truckNode.detachChild(container.containerNode);
        container = null;
    }
    public void move(){                 // move the container.
//        if(container != null)
//            container.containerNode.setLocalTranslation(
//                truckNode.getLocalTranslation().x,
//                truckNode.getLocalTranslation().y + 1.75f,
//                truckNode.getLocalTranslation().z + 1.5f);
    }
    public void arrivedHeen(){
//        container.isMoving = false;
//        container = null;
        isArrived = true;
        isLeaving = true;
    }
    
    public void arrivedTerug(){
        isArrived = false;
        isLeaving = false;
    }
    
    public void setMotionHeen(){
        path = new MotionPath();
        path.addWayPoint(new Vector3f(-850, - 11.45f, -312));
        path.addWayPoint(new Vector3f(-770, - 11.45f, -312));
        path.addWayPoint(new Vector3f(-770, - 11.45f, -355));
        //path.addWayPoint(new Vector3f(-300 , -11.45f, -355));
        //path.addWayPoint(new Vector3f(-300 , -11.45f, -390));
        path.addWayPoint(destination);
        path.addWayPoint(new Vector3f(destination.x, destination.y, destination.z -55));
        path.setPathSplineType(Spline.SplineType.Linear);
        path.enableDebugShape(assetManager, rootNode);
        
        motionControl = new MotionEvent(truckNode,path);
        motionControl.setDirectionType(MotionEvent.Direction.PathAndRotation);
        motionControl.setRotation(new Quaternion().fromAngleNormalAxis(-FastMath.PI, Vector3f.UNIT_Y));
        //motionControl.setInitialDuration(10f);
        motionControl.setSpeed(1000f / path.getLength());
        
        path.addListener(new MotionPathListener() {

            public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                if (path.getNbWayPoints() == wayPointIndex + 1) {
                    arrivedHeen();       
                } else {
                    
                }
            }
        });
    }    
    
    public void setMotionTerug(){
        path = new MotionPath();
        path.addWayPoint(new Vector3f(destination.x, destination.y, destination.z -10));
//        path.addWayPoint(new Vector3f(-310 , -11.45f, -390));
//        path.addWayPoint(new Vector3f(-310 , -11.45f, -365));
        path.addWayPoint(new Vector3f(-780, - 11.45f, -365));
        path.addWayPoint(new Vector3f(-780, - 11.45f, -332));
        path.addWayPoint(new Vector3f(-850, - 11.45f, -332));
        path.setPathSplineType(Spline.SplineType.Linear);
        path.enableDebugShape(assetManager, rootNode);
        
        motionControl = new MotionEvent(truckNode,path);
        motionControl.setDirectionType(MotionEvent.Direction.PathAndRotation);
        motionControl.setRotation(new Quaternion().fromAngleNormalAxis(-FastMath.PI, Vector3f.UNIT_Y));
        //motionControl.setInitialDuration(10f);
        motionControl.setSpeed(1000f / path.getLength());
        
        path.addListener(new MotionPathListener() {

            public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                if (path.getNbWayPoints() == wayPointIndex + 1) {
                    arrivedTerug();                    
                    truckNode.setLocalTranslation(deSpawnLoc);
                    if(container != null)
                    {
                        removeContainerPerm();
                    }
                } else {
                    
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

