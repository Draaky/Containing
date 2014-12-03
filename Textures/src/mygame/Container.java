package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import java.util.ArrayList;
import java.util.Random;

public class Container {
    public Node rootNode;
    public Node containerNode;
    //Spatial model;
    public AssetManager assetManager;
    boolean isMoving;
       
    public Container(Node rootNode, AssetManager assetManager)
    {
        this.rootNode = rootNode;
        this.assetManager = assetManager;
        createContainer();
        isMoving = false;
    }

//    public Spatial getModel() {
//        return model;
//    }
//    public void setModel(Spatial model) {
//        this.model = model;
//    }
//    public RigidBodyControl getBody(){
//        return model.getControl(RigidBodyControl.class);
//    }
    
    //String name, Vector3f position, AssetManager asset, Node RootNode
    private void createContainer(){
//        model = asset.loadModel("Models/container/container.j3o");
//        model.scale(0.8f, 0.8f, 0.8f);
//        model.setLocalTranslation(position);
//        model.setName(name);
        //rootNode.attachChild(model);
        Box containerbox = new Box(1.2f, 1.45f, 6.85f);
        Geometry container = new Geometry("Container", containerbox);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.randomColor());
        container.setMaterial(mat);
        
        containerNode = new Node("Container");
        containerNode.attachChild(container);
        rootNode.attachChild(containerNode);
    }
    
    public void setXYZ(float x, float y, float z){
        containerNode.setLocalTranslation(x, y, z);
    }
            
}
