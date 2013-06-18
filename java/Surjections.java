import java.io.*;
import java.util.Scanner;

public class Surjections extends AppliedAlgorithm {

    long exponent(long x, long y) {
        long total = 1;
        for (long i = 0; i < y; ++i) {
            total *= x;
        }
        return total;
    }

    long factorial(long m) {
        long total = 1;
        for (int i = 1; i <= m; ++i) {
            total *= i;
        }
        return total;
    }

    long ni(long n, long i) {
        if (n == 0 || i == 0 || n == i) return 1;
        if (0 < i && i < n) return ni(n - 1, i - 1) + ni(n - 1, i);
        throw new UnsupportedOperationException("0 < i < n did not hold true: n=" + n + ", i=" + i);
    }

    long S(long m, long n) {
        if (n == 1) return 1;
        if (m < n) return 0;
        if (m == n) return factorial(m);
        long total = exponent(n, m);
        for (long i = 1; i < n; ++i) {
            total -= ni(n, i) * S(m, i);
        }
        return total;
    }

    @Override
    void execute(Scanner in, PrintStream out) {
        for (long m = in.nextLong(), n = in.nextLong();
             m != 0 && n != 0;
             m = in.nextLong(), n = in.nextLong()) {
            out.println("S(" + m + "," + n + ") = " + S(m, n));
        }
    }
}
