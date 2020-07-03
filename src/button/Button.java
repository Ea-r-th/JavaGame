package button;

public class Button {
    public float width;
    public float height;
    public float x;
    public float y;

    public Button(float width, float height, float x, float y){
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }
    public float getWidth() {
        return width;
    }
    public float getHeight() {
        return height;
    }
    public float xPos() {return x; }
    public float yPos() {return y; }
}
