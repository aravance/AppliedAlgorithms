package com.radadev.applied;

import java.io.PrintStream;
import java.util.*;

public class Pancakes extends AppliedAlgorithm {

    private String flip(String pancakes, int c) {
        char[] buffer = pancakes.toCharArray();
        for (int i = pancakes.length() - 1, j = pancakes.length() - c; j < pancakes.length(); --i, ++j) {
            char pancake = pancakes.charAt(i);
            if (Character.isUpperCase(pancake)) {
                pancake = Character.toLowerCase(pancake);
            } else {
                pancake = Character.toUpperCase(pancake);
            }
            buffer[j] = pancake;
        }
        return new String(buffer);
    }

    private boolean isSolved(String pancakes) {
        boolean solved = true;
        char last = 'A';
        for (int i = 0; solved && i < pancakes.length(); ++i) {
            char next = pancakes.charAt(i);
            solved = Character.isUpperCase(next) && next >= last;
            last = next;
        }
        return solved;
    }

    private int countFlips(String stack) {
        Set<String> visited = new HashSet<>();
        Queue<State> bfs = new LinkedList<>();
        visited.add(stack);
        bfs.add(new State(0, stack));
        while (!bfs.isEmpty() && !isSolved(bfs.peek().pancakes)) {
            State state = bfs.poll();
            for (int i = 1; i <= state.pancakes.length(); ++i) {
                String flipped = flip(state.pancakes, i);
                if (!visited.contains(flipped)) {
                    bfs.add(new State(state.flips + 1, flipped));
                    visited.add(flipped);
                }
            }
        }
        return bfs.poll().flips;
    }

    @Override
    protected void execute(Scanner in, PrintStream out) {
        for (String line = in.nextLine(); !line.equals("0"); line = in.nextLine()) {
            out.println(countFlips(line));
        }
    }

    private static class State implements Comparable<State> {

        int flips;
        String pancakes;

        private State(int flips, String pancakes) {
            this.flips = flips;
            this.pancakes = pancakes;
        }

        @Override
        public int compareTo(State o) {
            int result = Integer.compare(flips, o.flips);
            if (result == 0) result = pancakes.compareTo(o.pancakes);
            return result;
        }

        @Override
        public String toString() {
            return "State{" +
                    "flips=" + flips +
                    ", pancakes='" + pancakes + '\'' +
                    '}';
        }
    }
}
