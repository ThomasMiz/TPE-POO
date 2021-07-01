package backend.model.movables;

public interface Movable {

    /**
     * Moves this figure by the specified delta.
     */
    void move(double deltaX, double deltaY);
}
