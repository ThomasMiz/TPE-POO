package backend.model;

import javafx.scene.canvas.GraphicsContext;

public class Rectangle extends Figure {

    private final Point topLeft, bottomRight;

    public Rectangle(Point topLeft, Point bottomRight) {
        super(new Point[]{topLeft, bottomRight});

        if (topLeft.getX() == bottomRight.getX())
            throw new IllegalArgumentException("El ancho de un rectángulo debe ser mayor que 0");
        if (topLeft.getY() == bottomRight.getY())
            throw new IllegalArgumentException("El alto de un rectángulo debe ser mayor que 0");
        if (topLeft.getX() > bottomRight.getX())
            throw new IllegalArgumentException("topLeft must be on the left of bottomRight");
        if (topLeft.getY() > bottomRight.getY())
            throw new IllegalArgumentException("topLeft must be above bottomRight");

        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
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
    public void draw(GraphicsContext gc) {
        super.draw(gc);
        gc.fillRect(topLeft.getX(), topLeft.getY(), width(), height());
        gc.strokeRect(topLeft.getX(), topLeft.getY(), width(), height());
    }

    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", topLeft, bottomRight);
    }

    /**
     * Creates a Rectangle from the two specified points, no matter their orientation.
     * Exceptions will still be thrown if the rectangle specified by those points has an area of 0
     */
    public static Rectangle from(Point pointA, Point pointB) {
        return new Rectangle(
                new Point(Math.min(pointA.getX(), pointB.getX()), Math.min(pointA.getY(), pointB.getY())),
                new Point(Math.max(pointA.getX(), pointB.getX()), Math.max(pointA.getY(), pointB.getY()))
        );
    }
}
