package textures;

public class ModelTexture
{
    private int textureID;

    public ModelTexture(int id)
    {
        this.textureID = id;
    }

    public int getID()
    {
        return this.textureID;
    }

    public void setTextureID(int textureID) {
        this.textureID = textureID;
    }
}
