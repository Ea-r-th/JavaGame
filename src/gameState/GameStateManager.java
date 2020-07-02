package gameState;

import display.DisplayManager;
import mainGame.MainGameManager;
import mainMenu.MainMenuManager;
import renderEngine.Loader;
import shaders.StaticShader;

import static gameState.GameStates.GAME;
import static org.lwjgl.glfw.GLFW.*;

public class GameStateManager {
    //Initializes game state
    public static GameStates state = GameStates.MAIN_MENU;
    public static StaticShader shader = new StaticShader();
    public static Loader loader = new Loader();

    MainMenuManager mainMenuManager = new MainMenuManager();
    MainGameManager mainGameManager = new MainGameManager();

    //creates variables for background color_buffer
    public static float stateR = 0.0f;
    public static float stateG = 0.0f;
    public static float stateB = 0.0f;
    public static float stateA = 0.0f;

    public void initGameStates(){
        mainGameManager.init();
        mainMenuManager.init();
    }

    //Sets functionality to switch game states
    public void renderGameState(){
        switch(state){
            case MAIN_MENU:
                //sets colors for game state
                stateR = 0.0f;
                stateG = 1f;
                stateB = 0.5f;
                stateA = 0.0f;

                mainMenuManager.update();

                break;
            case GAME:
                //sets colors for game state
                stateR = 0.0f;
                stateG = 1.0f;
                stateB = 1.0f;
                stateA = 0.0f;

                mainGameManager.update();
                break;
        }
    }

    public void cleanUp(){
        mainMenuManager.cleanUp();
    }

    //Takes input for switching game state
    public static void getStateInput(){
        switch(state){
            case GAME:
            if (glfwGetKey(DisplayManager.window, GLFW_KEY_BACKSPACE) == 1) {
                state = GameStates.MAIN_MENU;
                glfwSetInputMode(DisplayManager.window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
            }
            break;
            case MAIN_MENU:
            if (glfwGetKey(DisplayManager.window, GLFW_KEY_G) == 1) {
                state = GAME;
                glfwSetInputMode(DisplayManager.window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
            }
            break;
        }
    }
}