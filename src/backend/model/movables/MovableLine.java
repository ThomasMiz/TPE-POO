package backend.model.movables;

import backend.model.Line;
import backend.model.Point;

public class MovableLine extends Line implements Movable {

    public MovableLine(Point from, Point to) {
        super(new MovablePoint(from.getX(), from.getY()), new MovablePoint(to.getX(), to.getY()));
    }

    @Override
    public void move(double deltaX, double deltaY) {
        ((MovablePoint) from).move(deltaX, deltaY);
        ((MovablePoint) to).move(deltaX, deltaY);
    }
}
