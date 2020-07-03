package entities;

import org.joml.Vector3f;

public class Camera {
    private Vector3f position = new Vector3f(0f,0f,0f);
    private float pitch;
    private float yaw;
    private float roll;

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }


    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }

    public float getPositionX(){return position.x;}
    public float getPositionY(){return position.y;}
    public float getPositionZ(){return position.z;}

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }
}