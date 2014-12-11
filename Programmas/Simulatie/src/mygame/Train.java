package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;
import java.util.ArrayList;
import jme3tools.optimize.GeometryBatchFactory;
import mygame.Container;


//Jos
public class Train extends Node {
    Node trainNode = new Node("Train");
    Container traincontainer;
    private Spatial train;
    
    public Train(AssetManager assetManager)
    {
        //traincontainer = null;   
        train = assetManager.loadModel("Models/train/train_1.j3o");
        //train.rotate(0.0f,(float)(0.5*Math.PI), 0.0f);      //eventueel
        //train.setLocalTranslation(trainLoc);
        train.setCullHint(CullHint.Dynamic);
        train.scale(Main.Scale);   
        
        attachChild(train);
        
        // You must add a light to make the model visible
        //        DirectionalLight sun = new DirectionalLight();
        //        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
        //        addLight(sun);
    }
    
    //trein grootte:
    //150 lang: locomotief 13
    //hoogte: 1
    //breedte: 2.4
    public void addContainer(Container container)
    {
        //System.out.println("SHIP HAS A CONTAINER");
        this.traincontainer = container;  
    }
    public Container removeContainer()
    {
        //System.out.println("SHIP LOST A CONTAIENR");
        Container result = traincontainer;  //save container in new value;
        traincontainer = null;              //clean container.
        return result;
    }
//    public void move(){
//        if(container != null)
//            container.containerNode.setLocalTranslation(
//                trainNode.getLocalTranslation().x,
//                trainNode.getLocalTranslation().y + 1.75f,
//                trainNode.getLocalTranslation().z + 1.5f);
//    }
    
    
    public void arrived(){
        traincontainer.isMoving = false;
        traincontainer = null;
    }
}
