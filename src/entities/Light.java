package entities;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Light
{

    private Vector3f position;
    private Vector3f color;
    private Vector2f lightBias;

    public Light(Vector2f lightBias, Vector3f position, Vector3f color) { //Creates a light object
        this.position = position;
        this.color = color;
        this.lightBias = lightBias;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector2f getLightBias() {
        return lightBias;
    }

    public Vector3f getColor() {
        return color;
    }
}
