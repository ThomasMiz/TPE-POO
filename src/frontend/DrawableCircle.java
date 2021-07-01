package frontend;

import backend.model.Circle;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;

public class DrawableCircle extends Circle implements DrawableFigure {

    public DrawableCircle(Point center, double radius) {
        super(center, radius);
    }

    @Override
    public void redrawFigure(GraphicsContext gc) {
        gc.fillOval(getCenter().getX() - getRadius(), getCenter().getY() - getRadius(), getRadius()*2, getRadius()*2);

        gc.strokeOval(getCenter().getX() - getRadius(), getCenter().getY() - getRadius(), getRadius()*2, getRadius()*2);
    }
}
