package openGLTools;

import org.lwjgl.opengl.GL11;

public class OpenGLUtils {

    public static void enableDepthTest(boolean b){
        if(b = true){
            GL11.glEnable(GL11.GL_DEPTH_TEST);
        }
        else{
            GL11.glDisable(GL11.GL_DEPTH_TEST);
        }
    }
    public static void enableBackCulling(boolean b){
        if(b = true){
            GL11.glEnable(GL11.GL_CULL_FACE);
        }
        else{
            GL11.glDisable(GL11.GL_CULL_FACE);
        }
    }
}
