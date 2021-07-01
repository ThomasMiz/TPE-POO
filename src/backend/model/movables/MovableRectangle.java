package backend.model.movables;

import backend.model.Point;
import backend.model.Rectangle;

public class MovableRectangle extends Rectangle implements Movable {

    public MovableRectangle(Point pointA, Point pointB) {
        super(new MovablePoint(pointA.getX(), pointA.getY()), new MovablePoint(pointB.getX(), pointB.getY()));
    }

    @Override
    public void move(double deltaX, double deltaY) {
        ((MovablePoint) topLeft).move(deltaX, deltaY);
        ((MovablePoint) bottomRight).move(deltaX, deltaY);
    }
}
