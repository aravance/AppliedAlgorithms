package com.radadev.applied;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Gopher extends AppliedAlgorithm {

    @Override
    void execute(Scanner in, PrintStream out) {
        List<Location> holes = new ArrayList<>(1000);
        for (int c = in.nextInt(); c != 0; c = in.nextInt()) {

            Location gopher = new Location(in.nextDouble(), in.nextDouble());
            Location dog = new Location(in.nextDouble(), in.nextDouble());

            for (int i = 0; i < c; ++i) {
                holes.add(new Location(in.nextDouble(), in.nextDouble()));
            }

            Location escape = null;
            for (int i = 0; escape == null && i < holes.size(); ++i) {
                Location hole = holes.get(i);
                if (gopher.distance(hole) * 2 <= dog.distance(hole)) escape = hole;
            }

            if (escape == null) {
                out.println("The gopher cannot escape.");
            } else {
                out.println("The gopher can escape through the hole at " + escape + ".");
            }

            holes.clear();
        }
    }

    private static class Location {

        private double x;
        private double y;

        public Location(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double distance(Location o) {
            return Math.sqrt(Math.pow(x - o.x, 2) + Math.pow(y - o.y, 2));
        }

        @Override
        public String toString() {
            return String.format("(%.3f,%.3f)", x, y);
        }
    }
}
