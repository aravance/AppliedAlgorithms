package com.radadev.applied;

import java.io.PrintStream;
import java.util.Scanner;

public class Plinko extends AppliedAlgorithm {

    private int getBestPlinko(int[][] plinko) {
        for (int i = plinko.length - 2; i >= 0; --i) {
            for (int j = 0; j < plinko[i].length; ++j) {
                plinko[i][j] += Math.max(plinko[i + 1][j], plinko[i + 1][j + 1]);
            }
        }
        return plinko[0][0];
    }

    @Override
    protected void execute(Scanner in, PrintStream out) {
        for (int size = in.nextInt(); size != 0; size = in.nextInt()) {
            int[][] plinko = new int[size][];
            for (int i = 0; i < size; ++i) {
                plinko[i] = new int[i + 1];
                for (int j = 0; j < plinko[i].length; ++j) {
                    plinko[i][j] = in.nextInt();
                }
            }
            out.println(getBestPlinko(plinko));
        }
    }
}
