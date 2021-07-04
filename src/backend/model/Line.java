package backend.model;

import javafx.scene.canvas.GraphicsContext;

public class Line extends Figure {
    private final Point from, to;

    public Line(Point from, Point to) {
        super(new Point[]{from, to});
        if (from.equals(to))
            throw new IllegalArgumentException("Una línea no puede empezr y terminar en el mismo punto");
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
    public void draw(GraphicsContext gc) {
        super.draw(gc);
        gc.strokeLine(from.getX(), from.getY(), to.getX(), to.getY());
    }

    @Override
    public String toString() {
        return String.format("Linea [ %s, %s ]", from, to);
    }
}
