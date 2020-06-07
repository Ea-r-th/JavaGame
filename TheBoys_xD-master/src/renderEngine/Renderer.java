package renderEngine;

import gameState.GameState;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Renderer {

    public void renderModel(RawModel model){
        GL30.glBindVertexArray(model.getVaoID()); //Binds model VAOs
        GL20.glEnableVertexAttribArray(0); // Enables 0th attribute number
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0); //Renderers model
        GL20.glDisableVertexAttribArray(0); //Disables 0th attribute number
        GL30.glBindVertexArray(0); //Unbinds VAO
    }
}
