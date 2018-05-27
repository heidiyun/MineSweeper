import processing.core.PApplet;
import processing.core.PImage;

import java.util.HashMap;

public class ResourceManager {

    public static PApplet pApplet;
    private static HashMap<Integer, PImage> hashMap = new HashMap<>();

    public static final int NUMBER_0 = 0;
    public static final int NUMBER_1 = 1;
    public static final int NUMBER_2 = 2;
    public static final int NUMBER_3 = 3;
    public static final int NUMBER_4 = 4;
    public static final int NUMBER_5 = 5;
    public static final int NUMBER_6 = 6;
    public static final int NUMBER_7 = 7;
    public static final int NUMBER_8 = 8;
    public static final int NUMBER_9 = 9;
    public static final int MINE_IMAGE = 10;
    public static final int FLAG_IMAGE = 11;
    public static final int SMILE_XX = 12;
    public static final int SMILE_OO = 13;
    public static final int SMILE_SUNGLASSES = 14;
    public static final int SMILE_SMILE = 15;
    public static final int SAVE_IMAGE = 16;
    public static final int LOAD_IMAGE = 17;
    public static final int COLON_IMAGE = 18;

    public static void init() {
        hashMap.put(NUMBER_0, pApplet.loadImage("./data/0.png"));
        hashMap.put(NUMBER_1, pApplet.loadImage("./data/1.png"));
        hashMap.put(NUMBER_2, pApplet.loadImage("./data/2.png"));
        hashMap.put(NUMBER_3, pApplet.loadImage("./data/3.png"));
        hashMap.put(NUMBER_4, pApplet.loadImage("./data/4.png"));
        hashMap.put(NUMBER_5, pApplet.loadImage("./data/5.png"));
        hashMap.put(NUMBER_6, pApplet.loadImage("./data/6.png"));
        hashMap.put(NUMBER_7, pApplet.loadImage("./data/7.png"));
        hashMap.put(NUMBER_8, pApplet.loadImage("./data/8.png"));
        hashMap.put(NUMBER_9, pApplet.loadImage("./data/9.png"));
        hashMap.put(MINE_IMAGE, pApplet.loadImage("./data/mine.png"));
        hashMap.put(FLAG_IMAGE, pApplet.loadImage("./data/pin.png"));
        hashMap.put(SMILE_XX, pApplet.loadImage("./data/XX.png"));
        hashMap.put(SMILE_OO, pApplet.loadImage("./data/oo.png"));
        hashMap.put(SMILE_SUNGLASSES, pApplet.loadImage("./data/선글라스.png"));
        hashMap.put(SMILE_SMILE, pApplet.loadImage("./data/smile.png"));
        hashMap.put(SAVE_IMAGE, pApplet.loadImage("./data/save.png"));
        hashMap.put(LOAD_IMAGE, pApplet.loadImage("./data/load.png"));
        hashMap.put(COLON_IMAGE, pApplet.loadImage("./data/colon.png"));
    }

    public static PImage load(int imageNumber) {
        return hashMap.get(imageNumber);
    }
}
