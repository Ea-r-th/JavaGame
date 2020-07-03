package display;

public class Display {

    public int width;
    public int height;
    public String title;

    public Display(int width, int height, String title) { //Creates a display object so we can get the width and height more easily
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}