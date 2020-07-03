package entities;

import Models.TexturedModel;
import display.DisplayManager;
import mainGame.MainGameManager;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWCursorPosCallback;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends Entity { //This class is gonna need to be heavily touched up soon enough.

    //TODO: Player ramp up and ramp down speed, implemented speed blocks, updated player model, proper speed resets(So you cant just hold another key to not get to the else statement)

    public float RUN_SPEED = 0.002f;
    public float sprintSpeed = 10;

    float sensitivity = 0.12f;
    boolean firstMouse = true;

    private static final float TURN_SPEED = 160;

    private static float currentXSpeed;
    private static float currentZSpeed;
    private static float currentYSpeed = 0;
    private float currentTurnSpeed = 0;

    private float xBlock;
    private float yBlock = 0.04f;
    private float zBlock = 0.04f;

    /**
     * Creates a new player object that can be rendered
     * @param model - TexturedModel that will be the player model
     * @param position - Position in the world
     * @param rotX - X rotation
     * @param rotY - Y rotation
     * @param rotZ - Z rotation
     * @param scale - Player size
     * @param camera - Camera player uses
     */

    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, Camera camera) {
        super(model, position, rotX, rotY, rotZ, scale);
        camera.setPosition(position);
    }

    public void move() { //Called every frame, moves the player
        checkInputs();
        super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
        float distanceX = -currentZSpeed * DisplayManager.getFrameTimeSeconds();
        float distanceY  = currentYSpeed;
        float distanceZ = currentZSpeed * DisplayManager.getFrameTimeSeconds();
        float dx1 = (float) (distanceX * Math.sin(Math.toRadians((MainGameManager.camera.getYaw()))));
        float dy =  (distanceY);
        float dz1 = (float) (distanceZ * Math.cos(Math.toRadians((MainGameManager.camera.getYaw()))));
        super.increasePosition(dx1, dy, dz1);

        float distanceX2 = currentXSpeed * DisplayManager.getFrameTimeSeconds();
        float distanceZ2 = currentXSpeed * DisplayManager.getFrameTimeSeconds();
        float dx2 = (float) (distanceX2 * Math.cos(Math.toRadians((MainGameManager.camera.getYaw()))));
        float dz2 = (float) (distanceZ2 * Math.sin(Math.toRadians((MainGameManager.camera.getYaw()))));
        super.increasePosition(dx2, dy, dz2);
    }

    private void checkInputs(){ //Yanderedev code that moves the player given keybaord inputs, this needs to be reworked
        if(glfwGetKey(DisplayManager.window, GLFW_KEY_W) == 1 && glfwGetKey(DisplayManager.window, GLFW_KEY_S) != 1){
            currentZSpeed -= 0.02;
        }
        else if(glfwGetKey(DisplayManager.window, GLFW_KEY_S) == 1 && glfwGetKey(DisplayManager.window, GLFW_KEY_W) != 1){
            currentZSpeed += 0.02;
        }
        else if(glfwGetKey(DisplayManager.window, GLFW_KEY_A) == 1 && glfwGetKey(DisplayManager.window, GLFW_KEY_D) != 1){
            currentXSpeed -= 0.02;
        }
        else if(glfwGetKey(DisplayManager.window, GLFW_KEY_D) == 1 && glfwGetKey(DisplayManager.window, GLFW_KEY_A) != 1){
            currentXSpeed += 0.02;
        }
        else if(glfwGetKey(DisplayManager.window, GLFW_KEY_SPACE) == 1 && glfwGetKey(DisplayManager.window, GLFW_KEY_LEFT_SHIFT) != 1){
            currentYSpeed += 0.02;
        }
        else if(glfwGetKey(DisplayManager.window, GLFW_KEY_LEFT_SHIFT) == 1 && glfwGetKey(DisplayManager.window, GLFW_KEY_SPACE) != 1){
            currentYSpeed -= 0.02;
        }

        else{ //This needs to be reworked, example: if the w key is held and then the d key is pressed before the w key is released, this else statement won't be called, and the z speed will be constant.
            currentZSpeed = 0;
            currentXSpeed = 0;
            currentYSpeed = 0;
        }
    }

    public void moveCursor(){ //Sets a cursor callback, I have no clue what the hell this does but it works so don't touch it
        glfwSetCursorPosCallback(DisplayManager.window, new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
            setCameraPosition(xpos,ypos, MainGameManager.camera);
            }
        });
    }

    public void setCameraPosition(double xPos, double yPos, Camera camera){ //Called in the callback, this is gonna have to change because of the camera bug when you look too far up

        if (firstMouse)
        {
            DisplayManager.centerX = (int) xPos;
            DisplayManager.centerY = (int) yPos;
            firstMouse = false;
        }

        float xoffset = (float) (xPos - DisplayManager.centerX);
        float yoffset = (float) (DisplayManager.centerY - yPos); // reversed since y-coordinates range from bottom to top
        DisplayManager.centerX = (int) xPos;
        DisplayManager.centerY = (int) yPos;

        xoffset *= sensitivity;
        yoffset *= sensitivity;

        if(camera.getPitch() >= 90.0f)
            camera.setPitch(90.0f);
        if(camera.getPitch() <= -90.0f)
            camera.setPitch(-90.0f);

        camera.setYaw(camera.getYaw() + xoffset);
        camera.setPitch(camera.getPitch() - yoffset);
    }

    public float getRUN_SPEED() {
        return RUN_SPEED;
    }

    public static float getCurrentXSpeed() {
        return currentXSpeed;
    }

    public static float getCurrentZSpeed() {
        return currentZSpeed;
    }

    public static float getCurrentYSpeed() {
        return currentYSpeed;
    }
}
