package renderEngine;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class Loader {

    private List<Integer> vaoList = new ArrayList<Integer>();
    private List<Integer> vboList = new ArrayList<Integer>();

    public RawModel loadToVao(float[] positions, int[] indices){
        int vaoID = createVAO(); //calls createVAO class to create new vao
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0,positions); //Stores float array of vertex positions to attribute number 0
        unbindVAO();
        return new RawModel(vaoID,indices.length);
    }

    public void cleanUp(){ //loops through all vaos and vbos and deletes them
        for(int vao:vaoList){
            GL30.glDeleteVertexArrays(vao);
        }
        for(int vbo:vboList){
            GL15.glDeleteBuffers(vbo);
        }
    }

    private void bindIndicesBuffer(int[] indices)
    {
        int vboID = GL15.glGenBuffers();
        vboList.add(vboID); //adds so that it's deleted when the game closes
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        java.nio.IntBuffer buffer = storeDataInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }

    private IntBuffer storeDataInIntBuffer(int[] data)
    {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private int createVAO(){
        int vaoID = GL30.glGenVertexArrays(); //creates new VAO
        vaoList.add(vaoID); //Adds vaoID to the list of active VAOs
        GL30.glBindVertexArray(vaoID); //Binds VAO, keep in mind that you must bind a vao to edit it or use it in any way.
        return vaoID;
    }

    private void storeDataInAttributeList(int attributeNumber, float[] data){ //stores a vbo/floatbuffer in an attribute list
        int vboID = GL15.glGenBuffers(); //Generates a new VBO
        vboList.add(vboID); //Adds vboID to the list of active VBOs
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID); //Binds buffer to be written to
        FloatBuffer buffer = storeDataInFloatBuffer(data); //Converts data into a floatbuffer
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);//Stores data in the VBO
        GL20.glVertexAttribPointer(attributeNumber, 3, GL11.GL_FLOAT,false,0,0);//Puts VBO into VAO attribute list
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); //Unbinds vbo when done
    }

    private void unbindVAO(){
        GL30.glBindVertexArray(0); //Binds a null value to VAO
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length); //Generates a new floatbuffer
        buffer.put(data); //Take a guess what this does
        buffer.flip(); //prepares data to be read from, rather than written to
        return buffer;
    }
}
