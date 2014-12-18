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
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;
import jme3tools.optimize.GeometryBatchFactory;

/**
 *
 * @author Pieter
 */
public class Container {
    public Node rootNode;
    public Node containerNode;
    public AssetManager assetManager;
    public boolean isMoving;
    
    public Container(Node rootNode, AssetManager assetManager)
    {
        this.rootNode = rootNode;
        this.assetManager = assetManager;
        createContainer();
        isMoving = false;
    }
    
    private void createContainer()
    {
        //Box containerbox = new Box(2.4f, 2.9f, 13.7f);
        //Geometry container = new Geometry("Container", containerbox);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.randomColor());
        
        Spatial containr = assetManager.loadModel("Models/container/container.j3o");
        containr.setMaterial(mat);
        containr.setLocalTranslation(0, -1.50f, 0);
        containerNode = new Node("Container");
        containerNode.attachChild(containr);
        //containerNode.scale(0.5f);
        //ContainerNode = GeometryBatchFactory.optimize(containerNode, true);
        rootNode.attachChild(containerNode);
    }    
    public void setXYZ(float x, float y, float z){
        containerNode.setLocalTranslation(x, y, z);
    }
    
}
