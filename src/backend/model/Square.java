package backend.model;

public class Square extends Rectangle {

    /**
     * Creates a Square from a topLeft point and it's desired size.
     */
    public Square(Point topLeft, double size) {
        super(topLeft, new Point(topLeft.getX() + size, topLeft.getY() + size));
    }

    public Square(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight);
        if (bottomRight.getX() - topLeft.getX() != bottomRight.getY() - topLeft.getY())
            throw new IllegalArgumentException("A square's points must have the same distance in both axis");
    }

    @Override
    public String toString() {
        return String.format("Square [topLeft: %s, size: %.2f]", getTopLeft(), width());
    }
}
