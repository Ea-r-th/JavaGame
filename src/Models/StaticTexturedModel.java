package Models;

import textures.ModelTexture;

public class StaticTexturedModel {
        private RawStaticModel model;
        private ModelTexture texture;

    public StaticTexturedModel(RawStaticModel model, ModelTexture texture)
        {
            this.model = model;
            this.texture = texture;
        }

        public RawStaticModel getRawModel() {
        return model;
    }

        public void setRawModel(RawStaticModel model) {
        this.model = model;
    }

        public ModelTexture getTexture() {
        return texture;
    }

        public void setTexture(ModelTexture texture) {
        this.texture = texture;
    }
}
