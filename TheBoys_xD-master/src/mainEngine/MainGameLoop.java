package mainEngine;

import gameState.GameState;
import org.lwjgl.*;
import org.lwjgl.opengl.*;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class MainGameLoop {

    // The window handle

    public static void main(String[] args) {
        System.out.println(Version.getVersion());

        Display.init();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        GameState gameState = new GameState();
        // This line checks the context and capabilities
        GL.createCapabilities();
        float[] vertices = {
                -0.5f, 0.5f, 0f,//v0
                -0.5f, -0.5f, 0f,//v1
                0.5f, -0.5f, 0f,//v2
                0.5f, 0.5f, 0f,//v3
        };

        int[] indices = {
                0,1,3,//top left triangle (v0, v1, v3)
                3,1,2//bottom right triangle (v3, v1, v2)
        };
        RawModel model = loader.loadToVao(vertices, indices);

        // Set the clear color initially
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        /*
        THIS
        IS
        THE
        MAIN
        GAME
        LOGIC
        ---------------------------------------------------------------------------------
         */
        while ( !glfwWindowShouldClose(Display.window) ) {
            GL11.glClearColor(GameState.stateR, GameState.stateG, GameState.stateB, GameState.stateA);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            renderer.renderModel(model);

            glfwSwapBuffers(Display.window); // swap the color buffers


            gameState.getStateInput();
            gameState.renderGameState(loader, renderer);
            glfwPollEvents();

        }

        //End of main loop -----------------------------------------------------------------

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(Display.window);
        glfwDestroyWindow(Display.window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
    // --------------------------------------------------------------------------------------------
}