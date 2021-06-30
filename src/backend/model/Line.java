package backend.model;

public class Line extends Figure {
    private final Point from, to;

    public Line(Point from, Point to) {
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
    public void move(double deltaX, double deltaY) {
        from.move(deltaX, deltaY);
        to.move(deltaX, deltaY);
    }

    @Override
    public String toString() {
        return String.format("Linea [Desde: %s, Hasta: %s]", from, to);
    }
}
