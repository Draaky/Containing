

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
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
    Container container;
    boolean isMoving;
    
    private boolean active = true;
    private boolean playing = false;
    private MotionPath path;
    private MotionEvent motionControl;
    
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
        //truckNode.scale(0.5f);
        rootNode.attachChild(truckNode);
    }    
    public void addContainer(Container container)
    {
        System.out.println("TRUCK HAS CONTAIENR");
        this.container = container;  
    }
    public Container removeContainer()
    {
        Container result = container;  //save container in new value;
        container = null;              //clean container.
        return result;
    }
    public void move(){
        if(container != null)
            container.containerNode.setLocalTranslation(
                truckNode.getLocalTranslation().x,
                truckNode.getLocalTranslation().y + 3.5f,
                truckNode.getLocalTranslation().z + 3f);
    }
    public void arrived(){
        container.isMoving = false;
        container = null;
    }
    public void setMotion(){
        path = new MotionPath();
        path.addWayPoint(new Vector3f(10, 3, 0));
        path.addWayPoint(new Vector3f(10, 3, 10));
        path.addWayPoint(new Vector3f(-40, 3, 10));
        path.addWayPoint(new Vector3f(-40, 3, 0));
        path.addWayPoint(new Vector3f(-40, 8, 0));
        path.addWayPoint(new Vector3f(10, 8, 0));
        path.addWayPoint(new Vector3f(10, 8, 10));
        path.addWayPoint(new Vector3f(15, 8, 10));
        path.enableDebugShape(assetManager, rootNode);
        
        motionControl = new MotionEvent(truckNode,path);
        motionControl.setDirectionType(MotionEvent.Direction.PathAndRotation);
        motionControl.setRotation(new Quaternion().fromAngleNormalAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y));
        motionControl.setInitialDuration(10f);
        motionControl.setSpeed(2f);     
        path.setCycle(true);
        
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

