public class Util {

    public static int getPosXByIndex(int index) {
        return (index % Constants.BLOCK_COUNT_X) * Constants.BLOCK_SIZE_X;
    }

    public static int getPosYByIndex(int index) {
        return (index / Constants.BLOCK_COUNT_X) * Constants.BLOCK_SIZE_Y + 150;
    }

    public static int getIndexByPos(int x, int y) {
        return (x / Constants.BLOCK_SIZE_X) + (((y  - 150) / Constants.BLOCK_SIZE_Y) * Constants.BLOCK_COUNT_X);
    }

    public static boolean isTop(int index) {
        return index / Constants.BLOCK_COUNT_X == 0;
    }

    public static boolean isBottom(int index) {
        return index / Constants.BLOCK_COUNT_X == (Constants.BLOCK_COUNT_Y - 1);
    }

    public static boolean isLeft(int index) {
        return index % Constants.BLOCK_COUNT_X == 0;
    }

    public static boolean isRight(int index) {
        return index % Constants.BLOCK_COUNT_X == (Constants.BLOCK_COUNT_X - 1);
    }
}
