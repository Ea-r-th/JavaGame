package tools;

import entities.Camera;
import display.DisplayManager;
import org.lwjgl.BufferUtils;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;

public class CursorTools {

    public static double cursorX;
    public static double cursorY;

    public static void getCursorPosition(Camera camera){ //Gets the cursor position with center 0,0, and with a window width and height of 2
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        glfwGetWindowSize(DisplayManager.window, w, h);
        double width = w.get(0);
        double height = h.get(0);
        DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer y = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(DisplayManager.window,x,y);
        cursorX = (((x.get(0)/(width/2)))-(1 - camera.getPositionX()));
        cursorY  = (-(y.get(0)/(height/2))+(1 + camera.getPositionY()));
    }
}
