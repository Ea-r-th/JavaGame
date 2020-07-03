package display;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.glViewport;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class DisplayManager { //You guys most likely will never need to mess with this class
    //creates a new window
    public static long window;
    public static int width = 960;
    public static int height = 640;

    private static long lastFrameTime;
    private static float delta;

    public static Display display = new Display(width,height,"Game indev");

    public static int centerX = width;
    public static int centerY = height;

    //Initializes the window
    public static void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        // Create the window
        window = glfwCreateWindow(display.width, display.height, display.title, NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        glfwGetWindowSize(window, w, h);
        width = w.get(0);
        height = h.get(0);

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
        lastFrameTime = getCurrentTime();
    }

    public static void updateDisplay()
    {
        //Returns the amount of time that it took to generate the last frame
        long currentFrameTime = getCurrentTime();
        delta = (currentFrameTime - lastFrameTime);
        lastFrameTime = currentFrameTime;
    }

    public static float getFrameTimeSeconds()
    {
        return delta;
    }

    public void resizeDisplay(){
        glfwSetWindowSizeCallback(window, new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int argWidth, int argHeight) {
                resizeWindow(argWidth, argHeight);
                glfwSetWindowAspectRatio(window, 1920, 1080);
            }
        });
    }
    private void resizeWindow(int argWidth, int argHeight) {
        glViewport(0, 0, argWidth,argHeight);
        centerX = argWidth;
        centerY = argHeight;

        //  adjustProjectionMatrix(width, height); // recalculating projection matrix (only if you are using one)
    }

    private static long getCurrentTime()
    {
        return System.nanoTime()/1000000;
    }
}