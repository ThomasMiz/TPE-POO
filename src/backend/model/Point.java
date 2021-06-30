package backend.model;

public class Point {
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

    /** Calculates the square of the distance between this point and another point. */
    public double distanceSquaredTo(Point other) {
        double dx = x - other.x;
        double dy = y - other.y;
        return x * x + y * y;
    }

    /** Calculates the distance between this point and another point. */
    public double distanceTo(Point other){
        return Math.sqrt(distanceSquaredTo(other));
    }

    public void move(double deltaX, double deltaY) {
        x += deltaX;
        y += deltaY;
    }

    @Override
    public String toString() {
        return String.format("{%.2f , %.2f}", x, y);
    }

}
