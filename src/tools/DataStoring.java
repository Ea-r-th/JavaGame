package tools;

import org.joml.Vector3f;
import utils.Color;
import utils.DataUtils;

import java.nio.ByteBuffer;

public class DataStoring {

	public static void packVertexData(Vector3f position, Vector3f normal, Color color, ByteBuffer buffer) {
		packVertexData(position.x, position.y, position.z, normal, color, buffer);
	}

	public static void packVertexData(float x, float y, float z, Vector3f normal, Color color, ByteBuffer buffer) {
		storePosition(buffer, x, y, z);
		storeNormal(buffer, normal);
		storeColor(buffer, color);
	}

	public static void packVertexData(float x, float y, float z, Color color, ByteBuffer buffer) {
		storePosition(buffer, x, y, z);
		storeColor(buffer, color);
	}

	private static void storePosition(ByteBuffer buffer, float x, float y, float z) {
		buffer.putFloat(x);
		buffer.putFloat(y);
		buffer.putFloat(z);
	}

	private static void storeNormal(ByteBuffer buffer, Vector3f normal) {
		int packedInt = DataUtils.pack_2_10_10_10_REV_int(normal.x, normal.y, normal.z, 0);
		buffer.putInt(packedInt);
	}

	private static void storeColor(ByteBuffer buffer, Color color) {
		byte[] colorBytes = color.getAsBytes();
		buffer.put(colorBytes);
	}

}
