package Models;

public class RawModel {

    private int vaoID;
    private int vertexCount;

    public RawModel(int vaoID, int vertexCount){ //VAOID and vertexcount are defined in Loader
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVertexCount() {
        return vertexCount;
    }
}
