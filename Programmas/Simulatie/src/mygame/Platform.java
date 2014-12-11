/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.water.WaterFilter;

/**
 *
 * @author Jos
 */

/*           DONE                */
public class Platform extends Node {
    
    public Node rootNode;
    public AssetManager assetManager;
    public BulletAppState bulletAppState;
    RigidBodyControl p_phy;
    private AudioNode s;
    
    public Platform(Node rootNode, AssetManager assetManager)
    {
        this.rootNode = rootNode;
        this.assetManager = assetManager;
        createPlatform();
    }
    
 // <editor-fold defaultstate="collapsed" desc="createPlatform">
    public void createPlatform() {
        //Box platform = new Box(848f, 30f, 428.25f);
        Box platform = new Box(848f, 5f, 428.25f);
        platform.scaleTextureCoordinates(new Vector2f(1, 1));
        
        Geometry p = new Geometry("platform", platform);
        Material plat_mat;
        plat_mat = new Material (assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        p.setMaterial(plat_mat);
        //p.setLocalTranslation(0, -45f, 0f);
        p.setLocalTranslation(0, -18.5f, 0f);
        //plat_mat.setColor("Color", ColorRGBA.Black); 
        this.rootNode.attachChild(p);
        
        TextureKey key1 = new TextureKey("Textures/platformrails.jpg");
        key1.setGenerateMips(true);
        
        Texture tex1 = assetManager.loadTexture(key1);
        tex1.setWrap(Texture.WrapMode.Repeat);
        plat_mat.setTexture("ColorMap", tex1); 
        
        Sounds(); 
   }
   // </editor-fold>
 
 // <editor-fold defaultstate="collapsed" desc="Sounds">
    private void Sounds(){
        //achtergrond geluid
        s = new AudioNode(assetManager, "Sounds/traffic-11.wav", true);
        s.setLooping(false);
        s.setPositional(false);
        s.setVolume(3);
        rootNode.attachChild(s);
        s.play();
    }
    //</editor-fold>
        
 // <editor-fold defaultstate="collapsed" desc="Platform uit model, wil niet met texture">
    public void Platform(){
        Node platform = new Node();
        Spatial model;
        model = assetManager.loadModel("Models/platform/platform.j3o");
        //model.setMaterial(assetManager.loadMaterial("Materials/platform.j3m"));
        model.scale(10f);
        model.setLocalTranslation(0, 20f, 0);
        //model.setName(name);
        
        platform.attachChild(model);
        rootNode.attachChild(platform);
    }
    // </editor-fold>
 
}
