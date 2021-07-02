package backend.model;

import javafx.scene.canvas.GraphicsContext;

public class Ellipse extends Figure {
    protected final Point center;
    private final double radiusX, radiusY;

    public Ellipse(Point center, double radiusX, double radiusY) {
        super(new Point[]{center});
        if (radiusX <= 0) throw new IllegalArgumentException("radiusX must be greater than 0");
        if (radiusY <= 0) throw new IllegalArgumentException("radiusY must be greater than 0");
        this.center = center;
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
        Point tl = rectangle.getTopLeft();
        Point br = rectangle.getBottomRight();
        return tl.getX() <= center.getX() - radiusX && center.getX() + radiusX <= br.getX()
                && tl.getY() <= center.getY() - radiusY && center.getY() + radiusY <= br.getY();
    }

    @Override
    public void draw(GraphicsContext gc) {
        super.draw(gc);
        // TODO
    }

    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, RadioX: %.2f, RadioY: %.2f]", center, radiusX, radiusY);
    }
}
