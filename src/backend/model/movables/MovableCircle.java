package backend.model.movables;

import backend.model.Circle;
import backend.model.Point;

public class MovableCircle extends Circle implements Movable {
    public MovableCircle(Point center, double radius) {
        super(new MovablePoint(center.getX(), center.getY()), radius);
    }

    @Override
    public void move(double deltaX, double deltaY) {
        ((MovablePoint) center).move(deltaX, deltaY);
    }
}
