package backend.model;

public class Line extends Figure {
    private final Point from, to;

    public Line(Point from, Point to) {
        super(new Point[]{from, to});
        this.from = from;
        this.to = to;
    }

    public Point getFrom() {
        return from;
    }

    public Point getTo() {
        return to;
    }

    public double length() {
        return from.distanceTo(to);
    }

    @Override
    public boolean contains(Point point) {
        return false;
    }

    @Override
    public boolean isContainedIn(Rectangle rectangle) {
        return rectangle.contains(from) && rectangle.contains(to);
    }

    @Override
    public String toString() {
        return String.format("Linea [Desde: %s, Hasta: %s]", from, to);
    }
}
