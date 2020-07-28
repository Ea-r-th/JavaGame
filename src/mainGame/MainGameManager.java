package mainGame;

import Models.RawModel;
import Models.TexturedModel;
import chunks.Chunk;
import crosshair.CrosshairManager;
import debugMenu.DebugMenuManager;
import entities.*;
import fontRendering.TextMaster;
import org.joml.Vector2f;
import org.joml.Vector3f;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import shaders.StaticShader;
import staticRenderEngine.StaticModelShader;
import textures.ModelTexture;
import tools.CursorTools;

public class MainGameManager {

    public StaticShader shader = new StaticShader();

    Loader loader = new Loader();
    MasterRenderer masterRenderer = new MasterRenderer();
    StaticModelShader staticShader = new StaticModelShader();
    CrosshairManager crosshairManager = new CrosshairManager();
    DebugMenuManager debugMenuManager = new DebugMenuManager();
    TextMaster textMaster = new TextMaster();

    Light light = new Light(new Vector2f(1,1), new Vector3f(0,0,0), new Vector3f(1,1,1));

    public static boolean shouldDebugOpen = false;

    public static Camera camera = new Camera();

    RawModel model2 = OBJLoader.loadObjModel("lowPolyTree", loader);
    ModelTexture texture = new ModelTexture(loader.loadTexture("Sonny"));
    TexturedModel texturedModel2 = new TexturedModel(model2, texture);

    TexturedModel texturedModel = new TexturedModel(model2, new ModelTexture(loader.loadTexture("Image0")));

    Chunk chunk = Chunk.createChunk(32);

    Entity entity2 = new Entity(texturedModel, new Vector3f(-100,0,0),0,0,0,1);

    public Player player = new Player(texturedModel2, new Vector3f(camera.getPositionX(), camera.getPositionY(), camera.getPositionZ()), 0, 0, 0, 1, camera);

    public void renderDebugMenu(){
        if(shouldDebugOpen == true){
            debugMenuManager.renderMenu();
        }
    }

    public void init(){
        debugMenuManager.init();

        crosshairManager.init();

        textMaster.init();

        ModelTexture entityTexture = entity2.getModel().getTexture();
        entityTexture.setReflectivity(1);
        entityTexture.setShineDamper(10);
    }

    public void update(){
        player.moveCursor();

        masterRenderer.render(light,camera);
        shader.start();
        staticShader.start();

        shader.loadLight(light);
        shader.loadViewMatrix(camera);

        masterRenderer.processEntity(entity2);
        masterRenderer.processChunk(chunk);

        masterRenderer.processEntity(entity2);

        CursorTools.getCursorPosition(camera);

        crosshairManager.renderCrosshair();
        renderDebugMenu();

        player.move();

        staticShader.stop();
        shader.stop();
    }
}
