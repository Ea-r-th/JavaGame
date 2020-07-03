package button;

import Models.RawModel;
import Models.TexturedModel;
import entities.Entity;
import display.DisplayManager;
import mainMenu.MenuLoader;
import mainMenu.MenuRenderer;
import mainMenu.MenuShader;
import org.joml.Vector3f;
import textures.ModelTexture;
import tools.CursorTools;

import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class ButtonTools {

    MenuLoader loader = new MenuLoader();
    MenuShader shader = new MenuShader();
    MenuRenderer renderer = new MenuRenderer(shader);
    CursorTools cursorTools = new CursorTools();

    private ModelTexture onClickTexture = new ModelTexture(loader.loadTexture("Luca"));
    private ModelTexture defaultTexture = new ModelTexture(loader.loadTexture("sonny2"));

    public Entity createButtonEntity(Button button){ //Creates a renderable entity out of a button object

        float x = button.xPos();
        float y = button.yPos();
        float width = button.getWidth();
        float height = button.getHeight();

        float[] vertices = { //Sets vertices based on the width and height of the button object
                0,0,0,
                0, (-height), 0,
                (width), (-height), 0,
                (width), 0, 0
        };

        int[] indices = {
                0,1,3,3,1,2
        };

        float[] textureCoords = {
                0,0,
                0,1,
                1,1,
                1,0
        };

        RawModel model3 = loader.loadToVao(vertices, textureCoords, indices);
        ModelTexture texture2 = new ModelTexture(loader.loadTexture("Image0"));
        TexturedModel texturedModel = new TexturedModel(model3, texture2);

        return new Entity(texturedModel, new Vector3f(x,y,0),0,0,0,1);
    }

    public void updateButton(Entity buttonEntity, Button button){ //Renders the onClick button entity when the cursor is detected over a button
        if(detectHover(buttonEntity, button)){
            renderer.renderModel(buttonEntity, shader, onClickTexture);
        }
        else if(!detectHover(buttonEntity, button)){
            renderer.renderModel(buttonEntity, shader, defaultTexture);
        }
    }

    public boolean detectHover(Entity entity, Button button){ //Detects if cursor is hovering over a button
        float x = entity.getPosition().x;
        float y = entity.getPosition().y;
        float width = button.getWidth();
        float height = button.getHeight();
        if((cursorTools.cursorX > x) && (cursorTools.cursorX < (x + width)) && (cursorTools.cursorY > (y - height)) && (cursorTools.cursorY < y)){
            return true;
        }

        return false;
    }

    public void detectButtonClick(Entity entity, Button button){
        if((detectHover(entity, button) && glfwGetMouseButton(DisplayManager.window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS)){

        }
    }

    public void createButton(List<Button> buttonList, float width, float height, float x, float y){
        buttonList.add(new Button(width,height, x, y));
    }
}