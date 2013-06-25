package com.radadev.applied;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Subvector extends AppliedAlgorithm {

    private static long maxsub(ArrayList<Long> array) {
        long max = array.get(0);
        for (int i = 1; i < array.size(); ++i) {
            if (array.get(i - 1) > 0) array.set(i, array.get(i) + array.get(i - 1));
            if (array.get(i) > max) max = array.get(i);
        }
        return max;
    }

    @Override
    protected void execute(Scanner in, PrintStream out) {
        ArrayList<Long> array = new ArrayList<>(1000000);
        final int count = in.nextInt();
        for (int i = 0; i < count; ++i) {
            array.clear();
            int size = in.nextInt();
            array.ensureCapacity(size);
            for (int j = 0; j < size; ++j) {
                array.add(in.nextLong());
            }
            out.println(maxsub(array));
        }
    }
}
