package com.radadev.applied;

import java.io.PrintStream;
import java.util.Scanner;

public class Bowling extends AppliedAlgorithm {

    private static int value(char c) {
        int result;
        result = (c == 'X') ? 10 : Character.digit(c, 10);
        if (result == -1) throw new IllegalArgumentException("c must be [0-9x]: " + c);
        return result;
    }

    private static int sum(char c, char d) {

        if (c == '/') throw new IllegalArgumentException("c cannot be '/'");
        if (!Character.isDigit(c) && d == '/') {
            throw new IllegalArgumentException("c must be a digit for d to be '/': " + c);
        }

        int sum = value(c);
        if (d == '/') {
            sum = 10;
        } else {
            sum += value(d);
            if (Character.isDigit(c) && sum >= 10) {
                throw new IllegalStateException("sum must be a spare to reach 10");
            }
        }
        return sum;
    }

    private static int score(String line) {
        int score = 0;
        int expected = 0;
        for (int i = 0, loc = 0; i < 10; expected = ++loc, ++i) {
            char c = line.charAt(loc);
            if (c == 'X') {
                score += 10 + sum(line.charAt(loc + 1), line.charAt(loc + 2));
            } else if (Character.isDigit(c)) {
                int ball = sum(c, line.charAt(++loc));
                if (ball == 10) ball += value(line.charAt(loc + 1));
                score += ball;
            } else {
                throw new IllegalStateException("c is invalid: " + c);
            }
        }
        switch (line.charAt(expected - 1)) {
            case 'X': ++expected;
            case '/': ++expected;
            default: if (line.length() != expected) {
                throw new IllegalStateException("too many frames");
            }
        }
        return score;
    }

    @Override
    protected void execute(Scanner in, PrintStream out) {
        for (String line = in.nextLine(); !line.equals("*"); line = in.nextLine()) {
            try {
                out.println(score(line));
            } catch (IllegalArgumentException | IllegalStateException | IndexOutOfBoundsException e) {
                out.println("INVALID");
            }
        }
    }
}
