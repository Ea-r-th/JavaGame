package tools;

import entities.Camera;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class MathProgram {

    public static Matrix4f createTransformationMatrix(Vector3f translation, float rx,float ry,float rz,float scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.identity();
        matrix.translate(translation);
        matrix.rotate((float)Math.toRadians(rx),new Vector3f(1,0,0));
        matrix.rotate((float)Math.toRadians(ry),new Vector3f(0,1,0));
        matrix.rotate((float)Math.toRadians(rz),new Vector3f(0,0,1));
        matrix.scale(new Vector3f(scale,scale,scale));
        return matrix;
    }

    public static Matrix4f createViewMatrix(Camera camera) {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0));
        viewMatrix.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0));
        Vector3f cameraPos = camera.getPosition();
        Vector3f negativeCameraPos = new Vector3f(-cameraPos.x,-cameraPos.y,-cameraPos.z);
        viewMatrix.translate(negativeCameraPos);
        return viewMatrix;
    }

    /**
     * Calculates the normal of the triangle made from the 3 vertices. The vertices must be specified in counter-clockwise order.
     * @param vertex0
     * @param vertex1
     * @param vertex2
     * @return
     */
    public static Vector3f calculateNormal(Vector3f vertex0, Vector3f vertex1, Vector3f vertex2) {
        Vector3f segmentA = vertex1.sub(vertex0);
        Vector3f segmentB = vertex2.sub(vertex0);
        Vector3f normal = segmentA.cross(segmentB);
        normal.normalize();
        return normal;
    }

    public static float clamp(float value, float min, float max){
        return Math.max(Math.min(value, max), min);
    }
}
