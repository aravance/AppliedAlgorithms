package com.radadev.applied;

import com.radadev.applied.utility.Point;

import java.io.PrintStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Convex extends AppliedAlgorithm {

    @Override
    protected void execute(Scanner in, PrintStream out) {
        int n = 1;
        for (int c = in.nextInt(); c != 0; c = in.nextInt()) {
            List<Point> points = new LinkedList<>();
            for (int i = 0; i < c; ++i) {
                points.add(new Point(in.nextDouble(), in.nextDouble()));
            }

            out.print("Set #" + n++ + ":");
            for (Point point : new Hull(points)) {
                out.print(" " + point);
            }
            out.println();
        }
    }

    private static class Hull implements Iterable<Point> {

        private List<Point> mPoints;

        public Hull(List<Point> points) {
            mPoints = points;
        }

        private static double normalizeAngle(double angle) {
            angle = Math.PI / 2 - angle;
            if (angle < 0) angle += 2 * Math.PI;
            if (angle >= 2 * Math.PI) angle -= 2 * Math.PI;
            return angle;
        }

        @Override
        public Iterator<Point> iterator() {
            return new HullIterator(mPoints);
        }

        private static class HullIterator implements Iterator<Point> {

            private final List<Point> mPoints;
            private final Point mFirst;
            private Point mPrevious;
            private double mAngle = 0;

            public HullIterator(List<Point> points) {
                mPoints = new LinkedList<>(points);
                Collections.sort(mPoints);
                mFirst = mPoints.remove(0);
            }

            @Override
            public boolean hasNext() {
                Point next = getNext();
                return mPrevious == null || (next != null && next != mFirst);
            }

            @Override
            public Point next() {
                Point next = getNext();
                if (mPrevious != null) {
                    mAngle = normalizeAngle(mPrevious.angle(next));
                    mPoints.remove(next);
                }
                return mPrevious = next;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            private Point getNext() {
                if (mPrevious == null) return mFirst;

                Point next = mFirst;
                for (Point point : mPoints) {
                    double angle = normalizeAngle(mPrevious.angle(point));
                    if (angle >= mAngle) {
                        int compare = Double.compare(angle, normalizeAngle(mPrevious.angle(next)));
                        if (compare < 0 ||
                                (compare == 0 && mPrevious.distance(point) > mPrevious.distance(next))) {
                            next = point;
                        }
                    }
                }
                return next;
            }
        }
    }
}
