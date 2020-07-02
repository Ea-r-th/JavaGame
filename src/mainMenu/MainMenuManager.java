package mainMenu;

import display.DisplayManager;
import entities.Camera;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.FontLoader;
import fontRendering.TextMaster;
import org.joml.Vector2f;

import java.io.File;

public class MainMenuManager {

    ButtonManager buttonManager = new ButtonManager();

    TextMaster textMaster = new TextMaster();
    FontLoader fontLoader = new FontLoader();

    public static Camera camera = new Camera();

    MenuShader menuShader = new MenuShader();

    public void init(){

        textMaster.init();

        buttonManager.init();

    }

    FontType font = new FontType(fontLoader.loadTexture("arial"), new File("res/fonts/arial.fnt"));

    public void update(){

        GUIText text = new GUIText((String.valueOf((DisplayManager.getFrameTimeSeconds() * 1000))),1,font,new Vector2f(1, 0),10, false);
        GUIText text2 = new GUIText("When the", 5, font, new Vector2f(0.5f, -1f), 1, false);

        text.setColor(1,1,1);
        text2.setColor(1,0,0);


        menuShader.start();

        menuShader.loadViewMatrix(camera);

        //*****************Rendering Starts here **********************

        buttonManager.renderAllButtons();

        textMaster.render();

        menuShader.stop();

        textMaster.removeText(text);
        textMaster.removeText(text2);

    }

    public void cleanUp(){
        fontLoader.cleanUp();
    }
}
