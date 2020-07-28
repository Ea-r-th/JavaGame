package entities;

import mainEngine.MainGameLoop;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

    private Matrix4f projectionMatrix;

    public static float FOV = 70;
    public static float NEAR_PLANE = 0.1f;
    public static float FAR_PLANE = 2000;

    private Vector3f position = new Vector3f(0f,0f,0f);
    private float pitch;
    private float yaw;
    private float roll;

    public Camera(){
        this.projectionMatrix = createProjectionMatrix();
    }

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

    private Matrix4f createProjectionMatrix() { //LITERALLY 0 GAME DEVELOPERS IN THE WORLD KNOW HOW THE HELL THIS WORKS DO NOT TOUCH IT FOR THE LOVE OF GOD
        Matrix4f projectionMatrix;
        float aspectRatio = (float) MainGameLoop.display.width / (float) MainGameLoop.display.height;
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV/2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix._m00(x_scale);
        projectionMatrix._m11(y_scale);
        projectionMatrix._m22(-((FAR_PLANE + NEAR_PLANE) / frustum_length));
        projectionMatrix._m23(-1);
        projectionMatrix._m32(-((2 * NEAR_PLANE * FAR_PLANE) / frustum_length));
        projectionMatrix._m33(0);
        return projectionMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
}