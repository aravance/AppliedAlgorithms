package com.radadev.applied;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Sync extends AppliedAlgorithm {

    private static final int MAX = 24 * 60 * 60;

    private static String toTimeString(int seconds) {

        int minutes = seconds / 60;
        int hours = minutes / 60;

        minutes %= 60;
        seconds %= 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private static boolean isGreen(int time, int cycle) {
        return time % (2 * cycle) < cycle - 5;
    }

    private static boolean allGreen(int time, int[] cycles) {
        for (int cycle : cycles) {
            if (!isGreen(time, cycle)) return false;
        }
        return true;
    }

    private static int findSyncTime(int[] lights) throws TimeoutException {
        Arrays.sort(lights);
        final int cycle = lights[0];

        for (int time = cycle * 2; time <= MAX; time += cycle * 2) {
            for (int tick = time; tick <= MAX && tick < time + cycle - 5; ++tick) {
                if (allGreen(tick, lights)) return tick;
            }
        }

        throw new TimeoutException("??:??:??");
    }

    @Override
    protected void execute(Scanner in, PrintStream out) {
        for (int c = in.nextInt(); c != 0; c = in.nextInt()) {
            int[] lights = new int[c];
            for (int i = 0; i < c; ++i) {
                lights[i] = in.nextInt();
            }

            try {
                out.println(toTimeString(findSyncTime(lights)));
            } catch (TimeoutException e) {
                out.println("??:??:??");
            }
        }
    }
}
