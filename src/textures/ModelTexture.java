package textures;

import org.joml.Vector2f;

public class ModelTexture
{
    private int textureID;

    private float shineDamper = 1;
    private float reflectivity = 0;
    private Vector2f lightBias = new Vector2f(1f,1f);

    public ModelTexture(int texture)
    {
        this.textureID = texture;
    } //Takes in a texture

    public Vector2f getLightBias() {
        return lightBias;
    }

    public int getID()
    {
        return this.textureID;
    }

    public int getTextureID() {
        return textureID;
    }

    public void setTextureID(int textureID) {
        this.textureID = textureID;
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }
}
