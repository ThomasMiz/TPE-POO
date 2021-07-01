package backend.model.movables;

import backend.model.Point;

public class MovablePoint extends Point implements Movable {

    public MovablePoint(double x, double y) {
        super(x, y);
    }

    @Override
    public void move(double deltaX, double deltaY) {
        x += deltaX;
        y += deltaY;
    }
}
