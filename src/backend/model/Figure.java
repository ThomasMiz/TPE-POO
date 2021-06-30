package backend.model;

public abstract class Figure {

    /** Returns whether a given point is contained within this Figure. */
    public abstract boolean contains(Point point);

    /** Moves this figure by the specified delta. */
    public abstract void move(double deltaX, double deltaY);
}
