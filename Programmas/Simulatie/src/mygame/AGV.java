

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
public class AGV {
    
    Node rootNode;
    Node agvNode;
    AssetManager assetManager;
    public Container container;
    boolean isArrived = false;
    boolean isLeaving = false;
    boolean idle = true;
    
    private boolean active = true;
    private boolean playing = false;
    private MotionPath path;
    private MotionEvent motionControl;
    public Vector3f destination = new Vector3f();
    
    public AGV(Node rootNode, AssetManager assetManager)
    {
        this.rootNode = rootNode;
        this.assetManager = assetManager;
        createAGV();
        container = null;
    }
    
    private void createAGV()
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

        Node agv = new Node("AGV");
        agv.attachChild(flat);
        agv.attachChild(wheel);
        agv.attachChild(wheel2);
        agv.attachChild(wheel3);
        agv.attachChild(wheel4);
        agvNode = GeometryBatchFactory.optimize(agv, true);
        agvNode.scale(0.5f);
        rootNode.attachChild(agvNode);
    }    
    public void addContainer(Container container)
    {
        //System.out.println("AGV HAS CONTAIENR");
        this.container = container;
        System.out.println("TRUCK HAS CONTAINER");        
        //container.containerNode.rotate(0,(float)(0.5*Math.PI),0);
        container.containerNode.setLocalTranslation(0, 3.25f, 0);        
        agvNode.attachChild(container.containerNode);
        container.containerNode.scale(2f);
    }
    public Container removeContainer()
    {
        //System.out.println("AGV HAS LOST A CONTAIENR");
        Container result = container;  //save container in new value;
        container = null;              //clean container.
        return result;
    }
    public void move(){
        if(container != null)
            container.containerNode.setLocalTranslation(
                agvNode.getLocalTranslation().x,
                agvNode.getLocalTranslation().y + 1.75f,
                agvNode.getLocalTranslation().z + 1.5f);
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
    public void isIdle()
    {
        idle = true;
        isArrived = false;
    }
    public void setMotionTC(){
        path = new MotionPath();
        path.addWayPoint(agvNode.getLocalTranslation());
        //path.addWayPoint(new Vector3f(agvNode.getLocalTranslation().x, agvNode.getLocalTranslation().y, agvNode.getLocalTranslation().z));
        path.addWayPoint(new Vector3f(agvNode.getLocalTranslation().x , -11.45f, agvNode.getLocalTranslation().z - 33));
        path.addWayPoint(new Vector3f(destination.x, destination.y, destination.z -3));
        path.addWayPoint(new Vector3f(destination.x, destination.y, destination.z -55));
        path.setPathSplineType(Spline.SplineType.Linear);
        path.enableDebugShape(assetManager, rootNode);
        
        motionControl = new MotionEvent(agvNode,path);
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
    public void setIdle(){
        path = new MotionPath();
        path.addWayPoint(agvNode.getLocalTranslation());
        path.addWayPoint(new Vector3f(agvNode.getLocalTranslation().x , -11.45f, -325));
        path.setPathSplineType(Spline.SplineType.Linear);
        path.enableDebugShape(assetManager, rootNode);
        
        motionControl = new MotionEvent(agvNode,path);
        motionControl.setDirectionType(MotionEvent.Direction.PathAndRotation);
        motionControl.setRotation(new Quaternion().fromAngleNormalAxis(-FastMath.PI, Vector3f.UNIT_Y));
        //motionControl.setInitialDuration(10f);
        motionControl.setSpeed(1000f / path.getLength());
        
        path.addListener(new MotionPathListener() {

            public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                if (path.getNbWayPoints() == wayPointIndex + 1) {
                    isIdle();       
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

