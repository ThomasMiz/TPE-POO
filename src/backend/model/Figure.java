package backend.model;

import backend.Colorable;
import backend.Movable;
import javafx.scene.paint.Color;

public abstract class Figure implements Movable, Colorable {

    private final Point[] points;

    private double borderSize;
    private Color borderColor;
    private Color fillColor;

    protected Figure(Point[] points) {
        this.points = points;
    }

    public void setBorderSize(double borderSize) {
        if (borderSize < 0) throw new IllegalArgumentException("borderSize");
        this.borderSize = borderSize;
    }

    public double getBorderSize() {
        return borderSize;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    /**
     * Moves this figure by the specified amount.
     */
    public void move(double deltaX, double deltaY) {
        for (Point p : points)
            p.move(deltaX, deltaY);
    }

    /**
     * Returns whether a given point is contained within this Figure.
     */
    public abstract boolean contains(Point point);

    /**
     * Returns whether this Figure is fully contained within a given Rectangle.
     */
    public abstract boolean isContainedIn(Rectangle rectangle);
}
