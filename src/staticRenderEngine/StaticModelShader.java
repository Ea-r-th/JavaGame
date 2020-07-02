package staticRenderEngine;

import org.joml.Matrix4f;
import shaders.ShaderProgram;

public class StaticModelShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src/staticRenderEngine/staticVertex.glsl";
    private static final String FRAGMENT_FILE = "src/staticRenderEngine/staticFragment.glsl";

    public StaticModelShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes(){
        super.bindAttribute(0,"position");
        super.bindAttribute(1, "textureCoords");
    }

    @Override
    protected void getAllUniformLocations(){
        //Dont need shit here, suck my cack uniform variables this shiesse is staying in the same place xDDDDDDDD
    }
}
