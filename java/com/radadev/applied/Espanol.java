package com.radadev.applied;

import com.radadev.applied.utility.SetBuilder;

import java.io.PrintStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Espanol extends AppliedAlgorithm {

    @Override
    protected void execute(Scanner in, PrintStream out) {
        final int n = in.nextInt();
        List<String> words = new LinkedList<>();
        for (int i = 0; i < n; ++i) {
            words.add(in.next());
        }
        Collections.sort(words, new EspanolComparator());
        for (String str : words) {
            out.println(str);
        }
    }

    private static class EspanolComparator implements Comparator<String> {

        private static final Set<String> PAIRS =
                SetBuilder.newBuilder(new TreeSet<String>())
                        .with("ch")
                        .with("ll")
                        .with("n~")
                        .with("rr")
                        .buildUnmodifiable();

        @Override
        public int compare(String o1, String o2) {
            o1 = o1.replaceAll("~n", "n~");
            o2 = o2.replaceAll("~n", "n~");
            int result = 0;
            for (int i1 = 0, i2 = 0; result == 0; ++i1, ++i2) {

                if (i1 >= o1.length()) {
                    result = -1;
                    continue;
                }

                if (i2 >= o2.length()) {
                    result = 1;
                    continue;
                }

                String s1 = o1.substring(i1, i1 + 1 < o1.length() ? i1 + 2 : i1 + 1);
                String s2 = o2.substring(i2, i2 + 1 < o2.length() ? i2 + 2 : i2 + 1);

                if (PAIRS.contains(s1)) {
                    ++i1;
                } else {
                    s1 = o1.substring(i1, i1 + 1);
                }

                if (PAIRS.contains(s2)) {
                    ++i2;
                } else {
                    s2 = o2.substring(i2, i2 + 1);
                }

                result = s1.compareTo(s2);
            }
            return result;
        }
    }
}
