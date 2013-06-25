package com.radadev.applied.utility;

public class Point implements Comparable<Point> {

    private final double x;
    private final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    private static String stripTrailingZeros(double d) {
        return Double.toString(d).replaceAll("\\.0+$", "");
    }

    public double distance(Point p) {
        return Math.sqrt(Math.pow(x - p.x, 2) + Math.pow(y - p.y, 2));
    }

    public double angle(Point p) {
        double angle = Math.atan2(p.y - y, p.x - x);
        if (angle < 0) angle = 2 * Math.PI + angle;
        return angle;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + stripTrailingZeros(x) + "," + stripTrailingZeros(y) + ")";
    }

    @Override
    public int compareTo(Point o) {
        int result = Double.compare(x, o.x);
        if (result == 0) result = Double.compare(y, o.y);
        return result;
    }
}


