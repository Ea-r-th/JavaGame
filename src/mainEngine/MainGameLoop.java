package mainEngine;

import display.DisplayManager;
import fontRendering.TextMaster;
import gameState.GameStateManager;
import org.lwjgl.*;
import org.lwjgl.opengl.*;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import staticRenderEngine.StaticLoader;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class MainGameLoop { //No touchy touchy this class
    public static DisplayManager display = new DisplayManager();
    // The window handle

    public static void main(String[] args) {
        System.out.println(Version.getVersion());

        DisplayManager.init();

        // This line checks the context and capabilities
        GL.createCapabilities();

        Loader loader = new Loader();
        StaticLoader staticLoader = new StaticLoader();
        MasterRenderer masterRenderer = new MasterRenderer();

        GameStateManager gameState = new GameStateManager();
        gameState.initGameStates();
        // Set the clear color initially
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);


        //THIS
        //IS
        //THE
        //MAIN
        //GAME
        //LOGIC
        //---------------------------------------------------------------------------------

        while (!glfwWindowShouldClose(DisplayManager.window) ) { //Called every frame
            masterRenderer.prepare();

            display.resizeDisplay();
            display.updateDisplay(); //Something for time idk

            //renderer.renderModel(model);
            gameState.getStateInput(); //Logic for switching gamesates
            gameState.renderGameState(); //Renders everything in seperate game states

            //Everything below this line needs to stay at the end of the loop
            glfwSwapBuffers(DisplayManager.window); // swap the color buffers

            glfwPollEvents(); //updates display
        }

        //End of main loop -----------------------------------------------------------------

        // Free the window callbacks and destroy the window
        TextMaster.cleanUp();
        gameState.shader.cleanUp();
        loader.cleanUp();
        staticLoader.cleanUp();
        gameState.cleanUp();
        glfwFreeCallbacks(DisplayManager.window);
        glfwDestroyWindow(DisplayManager.window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
    // --------------------------------------------------------------------------------------------
}