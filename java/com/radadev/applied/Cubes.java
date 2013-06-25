package com.radadev.applied;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Cubes extends AppliedAlgorithm {

    private static double f(double b, double c, double d) {
        return Math.cbrt(Math.pow(b, 3) + Math.pow(c, 3) + Math.pow(d, 3));
    }

    @Override
    protected void execute(Scanner in, PrintStream out) {
        for (String line = in.nextLine(); !line.equals("0 0"); line = in.nextLine()) {

            StringTokenizer tokenizer = new StringTokenizer(line);
            final int m = Integer.parseInt(tokenizer.nextToken());
            final int n = Integer.parseInt(tokenizer.nextToken());

            SortedSet<CubeSet> results = new TreeSet<>();
            for (int b = 1; b <= n; ++b) {
                for (int c = b; c <= n; ++c) {
                    for (int d = c; d <= n; ++d) {
                        Double a = f(b, c, d);
                        if (a > n) break;
                        if (a >= m && a % 1 == 0) results.add(new CubeSet(a.intValue(), b, c, d));
                    }
                }
            }

            for (CubeSet result : results) {
                out.println(result);
            }
            out.println("+++");
        }
    }

    private static class CubeSet implements Comparable<CubeSet> {

        private int a;
        private int b;
        private int c;
        private int d;

        public CubeSet(int a, int b, int c, int d) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }

        @Override
        public String toString() {
            return a + " = f(" + b + "," + c + "," + d + ")";
        }

        @Override
        public int compareTo(CubeSet o) {
            int result = Integer.compare(a, o.a);
            if (result == 0) result = Integer.compare(b, o.b);
            if (result == 0) result = Integer.compare(c, o.c);
            if (result == 0) result = Integer.compare(d, o.d);
            return result;
        }
    }
}
