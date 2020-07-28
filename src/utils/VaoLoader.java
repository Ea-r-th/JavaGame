package utils;

import openGLTools.Vao;
import openGLTools.VaoAttribute;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL15;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * There's some stuff that I haven't covered in here because I'm storing the
 * data in packed formats (see the DataStoring class) but it has no effect on
 * this tutorial. You can store the vertex data in the VAO in whatever way you
 * want - it makes no difference to the outcome of the tutorial. I'll probably
 * talk about these packed formats in a future tutorial.
 * 
 * @author Karl
 *
 */
public class VaoLoader {

	public static Vao createVao(byte[] meshData, int[] indices) {
		Vao vao = Vao.create();
		vao.bind();
		storeVertexDataInVao(vao, meshData);
		if (indices != null) {
			storeIndicesInVao(vao, indices);
		}
		vao.unbind();
		return vao;
	}
	
	public static Vao createVaoNoNormals(byte[] meshData, int[] indices) {
		Vao vao = Vao.create();
		vao.bind();
		storeVertexDataInVaoNoNormals(vao, meshData);
		if (indices != null) {
			storeIndicesInVao(vao, indices);
		}
		vao.unbind();
		return vao;
	}
	
	private static void storeVertexDataInVaoNoNormals(Vao vao, byte[] meshData) {
		ByteBuffer buffer = storeMeshDataInBuffer(meshData);
		vao.initDataFeed(buffer, GL15.GL_STATIC_DRAW, new VaoAttribute(0, GL11.GL_FLOAT, 3),
				new VaoAttribute(1, GL11.GL_UNSIGNED_BYTE, 4, true));
	}

	private static void storeVertexDataInVao(Vao vao, byte[] meshData) {
		ByteBuffer buffer = storeMeshDataInBuffer(meshData);
		vao.initDataFeed(buffer, GL15.GL_STATIC_DRAW, new VaoAttribute(0, GL11.GL_FLOAT, 3),
				new VaoAttribute(1, GL11.GL_UNSIGNED_BYTE, 4, true));
	}
	
	private static ByteBuffer storeMeshDataInBuffer(byte[] meshData){
		ByteBuffer buffer = BufferUtils.createByteBuffer(meshData.length);
		buffer.put(meshData);
		buffer.flip();
		return buffer;
	}

	private static void storeIndicesInVao(Vao vao, int[] indices) {
		IntBuffer intBuffer = BufferUtils.createIntBuffer(indices.length);
		intBuffer.put(indices);
		intBuffer.flip();
		vao.createIndexBuffer(intBuffer);
	}
}
