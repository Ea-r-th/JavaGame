package entities;

import Models.StaticTexturedModel;

public class StaticEntity {
    private StaticTexturedModel model;
    private float rotX, rotY, rotZ;
    private float scale;

    public StaticEntity(StaticTexturedModel model, float rotX, float rotY, float rotZ, float scale) { //Similar to an entity, but is used in the static renderer to render static models.
        this.model = model;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }

    public void increaseRotation(float dx, float dy, float dz)
    {
        this.rotX += dx;
        this.rotY += dy;
        this.rotZ += dz;
    }

    public StaticTexturedModel getModel() {
        return model;
    }

    public void setModel(StaticTexturedModel model) {
        this.model = model;
    }

    public float getRotX() {
        return rotX;
    }

    public void setRotX(float rotX) {
        this.rotX = rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public void setRotY(float rotY) {
        this.rotY = rotY;
    }

    public float getRotZ() {
        return rotZ;
    }

    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
