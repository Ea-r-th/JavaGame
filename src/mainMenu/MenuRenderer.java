package mainMenu;

import Models.RawModel;
import Models.TexturedModel;
import entities.Entity;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import textures.ModelTexture;
import tools.MathProgram;

public class MenuRenderer {

    public MenuRenderer(MenuShader shader)
    {
        shader.start();
        shader.stop();
    }

    public void renderModel(Entity entity, MenuShader shader, ModelTexture texture){
        TexturedModel texturedModel = entity.getModel();
        RawModel model = texturedModel.getRawModel();
        GL30.glBindVertexArray(model.getVaoID()); //Binds model VAOs
        GL20.glEnableVertexAttribArray(0); // Enables 0th attribute number
        GL20.glEnableVertexAttribArray(1); // Enables 1st attribute number
        Matrix4f transformationMatrix = MathProgram.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY()
                , entity.getRotZ(), entity.getScale());
        shader.loadMenuTranformatrionMatrix(transformationMatrix);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0); //Renderers model
        GL20.glDisableVertexAttribArray(0); //Disables 0th attribute number
        GL20.glDisableVertexAttribArray(1); //Disables 1st attribute number
        GL30.glBindVertexArray(0); //Unbinds VAO
    }
}
