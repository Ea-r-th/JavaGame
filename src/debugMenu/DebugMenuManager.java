package debugMenu;

import display.DisplayManager;
import entities.Player;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.FontLoader;
import fontRendering.FontRenderer;
import fontRendering.TextMaster;
import mainGame.MainGameManager;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class DebugMenuManager {

    TextMaster textMaster = new TextMaster();
    FontLoader fontLoader = new FontLoader();
    int isDebugOpen = 0;

    FontRenderer fontRenderer = new FontRenderer();

    DecimalFormat dformatter = new DecimalFormat("0.000");

    private float textSize = 1f;

    FontType font = new FontType(fontLoader.loadTexture("franklin"), new File("res/fonts/franklin.fnt"));

    List<GUIText> allTexts = new ArrayList<>();

    public void init() {
        textMaster.init();
    }

    public void renderMenu() {
        toggleMenu();

        switch (isDebugOpen) {
            case 1:
                update();
                break;
            case 0:
                break;
        }
    }

    public void toggleMenu(){
        glfwSetKeyCallback(DisplayManager.window, new GLFWKeyCallbackI() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if(key == GLFW_KEY_F3 && action == GLFW_PRESS && isDebugOpen == 0){
                    isDebugOpen = 1;
                }
                else if(key == GLFW_KEY_F3 && action == GLFW_PRESS && isDebugOpen == 1){
                    isDebugOpen = 0;
                }
            }
        });
    }

    public void update(){
        createLine((dformatter.format(60/(1/DisplayManager.getFrameTimeSeconds()))), 0);
        createLine(("Position: " + (MainGameManager.camera.getPositionX()) + ", " + MainGameManager.camera.getPositionY()) + ", " + MainGameManager.camera.getPositionZ(), 1);
        createLine(("Angle: x:" + MainGameManager.camera.getYaw() + "Y: " + MainGameManager.camera.getPitch()), 2);
        createLine(("Player speed: " + Player.getCurrentXSpeed() + ", " + Player.getCurrentYSpeed() + ", " + Player.getCurrentZSpeed()), 3);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        render();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        cleanUp();
    }

    public void cleanUp(){
        for(GUIText text : allTexts){
            textMaster.removeText(text);
            allTexts = new ArrayList<>();
        }
    }

    public void render(){
      fontRenderer.renderTextList(allTexts,font);
    }

    private GUIText createLine(String text, float line){
        GUIText guiText;
        float adjustedLine = -((line * textSize)/15);
        guiText = new GUIText(text,textSize,font,new Vector2f(0, adjustedLine),2, false);
        guiText.setColor(1,1,1);
        allTexts.add(guiText);
        return guiText;
    }
}
