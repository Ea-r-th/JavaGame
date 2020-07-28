package shaders;

import entities.Camera;
import entities.Light;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import tools.MathProgram;

public class StaticShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src/shaders/vertex.glsl"; //Defines path to vertex shader
    private static final String FRAGMENT_FILE = "src/shaders/fragment.glsl"; //Path to fragment shader

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColor;
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_lightBias;

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    } //Creates new program thingy or something

    @Override
    protected void bindAttributes(){ //Binds the attributes to become ins for the vertex shader
        super.bindAttribute(0,"position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

    @Override
    protected void getAllUniformLocations(){ //Loads the uniform variables, this will need to happen for all uniforms you create
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_lightPosition = super.getUniformLocation("viewMatrix");
        location_lightColor = super.getUniformLocation("lightColor");
        location_lightBias = super.getUniformLocation("lightBias");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
    }

    public void loadLightVariables(float damper, float reflectivity, Vector2f lightBias){
        super.loadFloat(location_reflectivity, reflectivity);
        super.loadFloat(location_shineDamper, damper);
        super.loadVector2f(location_lightBias, lightBias);
    }

    public void loadTransformationMatrix(Matrix4f matrix)
    {
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadLight(Light light)
    {
        super.loadVector(location_lightPosition, light.getPosition());
        super.loadVector(location_lightColor, light.getColor());
    }

    public void loadProjectionMatrix(Matrix4f projection)
    {
        super.loadMatrix(location_projectionMatrix, projection);
    }

    public void loadViewMatrix(Camera camera)
    {
        Matrix4f viewMatrix = MathProgram.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }
}
