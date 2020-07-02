package mainGame;

import Models.RawModel;
import Models.TexturedModel;
import crosshair.Crosshair;
import crosshair.CrosshairManager;
import debugMenu.DebugMenuManager;
import entities.*;
import display.DisplayManager;
import fontRendering.TextMaster;
import org.joml.Vector3f;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import shaders.StaticShader;
import staticRenderEngine.StaticModelShader;
import textures.ModelTexture;
import tools.CursorTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;

public class MainGameManager {

    public StaticShader shader = new StaticShader();

    Loader loader = new Loader();
    CursorTools cursorTools = new CursorTools();
    MasterRenderer masterRenderer = new MasterRenderer();
    StaticModelShader staticShader = new StaticModelShader();
    CrosshairManager crosshairManager = new CrosshairManager();
    DebugMenuManager debugMenuManager = new DebugMenuManager();
    TextMaster textMaster = new TextMaster();

    Light light = new Light(new Vector3f(0,0,0), new Vector3f(1,1,1));

    int isClickDown = 0;

    public static Camera camera = new Camera();

    private List<Entity> blocks = new ArrayList<>();

    RawModel model2 = OBJLoader.loadObjModel("lowPolyTree", loader);
    ModelTexture texture = new ModelTexture(loader.loadTexture("Sonny"));
    TexturedModel texturedModel2 = new TexturedModel(model2, texture);

    ModelTexture texture2 = new ModelTexture(loader.loadTexture("Image0"));
    TexturedModel texturedModel = new TexturedModel(model2, texture2);
    //Generates Entity
    Entity entity2 = new Entity(texturedModel, new Vector3f(0,0,0),0,0,0,1);

    List<Entity> allModels = new ArrayList<Entity>();
    Random random = new Random();

    public Player player = new Player(texturedModel2, new Vector3f(camera.getPositionX(), camera.getPositionY(), camera.getPositionZ()), 0, 0, 0, 1, camera);

    public void createArmyOfCubes(){
        for(int i = 0; i<3000; i++){
            float x = random.nextFloat() * 100 - 50;
            float y = random.nextFloat() * 100 - 50;
            float z = random.nextFloat() * -300;
            allModels.add(new Entity(texturedModel2, new Vector3f(x,y,z), random.nextFloat() * 180f, random.nextFloat() * 180f, 0f, 0.05f));
        }
    }

    public void checkInput(){
        if((glfwGetMouseButton(DisplayManager.window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS)) {
            masterRenderer.processEntity(new Entity(texturedModel2, new Vector3f((float) cursorTools.cursorX, (float) cursorTools.cursorY, (camera.getPositionZ() - 2f)), 0, 0, 0, 1));
            isClickDown = 1;
        }
        else if(glfwGetMouseButton(DisplayManager.window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_RELEASE && isClickDown == 1){
            blocks.add(new Entity(texturedModel2,new Vector3f((float)cursorTools.cursorX,(float)cursorTools.cursorY,(camera.getPositionZ()-2f)), 0,0,0,1));
            isClickDown = 0;
            System.out.println(isClickDown);
        }
    }

    public void init(){
        createArmyOfCubes();
        debugMenuManager.init();

        crosshairManager.init();

       textMaster.init();
    }

    public void update(){
        player.moveCursor();

        masterRenderer.render(light,camera);
        shader.start();
        staticShader.start();

        debugMenuManager.renderMenu();

        shader.loadLight(light);
        shader.loadViewMatrix(camera);
        masterRenderer.processEntity(entity2);

        for(Entity ranModel : allModels){
            masterRenderer.processEntity(ranModel);
            ranModel.increaseRotation(0.0f,0.8f,0.0f);
            ranModel.increasePosition(0f,0f,0.5f);
        }

        for(Entity block:blocks){
            masterRenderer.processEntity(block);
        }

        masterRenderer.processEntity(entity2);

        //masterRenderer.processEntity(crosshairEntity);

        checkInput();

        cursorTools.getCursorPosition(camera);

        crosshairManager.renderCrosshair();

        player.move();

        staticShader.stop();
        shader.stop();

    }
}
