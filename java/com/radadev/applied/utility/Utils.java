package com.radadev.applied.utility;

import java.util.Arrays;

public class Utils {

    private Utils() {
        // disallow instantiation
    }

    @SafeVarargs
    public static <T> T[] concat(T[] array, T... others) {
        int length = array.length + others.length;
        T[] result = Arrays.copyOf(array, length);
        for (int i = array.length, j = 0; i < length && j < others.length; ++i, ++j) {
            result[i] = others[j];
        }
        return result;
    }

    public static String repeat(Object o, int n) {
        return n <= 0 || o == null ?
                "" :
                String.format(String.format("%%0%dd", n), 0).replaceAll("0", o.toString());
    }
}
