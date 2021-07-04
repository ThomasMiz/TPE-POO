package backend.model;

import backend.Movable;

public class Point implements Movable {
    private double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /**
     * Calculates the square of the distance between this point and another point.
     */
    public double distanceSquaredTo(Point other) {
        double dx = x - other.x;
        double dy = y - other.y;
        return dx * dx + dy * dy;
    }

    /**
     * Calculates the distance between this point and another point.
     */
    public double distanceTo(Point other) {
        return Math.sqrt(distanceSquaredTo(other));
    }

    @Override
    public void move(double deltaX, double deltaY) {
        x += deltaX;
        y += deltaY;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Point))
            return false;
        Point o = (Point)other;
        return x == o.x && y == o.y;
    }

    @Override
    public String toString() {
        return String.format("{%.2f , %.2f}", x, y);
    }
}
