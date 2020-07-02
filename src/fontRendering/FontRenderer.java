package fontRendering;

import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.List;
import java.util.Map;

public class FontRenderer {

	private FontShader shader;

	public FontRenderer() {
		shader = new FontShader();
	}

	public void render(Map<FontType, List<GUIText>> texts){
		prepare();
		for(FontType font : texts.keySet()){
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, font.getTextureAtlas());
			for(GUIText text : texts.get(font)){
				renderText(text,font);
			}
		}
		stop();
	}

	public void renderTextList(List<GUIText> texts, FontType font){
		prepare();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, font.getTextureAtlas());
		for(GUIText text: texts){
			renderText(text,font);
		}
		stop();
	}

	public void cleanUp(){
		shader.cleanUp();
	}
	
	private void prepare(){
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		shader.start();
	}
	
	public void renderText(GUIText text, FontType font){
		GL30.glBindVertexArray(text.getMesh());
		GL20.glEnableVertexAttribArray(0); //Enables 0th attribute number
		GL20.glEnableVertexAttribArray(1); //Enables attribute number 1
		shader.loadColor(text.getColor()); //Loads the text color to the uniform variable
		shader.loadTranslation(text.getPosition()); //Loads position to uniform variable
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getVertexCount()); //Draws triangles, starting at the first vertex, drawing vertexCount amount of vertices.
		GL20.glDisableVertexAttribArray(0); //Disables attribute 0
		GL20.glDisableVertexAttribArray(1); //Disables attribute 1
		GL30.glBindVertexArray(0); //Binds 0 to the vao
	}
	
	private void stop(){
		shader.stop();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

}
