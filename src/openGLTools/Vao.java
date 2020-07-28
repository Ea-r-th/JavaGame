package openGLTools;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Vao {

    private List<Vbo> relatedVbos = new ArrayList<>();
    private Vbo indexBuffer;
    private List<VaoAttribute> attributeList = new ArrayList<>();

    public final int id;

    public static Vao create() {
        int id = GL30.glGenVertexArrays();
        return new Vao(id);
    }

    private Vao(int id) {
        this.id = id;
    }

    public void bind() {
        GL30.glBindVertexArray(id);
    }

    public void unbind() {
        GL30.glBindVertexArray(0);
    }

    public void enableAttributes() {
        for (VaoAttribute attribute : attributeList) {
            attribute.enable(true);
        }
    }

    public void disableAttribs(int... attribs) {
        for (int i : attribs) {
            GL20.glDisableVertexAttribArray(i);
        }
    }

    public Vbo createDataFeed(int maxVertexCount, int usage, VaoAttribute... newAttributes) {
        int bytesPerVertex = getVertexDataTotalBytes(newAttributes);
        Vbo vbo = Vbo.create(GL15.GL_ARRAY_BUFFER, usage);
        relatedVbos.add(vbo);
        vbo.allocateData(bytesPerVertex * maxVertexCount);
        linkAttributes(bytesPerVertex, newAttributes);
        vbo.unbind();
        return vbo;
    }

    public Vbo initDataFeed(FloatBuffer data, int usage, VaoAttribute... newAttributes) {
        int bytesPerVertex = getVertexDataTotalBytes(newAttributes);
        Vbo vbo = Vbo.create(GL15.GL_ARRAY_BUFFER, usage);
        relatedVbos.add(vbo);
        vbo.allocateData(data.limit() * 4);
        vbo.storeData(0, data);
        linkAttributes(bytesPerVertex, newAttributes);
        vbo.unbind();
        return vbo;
    }

    public Vbo initDataFeed(ByteBuffer data, int usage, VaoAttribute... newAttributes) {
        int bytesPerVertex = getVertexDataTotalBytes(newAttributes);
        Vbo vbo = Vbo.create(GL15.GL_ARRAY_BUFFER, usage);
        relatedVbos.add(vbo);
        vbo.allocateData(data.limit());
        vbo.storeData(0, data);
        linkAttributes(bytesPerVertex, newAttributes);
        vbo.unbind();
        return vbo;
    }

    public void linkBoundVbo(Vbo vbo, VaoAttribute... newAttributes) {
        int bytesPerVertex = getVertexDataTotalBytes(newAttributes);
        linkAttributes(bytesPerVertex, newAttributes);
        relatedVbos.add(vbo);
    }

    public Vbo createIndexBuffer(IntBuffer indices) {
        this.indexBuffer = Vbo.create(GL15.GL_ELEMENT_ARRAY_BUFFER, GL15.GL_STATIC_DRAW);
        indexBuffer.allocateData(indices.limit() * 4);
        indexBuffer.storeData(0, indices);
        return indexBuffer;
    }

    public void delete(boolean deleteVbos) {
        GL30.glDeleteVertexArrays(id);
        if (deleteVbos) {
            for (Vbo vbo : relatedVbos) {
                vbo.delete();
            }
        }
    }

    private void linkAttributes(int bytesPerVertex, VaoAttribute[] newAttributes) {
        int offset = 0;
        for (VaoAttribute attribute : newAttributes) {
            attribute.link(offset, bytesPerVertex);
            offset += attribute.bytesPerVertex;
            attribute.enable(true);
            attributeList.add(attribute);
        }
    }

    private int getVertexDataTotalBytes(VaoAttribute... newAttributes) {
        int total = 0;
        for (VaoAttribute attribute : newAttributes) {
            total += attribute.bytesPerVertex;
        }
        return total;
    }
}
