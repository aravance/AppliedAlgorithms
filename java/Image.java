import java.io.PrintStream;
import java.util.Scanner;

public class Image extends AppliedAlgorithm {

    private static final int FULL = 'X';
    private static final int TOP = 0;
    private static final int RGT = 1;
    private static final int BOT = 2;
    private static final int LFT = 3;

    @SuppressWarnings("ConstantConditions")
    private static int getPerimeter(char[][] image, int x, int y) {

        final int width = image.length;
        final int height = image[0].length;

        // move to the top left
        boolean moved;
        do {
            moved = false;
            if (x + 1 < width && y + 1 < height && image[x + 1][y + 1] == FULL) {
                ++x;
                ++y;
                moved = true;
            } else if (x + 1 < width && image[x + 1][y] == FULL) {
                ++x;
                moved = true;
            } else if (y + 1 < height && image[x][y + 1] == FULL) {
                ++y;
                moved = true;
            }
        } while (moved);

        final int initX = x;
        final int initY = y;
        int side = TOP;

        // walk the perimeter
        int perimeter = 0;
        do {
            ++perimeter;
            switch (side) {
                case TOP:
                    if (x + 1 < width && y + 1 < height && image[x + 1][y + 1] == FULL) {
                        ++y;
                        ++x;
                        side = LFT;
                    } else if (x + 1 < width && image[x + 1][y] == FULL) {
                        ++x;
                    } else {
                        side = RGT;
                    }
                    break;
                case RGT:
                    if (x + 1 < width && y > 0 && image[x + 1][y - 1] == FULL) {
                        --y;
                        ++x;
                        side = TOP;
                    } else if (y > 0 && image[x][y - 1] == FULL) {
                        --y;
                    } else {
                        side = BOT;
                    }
                    break;
                case BOT:
                    if (x > 0 && y > 0 && image[x - 1][y - 1] == FULL) {
                        --y;
                        --x;
                        side = RGT;
                    } else if (x > 0 && image[x - 1][y] == FULL) {
                        --x;
                    } else {
                        side = LFT;
                    }
                    break;
                case LFT:
                    if (x > 0 && y + 1 < height && image[x - 1][y + 1] == FULL) {
                        ++y;
                        --x;
                        side = BOT;
                    } else if (y + 1 < height && image[x][y + 1] == FULL) {
                        ++y;
                    } else {
                        side = TOP;
                    }
                    break;
            }
        } while (x != initX || y != initY || side != TOP);

        return perimeter;
    }

    @Override
    void execute(Scanner in, PrintStream out) {
        for (int m = in.nextInt(), n = in.nextInt(), x = in.nextInt(), y = in.nextInt(), c = 1;
             m != 0 && n != 0 && x != 0 && y != 0;
             m = in.nextInt(), n = in.nextInt(), x = in.nextInt(), y = in.nextInt(), ++c) {
            char image[][] = new char[m][n];
            for (int i = 0; i < image.length; ++i) {
                String line = in.next();
                for (int j = 0; j < image[0].length; ++j) {
                    image[i][j] = line.charAt(j);
                }
            }
            out.println("Perimeter of object " + c + " at (" + x + "," + y + ") = " + getPerimeter(image, --x, --y));
        }
        out.println(); // for some reason the expected file has an extra new line
    }
}
