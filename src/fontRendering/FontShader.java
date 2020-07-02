package fontRendering;

import org.joml.Vector2f;
import org.joml.Vector3f;
import shaders.ShaderProgram;

public class FontShader extends ShaderProgram{

	private static final String VERTEX_FILE = "src/fontRendering/fontVertex.glsl";
	private static final String FRAGMENT_FILE = "src/fontRendering/fontFragment.glsl";

	private int location_color;
	private int location_translation;
	
	public FontShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_color = super.getUniformLocation("color");
		location_translation = super.getUniformLocation("translation");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position"); //Links the color in variable in vertex shader to attribute 2 in the VAO
		super.bindAttribute(1, "textureCoords"); //Links the textureCoords in variable in vertex shader to attribute 1 in the VAO
	}

	protected void loadColor(Vector3f color){

		super.loadVector(location_color, color);

	}

	protected void loadTranslation(Vector2f translation){
		super.loadVector2f(location_translation, translation);
	}
}
