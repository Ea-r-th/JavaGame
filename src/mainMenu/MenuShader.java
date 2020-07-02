package mainMenu;

import entities.Camera;
import org.joml.Matrix4f;
import shaders.ShaderProgram;
import tools.MathProgram;

public class MenuShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src/mainMenu/menuVertex.glsl";
    private static final String FRAGMENT_FILE = "src/mainMenu/menuFragment.glsl";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;

    public MenuShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes(){
        super.bindAttribute(0,"position");
        super.bindAttribute(1, "textureCoords");
    }

    @Override
    protected void getAllUniformLocations(){
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");

    }

    public void loadViewMatrix(Camera camera)
    {
        Matrix4f viewMatrix = MathProgram.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }
    public void loadMenuTranformatrionMatrix(Matrix4f matrix)
    {
        super.loadMatrix(location_transformationMatrix, matrix);
    }

}

