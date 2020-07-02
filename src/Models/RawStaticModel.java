package Models;

public class RawStaticModel {

    private int vaoID;
    private int vertexCount;

    public RawStaticModel(int vaoID, int vertexCount){ //VAOID and vertexcount are defined in Loader
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

