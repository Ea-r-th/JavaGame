package fontRendering;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import textures.Texture;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class FontLoader {

    private List<Integer> vaoList = new ArrayList<>();
    private List<Integer> vboList = new ArrayList<>();
    private List<Integer> textures = new ArrayList<>();

    public int loadVAO(float[] positions,float[] textureCoords){
        int vaoID = createVAO(); //calls createVAO class to create new vao
        storeDataInAttributeList(0,2,positions); //Stores float array of vertex positions to attribute number 0
        storeDataInAttributeList(1,2,textureCoords);
        unbindVAO();
        return vaoID;
    }

    public int loadTexture(String fileName)
    {
        Texture texture = new Texture("res/fonts/"+fileName+".png");
        int textureID = texture.getTextureID();
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -0);
        textures.add(textureID);
        return textureID;

    }

    public void deleteVAO(int vao){
        vaoList.remove(vao);
        GL30.glDeleteVertexArrays(vao);
    }

    public void deleteVBO(int vbo){
        vaoList.remove(vbo);
        GL15.glDeleteBuffers(vbo);
    }

    public void cleanUp(){ //loops through all vaos and vbos and deletes them
        for(int vao:vaoList){
            GL30.glDeleteVertexArrays(vao);
        }
        for(int vbo:vboList){
            GL15.glDeleteBuffers(vbo);
        }
        for(int texture:textures)
        {
            GL11.glDeleteTextures(texture);
        }
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

    private void storeDataInAttributeList(int attributeNumber, int coordinateSize,float[] data){ //stores a vbo/floatbuffer in an attribute list
        int vboID = GL15.glGenBuffers(); //Generates a new VBO
        vboList.add(vboID); //Adds vboID to the list of active VBOs
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID); //Binds buffer to be written to
        FloatBuffer buffer = storeDataInFloatBuffer(data); //Converts data into a floatbuffer
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);//Stores data in the VBO
        GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT,false,0,0);//Puts VBO into VAO attribute list
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
