package com.radadev.applied;

import com.radadev.applied.utility.Utils;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Semigroup extends AppliedAlgorithm {

    private static String toSetString(char[] set) {
        StringBuilder builder = new StringBuilder("S = {");
        if (set.length > 0) builder.append(set[0]);
        for (int i = 1; i < set.length; ++i) {
            builder.append(",").append(set[i]);
        }
        return builder.append("}").toString();
    }

    private static void printCayleyTable(PrintStream out, char[] set, Map<Character, Map<Character, Character>> cayley) {
        out.println(" #|" + new String(set));
        out.println(" -+" + Utils.repeat("-", set.length));
        for (char first : set) {
            out.print(" " + first + "|");
            for (char second : set) {
                out.print(cayley.get(first).get(second));
            }
            out.println();
        }
    }

    private static boolean contains(char[] set, char c) {
        boolean found = false;
        for (int i = 0; !found && i < set.length; ++i) {
            found = set[i] == c;
        }
        return found;
    }

    private static String evaluateGroup(char[] set, Map<Character, Map<Character, Character>> cayley) {

        for (char first : set) {
            for (char second : set) {
                if (!contains(set, cayley.get(first).get(second))) {
                    return "NOT A SEMIGROUP: " + first + "#" + second + " = " + cayley.get(first).get(second)
                            + " WHICH IS NOT AN ELEMENT OF THE SET";
                }
            }
        }

        for (char first : set) {
            for (char second : set) {
                for (char third : set) {
                    if (cayley.get(cayley.get(first).get(second)).get(third) !=
                            cayley.get(first).get(cayley.get(second).get(third))) {
                        return "NOT A SEMIGROUP: (" + first + "#" + second + ")#" + third +
                                " IS NOT EQUAL TO " + first + "#(" + second + "#" + third + ")";
                    }
                }
            }
        }

        for (int i = 0; i < set.length; ++i) {
            char first = set[i];
            for (int j = i + 1; j < set.length; ++j) {
                char second = set[j];
                if (cayley.get(first).get(second) != cayley.get(second).get(first)) {
                    return "SEMIGROUP BUT NOT COMMUTATIVE (" + first + "#" + second +
                            " IS NOT EQUAL TO " + second + "#" + first + ")";
                }
            }
        }

        return "COMMUTATIVE SEMIGROUP";
    }

    @Override
    protected void execute(Scanner in, PrintStream out) {
        for (int count = in.nextInt(); count != 0; count = in.nextInt()) {
            String line = in.next();
            char[] set = line.toCharArray();
            Map<Character, Map<Character, Character>> cayley = new HashMap<>();
            for (char c : set) {
                line = in.next();
                if (!cayley.containsKey(c)) cayley.put(c, new HashMap<Character, Character>());
                Map<Character, Character> subset = cayley.get(c);
                for (int i = 0; i < set.length; ++i) {
                    subset.put(set[i], line.charAt(i));
                }
            }
            out.println(toSetString(set));
            printCayleyTable(out, set, cayley);
            out.println();
            out.println(evaluateGroup(set, cayley));
            out.println(Utils.repeat("-", 30));
            out.println();
        }
    }

    @Override
    protected String getFileNameBase() {
        return "semigrp";
    }
}
