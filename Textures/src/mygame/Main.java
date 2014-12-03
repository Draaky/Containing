package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.water.WaterFilter;
import java.util.ArrayList;

public class Main extends SimpleApplication {

    public BulletAppState bulletAppState;
    RigidBodyControl p_phy;
    //water
    private WaterFilter water;
    private Vector3f lightdir = new Vector3f (-4f,-1f,5f);
    public ArrayList<Container> contList = new ArrayList<Container>();
    ArrayList<BargeShip> bList = new ArrayList<BargeShip>();
    //public ArrayList<Ship> shipList = new ArrayList<Ship>();
    SeaShip s;
    BargeShip bs;

    public static void main(String[] args) {
        Main app = new Main();
        
        //evt voor te programmeren resolutie etc. 
        AppSettings settings = new AppSettings(false);
        settings.setResolution(1920, 1080);
        settings.setFullscreen(false);
        settings.setTitle("Containing");
        settings.setSettingsDialogImage("/Interface/containing.jpg");
        app.setSettings(settings);
        app.start(); 
    }

    @Override
    public void simpleInitApp() {
        initScene();
        initPPcWater();
        initInputs();
        
        Platform p = new Platform(rootNode, assetManager);
        p.createPlatform();
        
       s = new SeaShip(rootNode, assetManager);
       //bs = new BargeShip(rootNode, assetManager);

//        platformen eromheen
//        TreinPlatform();
//        AchterPlatform();
//        VrachtwagenPlatform();
        
        for(int i = 0; i < 2; i++)
        {
            bList.add(new BargeShip(rootNode, assetManager));           
        }
        float x1 = 0f;
        float z1 = 0;

        for (BargeShip b : bList)
        {
            b.shipNode.setLocalTranslation(x1, 0f, z1);
            x1 += 400f; 
        }        
       
        for(int i = 0; i < 20; i++)
        {
            contList.add(new Container(rootNode, assetManager));
        }
                
        //container start locaties
        float x = 708.8f;
        float z = 306.4f;
        for (Container c : contList)
        {
            //c.setXYZ(x, 15.1f, z); 
            c.containerNode.setLocalTranslation(x, 15.1f, z);
            x += 2.5f; z += 0f; //4.6
        }
                
        flyCam.setMoveSpeed(200);
        cam.setLocation(new Vector3f(30f,100f,0));
        cam.lookAt(new Vector3f(0, 0, 0), new Vector3f(0,0,0));
        //cam.setFrustumPerspective(45, 0.8f, 10000, 200000);
        cam.setFrustumFar(2000);
        cam.onFrameChange();     

    }
        
    // <editor-fold defaultstate="collapsed" desc="Platformen eromheen">
    private void TreinPlatform(){
        Box platform = new Box(534f, 30f, 1107f);
        
        Geometry p = new Geometry("platform", platform);
        Material plat_mat;
        plat_mat = new Material (assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        p.setMaterial(plat_mat);
        p.setLocalTranslation(0, -15f, 1326f);
        plat_mat.setColor("Color", ColorRGBA.LightGray);
        this.rootNode.attachChild(p);
    }
    
    private void AchterPlatform(){
        Box platform = new Box(1107f, 30f, 1534);
        
        Geometry p = new Geometry("platform", platform);
        Material plat_mat;
        plat_mat = new Material (assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        p.setMaterial(plat_mat);
        p.setLocalTranslation(-1641f, -15f, 213f);
        plat_mat.setColor("Color", ColorRGBA.LightGray);
        this.rootNode.attachChild(p);
    }
        
    private void VrachtwagenPlatform(){
        Box platform = new Box(267f, 30f, 50f);
        
        Geometry p = new Geometry("platform", platform);
        Material plat_mat;
        plat_mat = new Material (assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        p.setMaterial(plat_mat);
        p.setLocalTranslation(-300f, -15f, -271f);
        plat_mat.setColor("Color", ColorRGBA.LightGray);
        this.rootNode.attachChild(p);
    }
    // </editor-fold>   
            
    // <editor-fold defaultstate="collapsed" desc="Lichten">
        private void initLight()
    {
        /** A white ambient light source. */ 
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient); 

        /** A white, spot light source. */ 
        PointLight lamp = new PointLight();
        lamp.setPosition(Vector3f.ZERO);
        lamp.setColor(ColorRGBA.White);
        rootNode.addLight(lamp); 
        
            /** A white, directional light source */ 
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun); 
    }    
    //</editor-fold>
    
    //scene
    private void initScene(){
        Spatial scene = assetManager.loadModel("Scenes/Scene.j3o");
        rootNode.attachChild(scene);
    }
    
   // <editor-fold defaultstate="collapsed" desc="Water">
    public void initPPcWater()
    { 
    FilterPostProcessor fpp = new FilterPostProcessor(assetManager); 
    water = new WaterFilter(rootNode, lightdir); water.setCenter(Vector3f.ZERO); 
    water.setRadius(2600); 
    water.setWaveScale(0.012f); 
    water.setMaxAmplitude(2f); //0.5 
    water.setFoamExistence(new Vector3f(1f, 4f, 0.5f)); 
    //water.setFoamTexture((Texture2D) assetManager.loadTexture("Common/MatDefs/Water/Textures/foam.jpg")); 
    water.setRefractionStrength(0.2f);
    water.setWaterHeight(10f); 
    fpp.addFilter(water); 
    viewPort.addProcessor(fpp); 
    }
    //</editor-fold>    
    
   public void initInputs(){
        inputManager.addMapping("shoot", 
        new KeyTrigger(KeyInput.KEY_P));
   
   ActionListener AL = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf) {
          if (name.equals("shoot") && keyPressed) {
              System.out.println("Start ship"); 
                 s.setMotion();
                 s.playMotion();   
                   for (BargeShip b : bList){
                    b.setMotion();
                    b.playMotion();
                   }
          }
        }
      };
   inputManager.addListener(AL, "shoot");  
   };
           
    @Override
    public void simpleUpdate(float tpf) {
            //s.move();
        
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
