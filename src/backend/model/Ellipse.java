package backend.model;

public class Ellipse extends Figure {
    private final Point center;
    private final double radiusX, radiusY;

    public Ellipse(Point center, double radiusX, double radiusY) {
        if (radiusX <= 0) throw new IllegalArgumentException("radiusX must be greater than 0");
        if (radiusY <= 0) throw new IllegalArgumentException("radiusY must be greater than 0");
        this.center = new Point(center.getX(), center.getY());
        this.radiusX = radiusX;
        this.radiusY = radiusY;
    }

    public Point getCenter() {
        return center;
    }

    public double getRadiusX() {
        return radiusX;
    }

    public double getRadiusY() {
        return radiusY;
    }

    @Override
    public boolean contains(Point point) {
        double dx = (center.getX() - point.getX()) / radiusX;
        double dy = (center.getY() - point.getY()) / radiusY;
        return dx * dx + dy * dy <= 1;
    }

    @Override
    public boolean isContainedIn(Rectangle rectangle) {
        return false; // TODO
    }

    @Override
    public void move(double deltaX, double deltaY) {
        center.move(deltaX, deltaY);
    }

    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, RadioX: %.2f, RadioY: %.2f]", center, radiusX, radiusY);
    }
}