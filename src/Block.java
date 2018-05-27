import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class Block {
    private boolean checkingMineByFlag;
    private int visibleState;
    private int blockState;
    private PApplet pApplet;
    private int neighborMineCount = 0;
    private int index;
    private int x;
    private int y;
    private List<Block> connections = new ArrayList<>();
    private boolean flickering = false;

    public Block(PApplet pApplet, int index) {
        this.visibleState = Constants.INVISIBLE;
        this.blockState = Constants.BLOCK_STATE_NUMBER;
        this.pApplet = pApplet;
        this.index = index;

        x = Util.getPosXByIndex(index);
        y = Util.getPosYByIndex(index);

    }


    public void render() {

        if (visibleState == Constants.INVISIBLE) {
            pApplet.fill(204, 204, 204);
            pApplet.stroke(170, 170, 170);
            pApplet.strokeWeight(3);
            pApplet.rect(x, y, Constants.BLOCK_SIZE_X, Constants.BLOCK_SIZE_Y);

            if (checkingMineByFlag) {
                pApplet.image(ResourceManager.load(ResourceManager.FLAG_IMAGE), x, y + 5, Constants.BLOCK_SIZE_X, Constants.BLOCK_SIZE_Y);
            }

        } else {
            pApplet.stroke(170, 170, 170);
            pApplet.strokeWeight(3);
            pApplet.fill(102, 102, 102);
            pApplet.rect(x, y, Constants.BLOCK_SIZE_X, Constants.BLOCK_SIZE_Y);

            if (blockState == Constants.BLOCK_STATE_MINE) {
                pApplet.fill(255,0,0);
                pApplet.rect(x, y, Constants.BLOCK_SIZE_X, Constants.BLOCK_SIZE_Y);
                pApplet.image(ResourceManager.load(ResourceManager.MINE_IMAGE), x, y, Constants.BLOCK_SIZE_X, Constants.BLOCK_SIZE_Y);
            } else {
                if (neighborMineCount != 0) {
                    pApplet.fill(0);
                    pApplet.textSize(15);
                    pApplet.text(neighborMineCount, x + (Constants.BLOCK_SIZE_X / 2), y + (Constants.BLOCK_SIZE_Y / 2) + 5);
                }
            }
        }
        if (flickering) {
            pApplet.fill(153, 153, 153);
            pApplet.rect(x, y, Constants.BLOCK_SIZE_X, Constants.BLOCK_SIZE_Y);
            flickering = false;
        }

    }

    public void click(boolean visible) {
        if (!visible) {
            if (visibleState == Constants.VISIBLE) return;
        }
        if (!checkingMineByFlag)
            visibleState = Constants.VISIBLE;

        if (neighborMineCount == 0 && blockState != Constants.BLOCK_STATE_MINE) {
            for (Block block : connections) {
                block.click(false);
            }
        }

    }


    public boolean openConnections() {
        for (Block block : connections) {
            if (!block.checkingMineByFlag) {
                block.visibleState = Constants.VISIBLE;
                block.click(true);
                if (block.blockState == Constants.BLOCK_STATE_MINE)
                    return false;
            }
        }

        return true;
    }

    public boolean realMineEqualsCheckMineInConnections() {
        int count = 0;
        for (Block block : connections) {
            block.flickering = true;
            if (block.getCheckMineByFlag()) count++;
        }

        return count == neighborMineCount;
    }

    public void makeConnections(List<Block> blocks) {
        neighborMineCount = 0;
        connections.clear();

        if (!Util.isLeft(index) && !Util.isTop(index)) {
            connections.add(blocks.get(index - 1 - Constants.BLOCK_COUNT_X));
        }

        if (!Util.isTop(index)) {
            connections.add(blocks.get(index - Constants.BLOCK_COUNT_X));
        }

        if (!Util.isTop(index) && !Util.isRight(index)) {
            connections.add(blocks.get(index + 1 - Constants.BLOCK_COUNT_X));
        }

        if (!Util.isLeft(index)) {
            connections.add(blocks.get(index - 1));
        }

        if (!Util.isRight(index)) {
            connections.add(blocks.get(index + 1));
        }

        if (!Util.isLeft(index) && !Util.isBottom(index)) {
            connections.add(blocks.get(index + Constants.BLOCK_COUNT_X - 1));
        }

        if (!Util.isBottom(index)) {
            connections.add(blocks.get(index + Constants.BLOCK_COUNT_X));
        }

        if (!Util.isBottom(index) && !Util.isRight(index)) {
            connections.add(blocks.get(index + Constants.BLOCK_COUNT_X + 1));
        }

        for (Block block : connections) {
            if (block.blockState == Constants.BLOCK_STATE_MINE)
                neighborMineCount++;
        }

    }

    public void loadBlock(String s) {
        String[] information = s.split("_");
        visibleState = Integer.parseInt(information[0]);
        blockState = Integer.parseInt(information[1]);
        if (information[2].equals("false"))
            checkingMineByFlag = false;
        else checkingMineByFlag = true;
        neighborMineCount = Integer.parseInt(information[3]);
        index = Integer.parseInt(information[4]);
    }

    @Override
    public String toString() {
        return visibleState + "_" + blockState + "_" + checkingMineByFlag + "_" + neighborMineCount + "_" + index;
    }

    public void setCheckingMineByFlag(boolean checking) {
        this.checkingMineByFlag = checking;
    }

    public void setBlockState(int blockState) {
        this.blockState = blockState;
    }


    public boolean getCheckMineByFlag() {
        return checkingMineByFlag;
    }

    public int getBlockState() {
        return blockState;
    }

    public void setVisibleState(int visibleState) {
        this.visibleState = visibleState;
    }

    public int getVisibleState() {
        return visibleState;
    }
}

