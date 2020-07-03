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

    /**
     * Magic that creates a renderable entity out of a button
     * @param button - Button to be turned into an entity
     * @return new entity(THIS CAN BE MOST LIKELY CHANGED TO STATIC ENTITY IF TEXTUREDMODEL IS ADDED AS A PARAM, SOMEONE PLEEEAAASSEE DO THIS!!!!!) We don't need a
     * separate button and static renderer! It's a waste of code and space!!!!!!
     */

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

    /**
     * Changes the texture of the button when colliding with the mouse
     * @param buttonEntity - The entity made from the button
     * @param button - the button (Stupid way of doing this, if someone wants to make a getButton method to get the button from an entity be my guest)
     */

    public void updateButton(Entity buttonEntity, Button button){ //Renders the onClick button entity when the cursor is detected over a button
        if(detectHover(buttonEntity, button)){
            renderer.renderModel(buttonEntity, shader, onClickTexture);
        }
        else if(!detectHover(buttonEntity, button)){
            renderer.renderModel(buttonEntity, shader, defaultTexture);
        }
    }

    /**
     * Returns true when the mouse cursor is colliding with a button entity
     * @param entity - The button you are testing for collision with
     * @param button - the button the entity is made from (This is probably a really remedial way to do this but too bad!)
     * @return True when colliding, false when not.
     */

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

    /**
     * Creates a button and adds it to a provided button list to be rendered
     * @param buttonList - The list the button is added to, which will be looped through for rendering in whatever master class you're using
     * @param width - Width of the button in screen coordinates
     * @param height - Height of the button in screen coordinates
     * @param x - x value of the top left of the button
     * @param y - y value of the top left of the button
     */

    public void createButton(List<Button> buttonList, float width, float height, float x, float y){
        buttonList.add(new Button(width,height, x, y));
    }
}