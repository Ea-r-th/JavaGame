package renderEngine;

import Models.TexturedModel;
import chunks.Chunk;
import chunks.ChunkRenderer;
import chunks.ChunkShader;
import display.DisplayManager;
import entities.Camera;
import entities.Entity;
import entities.Light;
import gameState.GameStateManager;
import openGLTools.OpenGLUtils;
import org.lwjgl.opengl.GL11;
import shaders.StaticShader;
import staticRenderEngine.StaticModelShader;
import staticRenderEngine.StaticRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class MasterRenderer {

    private Map<TexturedModel, List<Entity>> entities = new HashMap<>();
    private List<Chunk> chunkList = new ArrayList<>();

    private StaticShader shader = new StaticShader();
    private EntityRenderer entityRenderer;

    private ChunkRenderer chunkRenderer;
    private ChunkShader chunkShader = new ChunkShader();

    private StaticModelShader staticShader = new StaticModelShader();
    private StaticRenderer staticRenderer;

    static Camera camera = new Camera();

    public MasterRenderer(){
        OpenGLUtils.enableDepthTest(true);
        OpenGLUtils.enableBackCulling(true);
        camera.getProjectionMatrix();
        entityRenderer = new EntityRenderer(shader, camera.getProjectionMatrix());
        staticRenderer = new StaticRenderer(staticShader);
        chunkRenderer = new ChunkRenderer(chunkShader,camera.getProjectionMatrix());
    }

    public void processEntity(Entity entity){
        TexturedModel entityModel = entity.getModel();
        List<Entity> batch = entities.get(entityModel);
        if(batch!=null){
            batch.add(entity);
        }else{
            List<Entity> newBatch = new ArrayList<>();
            newBatch.add(entity);
            entities.put(entityModel, newBatch);
        }
    }

    public void processChunk(Chunk chunk){
        chunkList.add(chunk);
    }

    public void cleanUp(){
        shader.cleanUp();
        staticShader.cleanUp();
        chunkShader.cleanUp();
    }

    public void render(Light sun, Camera camera){
        shader.start();
        shader.loadLight(sun);
        shader.loadViewMatrix(camera);
        entityRenderer.renderModel(entities);
        shader.stop();
        entities.clear();
        chunkShader.start();
        chunkShader.loadLight(sun);
        chunkShader.loadViewMatrix(camera);
        chunkRenderer.render(chunkList);
        chunkShader.stop();
        chunkList.clear();
    }

    public void prepare() { //Called every frame, enables depth test and clears the color to the values defined in the gamestate manager
        OpenGLUtils.enableDepthTest(true);
        OpenGLUtils.enableBackCulling(true);
        GL11.glClearColor(GameStateManager.stateR, GameStateManager.stateG, GameStateManager.stateB, GameStateManager.stateA);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT| GL_DEPTH_BUFFER_BIT);
        if(glfwGetKey(DisplayManager.window, GLFW_KEY_F) == 1){
            GL11.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        }
        else if(glfwGetKey(DisplayManager.window, GLFW_KEY_P) == 1){
            GL11.glPolygonMode(GL_FRONT_AND_BACK, GL_POINT);
            GL11.glPointSize(10);
        }
        else{
            GL11.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        }
    }

    public static Camera getCamera() {
        return camera;
    }
}
