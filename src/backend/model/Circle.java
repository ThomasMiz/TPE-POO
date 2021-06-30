package backend.model;

public class Circle extends Ellipse {
    public Circle(Point center, double radius) {
        super(center, radius, radius);
    }

    public double getRadius() {
        return getRadiusX();
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", getCenter(), getRadius());
    }
}
