import java.io.PrintStream;
import java.util.Scanner;

public class Subvector extends AppliedAlgorithm {

    private long maxsub(int[] array) {
        long[] sums = new long[array.length];
        long max = sums[0] = array[0];
        for (int i = 1; i < sums.length; ++i) {
            sums[i] = array[i];
            if (sums[i - 1] > 0) sums[i] += sums[i - 1];
            if (sums[i] > max) max = sums[i];
        }
        return max;
    }

    @Override
    void execute(Scanner in, PrintStream out) {
        final int count = in.nextInt();
        for (int i = 0; i < count; ++i) {
            int size = in.nextInt();
            int array[] = new int[size];
            for (int j = 0; j < size; ++j) {
                array[j] = in.nextInt();
            }
            out.println(maxsub(array));
        }
    }
}
