package chunks;

import chunkGeneration.HeightsGenerator;
import chunkGeneration.PerlinNoise;
import openGLTools.Vao;
import org.joml.Vector3f;
import utils.ColorGenerator;
import utils.DataStoring;
import utils.VaoLoader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Chunk {

    private static PerlinNoise perlinNoise = new PerlinNoise(4,70,1);

    private static float size = 800;
    private static int vertexCount;

    private float x;
    private float z;
    private static Vao vao;

    private static final int VERTEX_SIZE_BYTES = 12 + 4;// position + normal

    public Chunk(int gridX, int gridZ, Vao vao, int vertexCount){
        this.x = gridX * size;
        this.z = gridZ * size;
        this.vao = vao;
        this.vertexCount = vertexCount;
    }

    public static Vector3f calculateNormal(int x, int z, HeightsGenerator generator){
        float heightL = getHeight(x-1, z, generator);
        float heightR = getHeight(x+1, z, generator);
        float heightD = getHeight(x, z-1, generator);
        float heightU = getHeight(x, z+1, generator);
        Vector3f normal = new Vector3f(heightR - heightL, 2f, heightD - heightU);
        normal.normalize();
        return normal;
    }

    public static Chunk createChunk(int vertexCount) {
        int totalVertexCount = 0;
        int count = vertexCount * vertexCount;
        int byteSize = VERTEX_SIZE_BYTES * count;
        ByteBuffer buffer = ByteBuffer.allocate(byteSize).order(ByteOrder.nativeOrder());
        HeightsGenerator generator = new HeightsGenerator();

        float[][] heights = new float[vertexCount][vertexCount];
        float[] vertices = new float[count * 3];
        int[] indices = new int[6*(vertexCount-1)*(vertexCount-1)];
        int vertexPointer = 0;
        for(int i=0;i<vertexCount;i++){
            for(int j=0;j<vertexCount;j++){
                vertices[vertexPointer*3] = (float)j/((float)vertexCount - 1) * size;
                float height = getHeight(j, i, generator);
                vertices[vertexPointer*3+1] = getHeight(j, i, generator);
                heights[j][i] = height;
                vertices[vertexPointer*3+2] = (float)i/((float)vertexCount - 1) * size;
                Vector3f normal = calculateNormal(j, i, generator);
                Vector3f position = new Vector3f(j/((float)vertexCount - 1) * size, perlinNoise.getPerlinNoise(i, j), (float)i/((float)vertexCount - 1) * size);
                //Vector3f position = new Vector3f(i, 20, j);
                DataStoring.packVertexData(position, normal, buffer);
                vertexPointer++;
                totalVertexCount++;
            }
        }
        int pointer = 0;
        for(int gz=0;gz<vertexCount-1;gz++){
            for(int gx=0;gx<vertexCount-1;gx++){
                int topLeft = (gz*vertexCount)+gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz+1)*vertexCount)+gx;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }
        System.out.println(totalVertexCount);
        byte[] byteArray = buffer.array();
        vao = VaoLoader.createVao(byteArray, indices);
        return new Chunk(0,0, vao, indices.length);
    }

    public static float getHeight(int x, int z, HeightsGenerator generator){
        return generator.generateHeights(x, z);
    }

    public static float getSize() {
        return size;
    }

    public static int getVertexCount() {
        return vertexCount;
    }

    public float getX() {
        return x;
    }

    public float getZ() {
        return z;
    }

    public Vao getVao() {
        return vao;
    }
}

