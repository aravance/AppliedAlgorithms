package com.radadev.applied;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Anagrams extends AppliedAlgorithm {

    private static final String END = "<end>";

    List<Character> normalize(String s) {
        List<Character> result = new ArrayList<>();
        for (char c : s.toCharArray()) {
            if (Character.isAlphabetic(c)) {
                result.add(Character.toLowerCase(c));
            }
        }
        Collections.sort(result);
        return result;
    }

    private boolean anagramic(String first, String second) {
        List<Character> f = normalize(first);
        List<Character> s = normalize(second);
        boolean success = f.size() == s.size();
        for (int i = 0; success && i < f.size(); ++i) {
            success = f.get(i) == s.get(i);
        }
        return success;
    }

    @Override
    protected void execute(Scanner in, PrintStream out) {
        for (String first = in.nextLine(), second = in.nextLine();
             !END.equals(first) && !END.equals(second);
             first = in.nextLine(), second = in.nextLine()) {
            out.println(anagramic(first, second) ? "YES" : "NO");
        }
    }
}
