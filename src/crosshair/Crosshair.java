package crosshair;

import Models.RawStaticModel;
import Models.StaticTexturedModel;
import entities.StaticEntity;
import mainGame.MainGameManager;
import renderEngine.Loader;
import staticRenderEngine.StaticLoader;
import textures.ModelTexture;

public class Crosshair {

    StaticLoader loader = new StaticLoader();

    private float width;
    private float height;
    private StaticTexturedModel model;
    private float scale;
    private float aspectRatio = 1.7777778f;

    public Crosshair(float width, float height, float scale){
        this.width = width;
        this.height = height;
        this.scale = scale;
    }

    /**
     * Creates a crosshair entity
     * @param crosshair - Takes in a crosshair object
     * @return - returns a new renderable static entity from crosshair values
     */

    public StaticEntity createEntity(Crosshair crosshair){
        float positions[] = {
                (-(crosshair.width) / aspectRatio), crosshair.height, 0,
                (-(crosshair.width)/aspectRatio), -(crosshair.height), 0,
                (crosshair.width /aspectRatio), -(crosshair.height), 0,
                (crosshair.width / aspectRatio), crosshair.height, 0
        };

        int[] indices = {
                0,1,3,3,1,2
        };

        float[] textureCoords = {
                0,0,
                0,1,
                1,1,
                1,0
        };

       RawStaticModel model = loader.loadToVao(positions,indices,textureCoords);
       ModelTexture texture = new ModelTexture(loader.loadTexture("crosshair"));
       StaticTexturedModel crosshairModel = new StaticTexturedModel(model,texture);

       return new StaticEntity(crosshairModel, MainGameManager.camera.getPositionX(), MainGameManager.camera.getPositionY(),MainGameManager.camera.getPositionZ(), crosshair.scale);
    }

    //Just getters n setters

    public float getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public StaticTexturedModel getModel() {
        return model;
    }

    public void setModel(StaticTexturedModel model) {
        this.model = model;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
