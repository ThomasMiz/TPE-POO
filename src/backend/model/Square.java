package backend.model;

public class Square extends Rectangle {

    /**
     * Creates a Square from a topLeft point and it's desired size.
     */
    public Square(Point topLeft, double size) {
        super(topLeft, new Point(topLeft.getX() + size, topLeft.getY() + size));
    }

    @Override
    public String toString() {
        return String.format("Square [topLeft: %s, size: %.2f]", getTopLeft(), width());
    }
}