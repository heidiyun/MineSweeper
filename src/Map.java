import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private List<Block> blocks = new ArrayList<>();
    private PApplet pApplet;
    private boolean checkMine = false;
    private int invisibleBlockCount = 0;
    private int mineHundredSeat;
    private int mineTenSeat;
    private int mineOneSeat;
    private int hundredSeat;
    private int tenSeat;
    private int oneSeat;
    private int checkingMineCount;

    public Map(PApplet pApplet) {
        this.pApplet = pApplet;

        for (int i = 0; i < Constants.BLOCK_COUNT_X * Constants.BLOCK_COUNT_Y; i++) {
            blocks.add(new Block(pApplet, i));
        }

        initMap();

        for (Block block : blocks) {
            block.makeConnections(blocks);
        }

    }

    private void initMap() {
        int[] map = new int[Constants.BLOCK_COUNT_X * Constants.BLOCK_COUNT_Y];

        for (int i = 0; i < blocks.size(); i++) {
            map[i] = i;
        }

        for (int i = 0; i < blocks.size() * 3; i++) {

            int a = (int) (Math.random() * blocks.size());
            int b = (int) (Math.random() * blocks.size());

            int temp = map[a];
            map[a] = map[b];
            map[b] = temp;
        }

        for (int i = 0; i < Constants.MINE_COUNT; i++) {
            blocks.get(map[i]).setBlockState(Constants.BLOCK_STATE_MINE);
        }
    }

    public void update() {
        invisibleBlockCount = 0;
        checkingMineCount = Constants.MINE_COUNT;

        for (Block block : blocks) {
            if (block.getVisibleState() == Constants.INVISIBLE)
                invisibleBlockCount++;

            if (block.getCheckMineByFlag()) checkingMineCount--;
        }

        if (invisibleBlockCount >= 100) {
            hundredSeat = invisibleBlockCount / 100;
            tenSeat = (invisibleBlockCount - (hundredSeat * 100)) / 10;
            oneSeat = (invisibleBlockCount - (hundredSeat * 100) - (tenSeat * 10)) % 10;
        } else {
            hundredSeat = 0;
            tenSeat = invisibleBlockCount / 10;
            oneSeat = invisibleBlockCount % 10;
        }

        mineHundredSeat = (checkingMineCount / 100);
        mineTenSeat = (checkingMineCount % 100) / 10;
        mineOneSeat = checkingMineCount % 10;


    }

    public void render() {
        pApplet.fill(204, 204, 204);
        pApplet.rect(0, 0, 1080, 150);
        pApplet.stroke(170, 170, 170);
        for (Block block : blocks) {
            block.render();
        }
        pApplet.strokeWeight(20);

        pApplet.image(ResourceManager.load(hundredSeat), 20, 20, 30, 50);
        pApplet.image(ResourceManager.load(tenSeat), 55, 20, 30, 50);
        pApplet.image(ResourceManager.load(oneSeat), 90, 20, 30, 50);

        pApplet.image(ResourceManager.load(mineHundredSeat), 20, 75, 30, 50);
        pApplet.image(ResourceManager.load(mineTenSeat), 55, 75, 30, 50);
        pApplet.image(ResourceManager.load(mineOneSeat), 90, 75, 30, 50);

    }

    public void keyPressed(char key) {
        int index = Util.getIndexByPos(pApplet.mouseX, pApplet.mouseY);
        if (index < 0 || index >= 480) return;
        if (key == 'a') {
            if (!blocks.get(index).getCheckMineByFlag())
                blocks.get(index).click(false);
            if (blocks.get(index).getBlockState() == Constants.BLOCK_STATE_MINE) {
                for (Block block : blocks) {
                    if (block.getBlockState() == Constants.BLOCK_STATE_MINE) {
                        block.setVisibleState(Constants.VISIBLE);
                        checkMine = true;
                    }
                }
            }
        }
        if (checkingMineCount > 0) {
            if (key == 's') {
                if (blocks.get(index).getVisibleState() == Constants.INVISIBLE) {
                    if (blocks.get(index).getCheckMineByFlag())
                        blocks.get(index).setCheckingMineByFlag(false);
                    else blocks.get(index).setCheckingMineByFlag(true);
                }
            }
        }

        if (key == 'd') {
            Block blockStd = blocks.get(index);
            if (blockStd.getVisibleState() == Constants.VISIBLE &&
                    blockStd.realMineEqualsCheckMineInConnections() &&
                    !blockStd.openConnections()) {

                for (Block block : blocks) {
                    if (block.getBlockState() == Constants.BLOCK_STATE_MINE) {
                        block.setVisibleState(Constants.VISIBLE);
                        checkMine = true;
                    }
                }
                checkMine = true;

            }
        }

    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Block block : blocks) {
            stringBuilder.append(block.toString());
            stringBuilder.append(",");
        }
        return stringBuilder.toString();
    }

    public boolean getAllOfBlockIsVisible() {
        int count = 0;
        for (Block block : blocks) {
            if (block.getBlockState() == Constants.BLOCK_STATE_NUMBER && block.getVisibleState() == Constants.VISIBLE)
                count++;
        }

        return count == Constants.BLOCK_COUNT_X * Constants.BLOCK_COUNT_Y - Constants.MINE_COUNT;
    }

    public boolean getCheckAllOfMineByFlag() {
        int count = 0;

        for (Block block : blocks) {
            if (block.getBlockState() == Constants.BLOCK_STATE_MINE && block.getCheckMineByFlag())
                count++;
        }

        return (count == Constants.MINE_COUNT);
    }

    public boolean getCheckMine() {
        return checkMine;
    }

    public void loadMap(String mapInformation) {
        String[] s = mapInformation.split(",");
        for (int i = 0; i < s.length; i++) {
            blocks.get(i).loadBlock(s[i]);
        }
    }

    public void mouseClicked() {
        for (Block block : blocks) {
            block.setVisibleState(Constants.INVISIBLE);
            block.setCheckingMineByFlag(false);
            block.setBlockState(Constants.BLOCK_STATE_NUMBER);
        }

        initMap();

        for (Block block : blocks) {
            block.makeConnections(blocks);
        }

        checkMine = false;
        update();
    }

}
