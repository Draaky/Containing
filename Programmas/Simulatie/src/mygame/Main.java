package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import java.util.ArrayList;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.math.Quaternion;
import com.jme3.post.FilterPostProcessor;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture2D;
import com.jme3.ui.Picture;
import com.jme3.water.WaterFilter;
import java.util.LinkedList;

public class Main extends SimpleApplication {

    public static void main(final String[] args) {
        Main app = new Main();
        AppSettings settings = new AppSettings(false);
        settings.setFullscreen(false);
        settings.setTitle("Containing");
        settings.setSettingsDialogImage("/Interface/containing.jpg");
        app.setSettings(settings);
        app.start();
        // Create Client. That gets the commands from Controller.
        Thread EchoClientSimmulator = new Thread(){
           public void run(){
               EchoClient.main(args);
           }
       };
       EchoClientSimmulator.start();
    }
    // zoek uit hoe een echte string buffer werkt. "roundedbuffer."
    static LinkedList<String> inputBuffer = new LinkedList();
    ArrayList<Truck> truckList = new ArrayList<Truck>();
    ArrayList<TruckCrane> truckCraneList = new ArrayList<TruckCrane>();
    ArrayList<ShipCrane> shipCraneList = new ArrayList<ShipCrane>();
    ArrayList<SeaShipCrane> seaShipCraneList = new ArrayList<SeaShipCrane>();
    ArrayList<Container> containerList = new ArrayList<Container>();
    ArrayList<AGV> AGVList = new ArrayList<AGV>();
    Box    floor;
    Quaternion pitch90 = new Quaternion();
    
    // JOS
    Spatial scene;
    FilterPostProcessor fpp;
    private WaterFilter water;
    private Vector3f lightdir = new Vector3f (-4f,-1f,5f);
    public ArrayList<Container> contList = new ArrayList<Container>();
    ArrayList<BargeShip> bList = new ArrayList<BargeShip>();
    SeaShip s;
    
    @Override
    public void simpleInitApp() {
        viewPort.setBackgroundColor(ColorRGBA.LightGray);
        flyCam.setMoveSpeed(150);
        flyCam.setRotationSpeed(10f);
        cam.setLocation(new Vector3f(10f,20f,-35f)); // set cam location
        cam.setFrustumFar(9000);    // set how far cam can see
        cam.onFrameChange();       
        
        //initFloor();// create floor.
        
        //Jos`
            initPPcWater();
            initScene();
            Interface();
                    
        Platform p = new Platform(rootNode, assetManager);  // place the floor
        
        // spawn 10 trucks, agv's and trucks.
        for(int i = 0; i < 10; i++)
        {
            containerList.add( new Container(this.rootNode, this.assetManager));
            truckList.add( new Truck(this.rootNode, this.assetManager));
            truckList.get(i).addContainer(containerList.get(i));
            AGVList.add(new AGV(this.rootNode, this.assetManager));
            
            truckList.get(i).truckNode.setLocalTranslation(-150+(10*i), - 11.45f, 150);
            AGVList.get(i).agvNode.setLocalTranslation(150+(10*i), - 11.45f, -150);
            
            seaShipCraneList.add(new SeaShipCrane(this.rootNode, this.assetManager, new Vector3f(875,0,350-(i*60))));
            
        }
        for(int i = 0; i < 8; i++){
        if(shipCraneList.size() < 4)
                shipCraneList.add(new ShipCrane(this.rootNode, this.assetManager, new Vector3f(680-(i*60),0,-450)));
            else
                shipCraneList.add(new ShipCrane(this.rootNode, this.assetManager, new Vector3f(680-((i*60)+170),0,-450)));
        }
        // spawn 10 truckcranes. Place AGV's and trucks at a location.
        for(int k=0; k < 20;k++)
        {
            truckCraneList.add( new TruckCrane(this.rootNode, this.assetManager, 
                           new Vector3f(-80 - (k*20),0,-400)));
        }
        
        // spawn new bargeships
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
       
        s = new SeaShip(rootNode, assetManager); // spawn a big ship.
        //s.rootNode.
       /*// input commands for testing use.
       inputManager.addMapping("shoot", 
               new MouseButtonTrigger(MouseInput.BUTTON_LEFT) );
       
       inputManager.addMapping("play", 
       new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
       inputManager.addListener(actionListener, "shoot", "play");*/
       
        
       // useCommand("tc 2 1");
        
       // <editor-fold defaultstate="collapsed" desc="Thread">
       // Thread  for using commands 
       /* Thread thread;
        thread = new Thread(){
            @Override
            public void run(){
                System.out.println("Thread Running, checking inputbuffer.");
                while(true){
                    if (!inputBuffer.isEmpty())
                    {
                        System.out.println("pop" + inputBuffer.getFirst());
                        useCommand(inputBuffer.pop());
                    }
                    else{
                        // do nothing // this is bug fix.
                        System.out.print("");
                    }
                }
            }
        };
        thread.start();*/
       // </editor-fold>
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
    // <editor-fold defaultstate="collapsed" desc="Scene">
    private void initScene(){
        scene = assetManager.loadModel("Scenes/Scene.j3o");
        rootNode.attachChild(scene);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Water">
    public void initPPcWater()
    { 
        fpp = new FilterPostProcessor(assetManager); 
        water = new WaterFilter(rootNode, lightdir); 
        water.setCenter(Vector3f.ZERO); 
        water.setRadius(260000000); 
        water.setWaveScale(0.012f); 
        water.setMaxAmplitude(2f); //0.5 
        water.setFoamExistence(new Vector3f(1f, 4f, 0.5f)); 
        water.setFoamTexture((Texture2D) assetManager.loadTexture("Common/MatDefs/Water/Textures/foam.jpg")); 
        water.setRefractionStrength(0.2f);
        water.setWaterHeight(-20f); 
        fpp.addFilter(water); 
        viewPort.addProcessor(fpp); 
    }
    
    //</editor-fold>    
    // <editor-fold defaultstate="collapsed" desc="InitInputs">
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
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Interface">   
    public void Interface(){
        setDisplayStatView(false); setDisplayFps(false);
        Picture pic = new Picture("HUD Picture");
        pic.setImage(assetManager, "Textures/nhl.png", true);
        pic.setWidth(settings.getWidth()/5);
        pic.setHeight(settings.getHeight()/5);
        pic.setPosition(0f,20f);
        
        Picture pic2 = new Picture("HUD Picture");
        pic2.setImage(assetManager, "Textures/groep5.png", true);
        pic2.setWidth(settings.getWidth()/5);
        pic2.setHeight(settings.getHeight()/3);
        pic2.setPosition(settings.getWidth()/1.25f, settings.getHeight()/1.5f);
        guiNode.attachChild(pic);
        guiNode.attachChild(pic2);
    }
    // </editor-fold>
    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
        while (!inputBuffer.isEmpty())
        {
            System.out.println("pop" + inputBuffer.getFirst());
            useCommand(inputBuffer.pop());
        }
        
        
        for(AGV agv : AGVList)
        {
            agv.move();
        }
        for(Truck truck : truckList)
        {
            truck.move();
        }
        for(TruckCrane tc: truckCraneList)
        {
            tc.setContainerLoc();
        }
        for(ShipCrane sc: shipCraneList)
        {
            sc.moveMagnet();
        }
        for(SeaShipCrane ssc: seaShipCraneList)
        {
            ssc.moveMagnet();
        }
    }
    // Get a string convert it to a command.
    public void useCommand(String command){
        
        String[] parts = command.split(" ");
        //if(parts.length == 3){
            String cmd = parts[1];
            int id = 0;
            int action = 0;
            try{
                 id = Integer.parseInt(parts[2]);// id
                 action = Integer.parseInt(parts[3]);// command
            }
            catch(Exception e){System.out.println(e + "\n cannot convert string part to int.");}
            
            if(cmd.equals("tc")) // TruckCrane 
            {
                try{
                   // System.out.println(truckCraneList.get(id));
                    truckCraneList.get(id).setMotion(action);
                    truckCraneList.get(id).playMotion();
                }
                catch(Exception e){System.out.println(e);}
            }
            else if(cmd.equals("sc")) // ShipCrane
            {
                try{
                   // System.out.println(shipCraneList.get(id));
                    shipCraneList.get(id).setMotion(action);
                    shipCraneList.get(id).playMotion();
                }
                catch(Exception e){System.out.println(e);}
            }
            else if(cmd.equals("ssc")) // SeaShipCrane
            {
                try{
                    //System.out.println(seaShipCraneList.get(id));
                    seaShipCraneList.get(id).setMotion(action);
                    seaShipCraneList.get(id).playMotion();
                }
                catch(Exception e){System.out.println(e);}
            }
            else if(cmd.equals("tcAddTruck")){
                try{
                   // System.out.println(truckList.get(action));
                    truckCraneList.get(id).setTruck(truckList.get(action));
                }
                catch(Exception e){System.out.println(e);}
            }
            else if(cmd.equals("tcAddAGV")){
                try{
                  //  System.out.println(AGVList.get(action));
                    truckCraneList.get(id).setAGV(AGVList.get(action));
                }
                catch(Exception e){System.out.println(e);}
            }
        //}
        //else
           // System.out.println("Invalid String : size = " + parts.length + ", must be 3");
    }
    
    /*private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf) {
          if (name.equals("play") && !keyPressed) {
              
              //System.out.println("START  Truck AGV");
              for(TruckCrane truckC : truckCraneList)
              {
                    System.out.println("\n" +truckC.truckCraneNode.getLocalTranslation());
                    truckC.setMotion(1);
                    truckC.playMotion();
              }
          }
          if (name.equals("shoot") && !keyPressed) {
                //System.out.println("START AGV Truck");
                for(TruckCrane truckC : truckCraneList)
                {
                      truckC.setMotion(2);
                      truckC.playMotion();
                }
            }
        }
    };*/
    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
