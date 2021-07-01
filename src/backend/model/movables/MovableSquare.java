package backend.model.movables;

import backend.model.Point;
import backend.model.Square;

public class MovableSquare extends Square implements Movable {

    public MovableSquare(Point topLeft, Point bottomRight) {
        super(new MovablePoint(topLeft.getX(), topLeft.getY()), new Point(bottomRight.getX(), bottomRight.getY()));
    }

    @Override
    public void move(double deltaX, double deltaY) {
        ((MovablePoint) topLeft).move(deltaX, deltaY);
        ((MovablePoint) bottomRight).move(deltaX, deltaY);
    }
}
