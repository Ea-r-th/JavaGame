package renderEngine;

import Models.TexturedModel;
import entities.Camera;
import entities.Entity;
import entities.Light;
import gameState.GameStateManager;
import mainEngine.MainGameLoop;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import shaders.StaticShader;
import staticRenderEngine.StaticModelShader;
import staticRenderEngine.StaticRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;

public class MasterRenderer {

    public static float FOV = 70;
    public static float NEAR_PLANE = 0.1f;
    public static float FAR_PLANE = 1000;

    private Matrix4f projectionMatrix;

    private Map<TexturedModel, List<Entity>> entities = new HashMap<>();

    private StaticShader shader = new StaticShader();
    private EntityRenderer entityRenderer;

    private StaticModelShader staticShader = new StaticModelShader();
    private StaticRenderer staticRenderer;

    public MasterRenderer(){
        GL11.glEnable(GL11.GL_CULL_FACE); //Gets rid of unseen faces
        GL11.glCullFace(GL11.GL_BACK);
        createProjectionMatrix();
        entityRenderer = new EntityRenderer(shader, projectionMatrix);
        staticRenderer = new StaticRenderer(staticShader);
    }

    public void processEntity(Entity entity){
        TexturedModel entityModel = entity.getModel();
        List<Entity> batch = entities.get(entityModel);
        if(batch!=null){
            batch.add(entity);
        }else{
            List<Entity> newBatch = new ArrayList<Entity>();
            newBatch.add(entity);
            entities.put(entityModel, newBatch);
        }
    }

    public void cleanUp(){
        shader.cleanUp();
        staticShader.cleanUp();
    }

    public void render(Light sun, Camera camera){
        shader.start();
        shader.loadLight(sun);
        shader.loadViewMatrix(camera);
        entityRenderer.renderModel(entities);
        shader.stop();
        entities.clear();
    }

    private void createProjectionMatrix()
    {
        float aspectRatio = (float) MainGameLoop.display.width / (float) MainGameLoop.display.height;
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV/2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix._m00(x_scale);
        projectionMatrix._m11(y_scale);
        projectionMatrix._m22(-((FAR_PLANE + NEAR_PLANE) / frustum_length));
        projectionMatrix._m23(-1);
        projectionMatrix._m32(-((2 * NEAR_PLANE * FAR_PLANE) / frustum_length));
        projectionMatrix._m33(0);
    }

    public void prepare() {
        GL11.glEnable(GL_DEPTH_TEST);
        GL11.glClearColor(GameStateManager.stateR, GameStateManager.stateG, GameStateManager.stateB, GameStateManager.stateA);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT| GL_DEPTH_BUFFER_BIT);
    }
}
