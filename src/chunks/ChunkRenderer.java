package chunks;

import Models.RawModel;
import entities.Light;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import textures.ModelTexture;
import tools.MathProgram;

import java.util.List;

public class ChunkRenderer {

    private ChunkShader shader;

    public ChunkRenderer(ChunkShader shader, Matrix4f projectionMatrix) {
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void render(List<Chunk> chunks) {
        for (Chunk chunk : chunks) {
            GL11.glDisable(GL11.GL_CULL_FACE);
            prepareChunk(chunk);
            loadModelMatrix(chunk);
            GL11.glDrawElements(GL11.GL_TRIANGLES, chunk.getVertexCount(),
                    GL11.GL_UNSIGNED_INT, 0);
            unbindTexturedModel();
            GL11.glEnable(GL11.GL_CULL_FACE);
        }
    }

    private void prepareChunk(Chunk chunk) {
        chunk.getVao().bind();
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
    }

    private void unbindTexturedModel() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void loadModelMatrix(Chunk chunk) {
        Matrix4f transformationMatrix = MathProgram.createTransformationMatrix(new Vector3f(chunk.getX(), 0, chunk.getZ()), 0, 0, 0, 1);
        shader.loadTransformationMatrix(transformationMatrix);
    }
}