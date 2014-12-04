

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
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
    Container container;
    
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
        //agvNode.scale(0.5f);
        rootNode.attachChild(agvNode);
    }    
    public void addContainer(Container container)
    {
        //System.out.println("AGV HAS CONTAIENR");
        this.container = container;  
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
                agvNode.getLocalTranslation().y + 3.5f,
                agvNode.getLocalTranslation().z + 3f);
    }
    public void arrived(){
        container.isMoving = false;
        container = null;
    }
}

