package crosshair;

import entities.StaticEntity;
import org.lwjgl.opengl.GL11;
import staticRenderEngine.StaticModelShader;
import staticRenderEngine.StaticRenderer;

import java.util.ArrayList;
import java.util.List;

public class CrosshairManager {

    private List<StaticEntity> crosshairList = new ArrayList<>();

    StaticModelShader shader = new StaticModelShader();
    StaticRenderer renderer = new StaticRenderer(shader);

    Crosshair crosshair = new Crosshair(0.05f, 0.05f, 1);

    public void init() {
        StaticEntity crosshairEntity = crosshair.createEntity(crosshair);
        crosshairList.add(crosshairEntity);
    }

    public void renderCrosshair() {
        shader.start();
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        renderer.render(crosshairList.get(0));
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        shader.stop();
    }
}