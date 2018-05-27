import processing.core.PApplet;
import processing.core.PImage;

public class UI {
    private float x;
    private float y;
    private float width;
    private float height;
    private PApplet pApplet;
    private PImage image;

    public UI(PApplet pApplet, float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.pApplet = pApplet;
    }

    public void update(PImage image) {
        this.image = image;
    }

    public void render() {
        if (isCollision(pApplet.mouseX, pApplet.mouseY)) {
            pApplet.stroke(150, 150, 150);
        }
        pApplet.fill(170,170,170);
        pApplet.rect(x, y, width, height);
        pApplet.image(image, x, y, width, height);
        pApplet.stroke(170, 170, 170);

    }

    public boolean mouseClicked(int mouseX, int mouseY) {
        return (isCollision(mouseX, mouseY));

    }

    public boolean isCollision(int mouseX, int mouseY) {
        return mouseX > x
                && mouseX < (x + width)
                && mouseY > y
                && mouseY < (y + height);
    }


}
