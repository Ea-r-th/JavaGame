package mainMenu;

import display.DisplayManager;
import entities.Camera;
import entities.StaticEntity;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.FontLoader;
import fontRendering.TextMaster;
import org.joml.Vector2f;
import staticRenderEngine.StaticModelShader;
import staticRenderEngine.StaticRenderer;
import tools.QuadGenerator;

import java.io.File;

public class MainMenuManager {

    ButtonManager buttonManager = new ButtonManager();

    TextMaster textMaster = new TextMaster();
    FontLoader fontLoader = new FontLoader();
    StaticModelShader staticShader = new StaticModelShader();
    StaticRenderer staticRenderer = new StaticRenderer(staticShader);

    public static Camera camera = new Camera();

    MenuShader menuShader = new MenuShader();

    public void init(){

        textMaster.init();

        buttonManager.init();

    }

    FontType font = new FontType(fontLoader.loadTexture("arial"), new File("res/fonts/arial.fnt"));
    StaticEntity test = QuadGenerator.createQuad(2,2,-1,1,"TestThing");

    public void update(){//Same thing as the mainGameManager, just ask Luca if you have questions

        GUIText text = new GUIText((String.valueOf((DisplayManager.getFrameTimeSeconds() * 1000))),1,font,new Vector2f(1, 0),10, false);
        GUIText text2 = new GUIText("When the", 5, font, new Vector2f(0.5f, -1f), 1, false);

        text.setColor(1,1,1);
        text2.setColor(1,0,0);


        menuShader.start();
        staticShader.start();

        menuShader.loadViewMatrix(camera);

        //*****************Rendering Starts here **********************

        staticRenderer.render(test);

        buttonManager.renderAllButtons();

        textMaster.render();

        staticShader.stop();
        menuShader.stop();

        textMaster.removeText(text);
        textMaster.removeText(text2);

    }

    public void cleanUp(){
        fontLoader.cleanUp();
    }
}
