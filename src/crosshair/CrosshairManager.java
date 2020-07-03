package crosshair;

import entities.StaticEntity;
import org.lwjgl.opengl.GL11;
import staticRenderEngine.StaticModelShader;
import staticRenderEngine.StaticRenderer;

public class CrosshairManager {

    StaticModelShader shader = new StaticModelShader();
    StaticRenderer renderer = new StaticRenderer(shader);

    Crosshair crosshair = new Crosshair(0.05f, 0.05f, 1);
    StaticEntity crosshairEntity = crosshair.createEntity(crosshair);

    public void init() {} //Don't need anything here yet

    public void renderCrosshair() {
        shader.start();
        GL11.glDisable(GL11.GL_DEPTH_TEST); //Disables the depth test so crosshair is not rendered over
        renderer.render(crosshairEntity); //Renders crosshair. The crosshair entity will likely be overhauled if player customization is added.
        GL11.glEnable(GL11.GL_DEPTH_TEST); //Re-enables depth test
        shader.stop();
    }
}