import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Window extends PApplet {
    private Map map;
    private Scanner input = new Scanner(System.in);
    private UI smileButton = new UI(this, 470, 25, 100, 100);
    private UI saveButton = new UI(this, 400, 25, 30, 30);
    private UI loadButton = new UI(this, 400, 90, 30, 30);
    private boolean pressed = false;
    private boolean gamePlaying = true;
    private int tick = 0;
    private int second = 0;
    private int minute = 0;
    private boolean pressedSave = false;

    public void settings() {
        size(1080, 640);
        do {
            System.out.print("160이하의 숫자를 입력해주세요 >> ");
            Constants.MINE_COUNT = input.nextInt();
        } while (Constants.MINE_COUNT > 160);

        map = new Map(this);
        ResourceManager.pApplet = this;
        ResourceManager.init();
    }

    public void setup() {

    }

    public void draw() {
        background(0);
        if (gamePlaying) {
            tick++;
        }

        map.update();
        map.render();
//        try {
//            SpriteManager.loadImage(this, "./data/smileSprite (2).jpg", 0, 0, 38, 38, 1, 0);
//            SpriteManager.loadImage(this, "./data/smileSprite (2).jpg", 2, 2, 30, 30, 1, 19);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
       smileButton.update(ResourceManager.load(ResourceManager.SMILE_SMILE));
        saveButton.update(ResourceManager.load(ResourceManager.SAVE_IMAGE));
        loadButton.update(ResourceManager.load(ResourceManager.LOAD_IMAGE));

        if (map.getCheckMine()) {
            smileButton.update(ResourceManager.load(ResourceManager.SMILE_XX));
            gamePlaying = false;
        }

        if (map.getCheckAllOfMineByFlag() && map.getAllOfBlockIsVisible()) {
            smileButton.update(ResourceManager.load(ResourceManager.SMILE_SUNGLASSES));
            gamePlaying = false;
        }

        if (pressed) {
            smileButton.update(ResourceManager.load(ResourceManager.SMILE_OO));
        }

        boolean secondUp = false;

        if (tick % 60 == 0 && tick != 0) {
            second++;
            secondUp = true;
        }

        if (second % 60 == 0 && second != 0) {
            if (secondUp) {
                minute++;
                second = 0;
            }
        }

        saveButton.render();
        loadButton.render();
        smileButton.render();
        showTimeImage(minute, "minute");
        showTimeImage(second, "second");

    }

    private void showTimeImage(int num, String name) {
        int tenSeat = num / 10;
        int oneSeat = num % 10;

        if (name.equals("minute")) {
            image(ResourceManager.load(tenSeat), 750, 30, 50, 50);
            image(ResourceManager.load(oneSeat), 810, 30, 50, 50);
        } else {
            image(ResourceManager.load(tenSeat), 880, 30, 50, 50);
            image(ResourceManager.load(oneSeat), 940, 30, 50, 50);
        }
    }

    public void keyPressed(processing.event.KeyEvent event) {
        if (gamePlaying) {
            map.keyPressed(event.getKey());
            if (event.getKey() == 'd' || event.getKey() == 's') {
                pressed = true;
            }
        }
    }

    public void keyReleased() {
        pressed = false;
    }


    public void mouseClicked() {
        if (smileButton.mouseClicked(mouseX, mouseY)) {
            map.mouseClicked();
            gamePlaying = true;
            second = 0;
            minute = 0;
        }

        if (gamePlaying) {

            if (saveButton.mouseClicked(mouseX, mouseY)) {
                FileManager.writeFile("./data/save.csv", map.toString());
                FileManager.writeFile("./data/saveTime.csv", toString());
                pressedSave = true;
                System.out.println("save");
            }

            if (loadButton.mouseClicked(mouseX, mouseY)) {
                if (pressedSave) {
                    String mapInformation = FileManager.readFile("./data/save.csv");
                    map.loadMap(mapInformation);
                    String[] time = FileManager.readFile("./data/saveTime.csv").split(",");
                    minute = Integer.parseInt(time[0]);
                    second = Integer.parseInt(time[1]);
                    System.out.println("load");
                }
            }
        }
    }

    public String toString() {
        return minute + "," + second;
    }
}
