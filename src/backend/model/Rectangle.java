package backend.model;

public class Rectangle extends Figure {

    private final Point topLeft, bottomRight;

    public Rectangle(Point pointA, Point pointB) {
        topLeft = new Point(Math.min(pointA.getX(), pointB.getX()), Math.min(pointA.getY(), pointB.getY()));
        bottomRight = new Point(Math.max(pointA.getX(), pointB.getX()), Math.max(pointA.getY(), pointB.getY()));
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public double width() {
        return bottomRight.getX() - topLeft.getX();
    }

    public double height() {
        return bottomRight.getY() - topLeft.getY();
    }

    @Override
    public boolean contains(Point point) {
        return topLeft.getX() <= point.getX() && point.getX() <= bottomRight.getX() &&
                topLeft.getY() <= point.getY() && point.getY() <= bottomRight.getY();
    }

    /**
     * Returns true if the given rectangle is fully contained within this rectangle.
     */
    public boolean contains(Rectangle other) {
        return topLeft.getX() <= other.topLeft.getX() && other.bottomRight.getX() <= bottomRight.getX()
                && topLeft.getY() <= other.topLeft.getY() && other.bottomRight.getY() <= bottomRight.getY();
    }

    @Override
    public boolean isContainedIn(Rectangle rectangle) {
        return rectangle.contains(this);
    }

    @Override
    public void move(double deltaX, double deltaY) {
        topLeft.move(deltaX, deltaY);
        bottomRight.move(deltaX, deltaY);
    }

    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", topLeft, bottomRight);
    }
}
