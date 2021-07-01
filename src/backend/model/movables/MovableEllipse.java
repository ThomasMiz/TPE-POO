package backend.model.movables;

import backend.model.Ellipse;
import backend.model.Point;

public class MovableEllipse extends Ellipse implements Movable {

    public MovableEllipse(Point center, double radiusX, double radiusY) {
        super(new MovablePoint(center.getX(), center.getY()), radiusX, radiusY);
    }

    @Override
    public void move(double deltaX, double deltaY) {
        ((MovablePoint) center).move(deltaX, deltaY);
    }
}
