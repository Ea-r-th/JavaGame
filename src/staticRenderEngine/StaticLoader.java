package staticRenderEngine;

import Models.RawStaticModel;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import textures.Texture;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class StaticLoader {

    private static List<Integer> vaoList = new ArrayList<>();
    private static List<Integer> vboList = new ArrayList<>();
    private static List<Integer> textureList = new ArrayList<>();

    public RawStaticModel loadToVao(float[] positions, int[] indices, float[] textureCoords){
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0,3,positions);
        storeDataInAttributeList(1,2,textureCoords);
        unbindVAO();
        return new RawStaticModel(vaoID,indices.length);
    }

    private int createVAO(){
        int vaoID = GL30.glGenVertexArrays(); //creates new VAO
        vaoList.add(vaoID); //Adds vaoID to the list of active VAOs
        GL30.glBindVertexArray(vaoID); //Binds VAO, keep in mind that you must bind a vao to edit it or use it in any way.
        return vaoID;
    }

    private IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private void bindIndicesBuffer(int[] indices) {
        int vboID = GL15.glGenBuffers();
        vboList.add(vboID); //adds so that it's deleted when the game closes
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        java.nio.IntBuffer buffer = storeDataInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }

    public int loadTexture(String fileName)
    {
        Texture texture = new Texture("res/textures/"+fileName+".png");
        int textureID = texture.getTextureID();
        textureList.add(textureID);
        return textureID;
    }


    private void storeDataInAttributeList(int attributeNumber, int coordinateSize,float[] data){ //stores a vbo/floatbuffer in an attribute list
        int vboID = GL15.glGenBuffers(); //Generates a new VBO
        vboList.add(vboID); //Adds vboID to the list of active VBOs
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID); //Binds buffer to be written to
        FloatBuffer buffer = storeDataInFloatBuffer(data); //Converts data into a floatbuffer
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);//Stores data in the VBO
        GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT,false,0,0);//Puts VBO into VAO attribute list
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); //Unbinds vbo when done
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length); //Generates a new floatbuffer
        buffer.put(data); //Take a guess what this does
        buffer.flip(); //prepares data to be read from, rather than written to
        return buffer;
    }

    public void cleanUp(){ //loops through all vaos and vbos and deletes them
        for(int vao:vaoList){
            GL30.glDeleteVertexArrays(vao);
        }
        for(int vbo:vboList){
            GL15.glDeleteBuffers(vbo);
        }
        for(int texture:textureList)
        {
            GL11.glDeleteTextures(texture);
        }
    }
    private void unbindVAO(){
        GL30.glBindVertexArray(0); //Binds a null value to VAO
    }
}
