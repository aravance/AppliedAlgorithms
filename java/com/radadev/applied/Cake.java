package com.radadev.applied;

import java.io.PrintStream;
import java.util.*;

public class Cake extends AppliedAlgorithm {

    private int getLongestIncreasingSequence(List<Integer> belt) {
        Collections.reverse(belt);

        int[] lengths = new int[belt.size()];
        lengths[0] = 1;

        int max = 1;
        for (int i = 1; i < belt.size(); ++i) {
            int best = 1;
            for (int j = 0; j < i; ++j) {
                if (belt.get(j).compareTo(belt.get(i)) <= 0) {
                    if (lengths[j] >= best) best = lengths[j] + 1;
                }
            }
            lengths[i] = best;
            if (best > max) max = best;
        }
        return max;
    }

    @Override
    void execute(Scanner in, PrintStream out) {
        List<Integer> belt = new ArrayList<>(100000);
        for (int c = in.nextInt(); c != 0; c = in.nextInt()) {
            for (int i = 0; i < c; ++i) {
                belt.add(in.nextInt());
            }
            out.println(getLongestIncreasingSequence(belt));
            belt.clear();
        }
    }
}
