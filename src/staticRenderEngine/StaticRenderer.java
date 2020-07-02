package staticRenderEngine;

import Models.RawStaticModel;
import Models.StaticTexturedModel;
import entities.StaticEntity;;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;


public class StaticRenderer {

    public StaticRenderer(StaticModelShader shader) {
            shader.start();
            shader.stop();
    }
    public void render(StaticEntity entity){
        StaticTexturedModel texturedModel = entity.getModel();
        RawStaticModel model = texturedModel.getRawModel();
        GL30.glBindVertexArray(model.getVaoID()); //Binds model VAOs
        GL20.glEnableVertexAttribArray(0); // Enables 0th attribute number
        GL20.glEnableVertexAttribArray(1); // Enables 1st attribute number
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getID());
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0); //Renderers model
        GL20.glDisableVertexAttribArray(0); //Disables 0th attribute number
        GL20.glDisableVertexAttribArray(1); //Disables 1st attribute number
        GL30.glBindVertexArray(0); //Unbinds VAO
    }
}
