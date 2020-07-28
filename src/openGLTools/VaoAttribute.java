package openGLTools;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL20;

public class VaoAttribute {

    protected final int attributeNumber;
    protected final int dataType;
    protected final boolean isNormalized;
    protected final int componentCount;
    protected final int bytesPerVertex;

    public VaoAttribute(int attributeNumber, int dataType, int componentCount){
        this.attributeNumber = attributeNumber;
        this.dataType = dataType;
        this.componentCount = componentCount;
        this.isNormalized = false;
        this.bytesPerVertex = calcBytesPerVertex();
    }

    public VaoAttribute(int attributeNumber, int dataType, int componentCount, boolean isNormalized){
        this.attributeNumber = attributeNumber;
        this.dataType = dataType;
        this.componentCount = componentCount;
        this.isNormalized = isNormalized;
        this.bytesPerVertex = calcBytesPerVertex();
    }

    protected void link(int offset, int stride) {
        GL20.glVertexAttribPointer(attributeNumber, componentCount, dataType, isNormalized, stride, offset);
    }

    protected void enable(boolean enable){
        if(enable) {
            GL20.glEnableVertexAttribArray(attributeNumber);
        }
        else {
            GL20.glDisableVertexAttribArray(attributeNumber);
        }
    }

    private int calcBytesPerVertex() {
        if (dataType == GL11.GL_FLOAT || dataType == GL11.GL_UNSIGNED_INT || dataType == GL11.GL_INT) {
            return 4 * componentCount;
        } else if (dataType == GL11.GL_SHORT || dataType == GL11.GL_UNSIGNED_SHORT) {
            return 2 * componentCount;
        } else if (dataType == GL11.GL_BYTE || dataType == GL11.GL_UNSIGNED_BYTE) {
            return 1 * componentCount;
        } else if (dataType == GL12.GL_UNSIGNED_INT_2_10_10_10_REV) {
            return 4;
        }
        System.err.println("The following dataType does not work:  " + dataType);
        return 0;
    }
}
