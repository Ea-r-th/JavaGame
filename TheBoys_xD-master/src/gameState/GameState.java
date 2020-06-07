package gameState;

import mainEngine.Display;
import mainEngine.MainGameLoop;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;

import static org.lwjgl.glfw.GLFW.*;

public class GameState {
    //Initializes game state
    public static GameStates state = GameStates.MAIN_MENU;

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

    //creates variables for background color_buffer
    public static float stateR = 0.0f;
    public static float stateG = 0.0f;
    public static float stateB = 0.0f;
    public static float stateA = 0.0f;

    //Sets functionality to switch game states
    public void renderGameState(Loader loader, Renderer renderer){
        switch(state){
            case MAIN_MENU:
                //sets colors for game state
                stateR = 1f;
                stateG = 0.0f;
                stateB = 0.0f;
                stateA = 0.0f;
                break;
            case GAME:
                //sets colors for game state
                stateR = 0.0f;
                stateG = 1.0f;
                stateB = 1.0f;
                stateA = 0.0f;
                RawModel model = loader.loadToVao(vertices, indices);
                renderer.renderModel(model);
                break;
        }
    }

    //Takes input for switching game state
    public static void getStateInput(){
        switch(state){
            case GAME:
            if (glfwGetKey(Display.window, GLFW_KEY_BACKSPACE) == 1) {
                state = GameStates.MAIN_MENU;
            }
            break;
            case MAIN_MENU:
            if (glfwGetKey(Display.window, GLFW_KEY_G) == 1) {
                state = GameStates.GAME;
            }
            break;
        }
    }
}
