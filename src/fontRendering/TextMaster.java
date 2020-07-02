package fontRendering;

import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontMeshCreator.TextMeshData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextMaster {

    private static FontLoader loader;
    private static Map<FontType, List<GUIText>> texts  = new HashMap<>();
    private static FontRenderer renderer;

    public static void init(){
        renderer = new FontRenderer();
        loader = new FontLoader();
    }

    public static void render(){
        renderer.render(texts);
    }

    public static void loadText(GUIText text){
        FontType font = text.getFont();
        TextMeshData data = font.loadText(text);
        int vao = loader.loadVAO(data.getVertexPositions(), data.getTextureCoords());
        text.setMeshInfo(vao, data.getVertexCount());
        List<GUIText> textBatch = texts.get(font);
        if(textBatch == null){
            textBatch = new ArrayList<GUIText>();
            texts.put(font, textBatch);
        }
        textBatch.add(text);
    }

    public static void removeText(GUIText text){
        List<GUIText> textBatch = texts.get(text.getFont());
        textBatch.remove(text);
        if(textBatch.isEmpty()){
            texts.remove(text.getFont());
            //loader.deleteVAO(text.getMesh());
        }
    }

    public static void cleanUp(){
        renderer.cleanUp();
    }

}
