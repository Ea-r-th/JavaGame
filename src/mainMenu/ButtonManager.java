package mainMenu;

import button.Button;
import button.ButtonTools;
import entities.Entity;
import tools.CursorTools;

import java.util.ArrayList;
import java.util.List;

import static mainMenu.MainMenuManager.camera;

public class ButtonManager {

    List<Entity> buttonEntities = new ArrayList<>();
    List<Button> buttons = new ArrayList<>();

    ButtonTools buttonTools = new ButtonTools();
    CursorTools cursorTools = new CursorTools();

    public void init(){
        buttonTools.createButton(buttons,1f,0.5f, -0.95f, 0.95f);
        buttonTools.createButton(buttons, 0.5f,1f, 0.45f, 0.95f);
        buttonTools.createButton(buttons, 0.5f,0.5f, 0.45f, -0.45f);

        for(Button button:buttons){
            buttonEntities.add(buttonTools.createButtonEntity(button));
        }

    }

    public void renderAllButtons() {
        for (Button button : buttons) {
            cursorTools.getCursorPosition(camera);

            buttonTools.updateButton(buttonEntities.get(buttons.indexOf(button)), button);
            buttonTools.detectHover(buttonEntities.get(buttons.indexOf(button)), button);
        }
    }
}