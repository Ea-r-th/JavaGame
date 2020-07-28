package tools;

import Models.RawStaticModel;
import Models.StaticTexturedModel;

import entities.StaticEntity;
import staticRenderEngine.StaticLoader;
import textures.ModelTexture;

public class QuadGenerator {

    static StaticLoader loader = new StaticLoader();

    public static StaticEntity createQuad(float width, float height, float x, float y, String texture){ //Creates a renderable entity out of a button object

        float[] vertices = { //Sets vertices based on the width and height of the button object
                x,y,0,
                x, (y-height), 0,
                (x + width), (y - height), 0,
                (x + width), y, 0
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

        RawStaticModel model3 = loader.loadToVao(vertices, indices, textureCoords);
        ModelTexture texture2 = new ModelTexture(loader.loadTexture(texture));
        StaticTexturedModel texturedModel = new StaticTexturedModel(model3, texture2);

        return new StaticEntity(texturedModel, 0,0,0,1);
    }
}
