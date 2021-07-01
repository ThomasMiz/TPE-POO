package backend.model;

public abstract class Figure {

    /**
     * Returns whether a given point is contained within this Figure.
     */
    public abstract boolean contains(Point point);

    /**
     * Returns whether this Figure is fully contained within a given Rectangle.
     */
    public abstract boolean isContainedIn(Rectangle rectangle);
}
