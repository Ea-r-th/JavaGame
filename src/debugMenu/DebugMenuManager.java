package debugMenu;

import button.ButtonTools;
import display.DisplayManager;
import entities.Player;
import entities.StaticEntity;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.FontLoader;
import fontRendering.FontRenderer;
import fontRendering.TextMaster;
import mainGame.MainGameManager;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;
import staticRenderEngine.StaticModelShader;
import staticRenderEngine.StaticRenderer;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DebugMenuManager {

    TextMaster textMaster = new TextMaster();
    FontLoader fontLoader = new FontLoader();
    StaticModelShader staticModelShader= new StaticModelShader();
    StaticRenderer renderer = new StaticRenderer(staticModelShader);

    FontRenderer fontRenderer = new FontRenderer();

    DecimalFormat dformatter = new DecimalFormat("0.000");

    private float textSize = 1f;

    FontType font = new FontType(fontLoader.loadTexture("franklin"), new File("res/fonts/franklin.fnt")); //Sets the font, fileName and the directory need to be changed to use different fonts

    List<GUIText> allTexts = new ArrayList<>();
    List<StaticEntity> backEntities = new ArrayList<>();

    public void init() {
        textMaster.init();
    }

    public void renderMenu() {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        update();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public void update(){ //This is called every frame
        createLine((dformatter.format(60/(1/DisplayManager.getFrameTimeSeconds()))), 0);
        createLine(("Position: " + (MainGameManager.camera.getPositionX()) + ", " + MainGameManager.camera.getPositionY()) + ", " + MainGameManager.camera.getPositionZ(), 1);
        createLine(("Angle: x:" + MainGameManager.camera.getYaw() + "Y: " + MainGameManager.camera.getPitch()), 2);
        createLine(("Player speed: " + Player.getCurrentXSpeed() + ", " + Player.getCurrentYSpeed() + ", " + Player.getCurrentZSpeed()), 3);
        GL11.glDisable(GL11.GL_DEPTH_TEST); //Disables depth test so the text is always rendered regardless of what's in front of it
        render();
        GL11.glEnable(GL11.GL_DEPTH_TEST); //Re-enables the depth test after rendering
        cleanUp();
    }

    public void cleanUp(){ //Loops through all text in GUIText list so new updated values can be rendered.
        for(GUIText text : allTexts){
            textMaster.removeText(text);
            allTexts = new ArrayList<>();
        }
    }

    public void render(){
      fontRenderer.renderTextList(allTexts,font);
    } //Loops through a list to render all buttons

    /**
     * Creats a renderable GUIText in the debug menu
     * @param text - What you want to be displayed as text
     * @param line - The line coming down from the top, every new text you render should have a line value of 1 more than the last
     * @return a new GUIText added to the list to be rendered.
     */

    private GUIText createLine(String text, float line){
        GUIText guiText;
        float adjustedLine = -((line * textSize)/15);
        guiText = new GUIText(text,textSize,font,new Vector2f(0, adjustedLine),2, false);
        guiText.setColor(1,1,1);
        allTexts.add(guiText);
        return guiText;
    }
}
