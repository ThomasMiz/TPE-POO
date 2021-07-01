package frontend;

import backend.model.Ellipse;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;

public class DrawableEllipse extends Ellipse implements DrawableFigure {

    public DrawableEllipse(Point center, double radiusX, double radiusY) {
        super(center, radiusX, radiusY);
    }

    @Override
    public void redrawFigure(GraphicsContext gc) {
        gc.fillOval(getCenter().getX() - getRadiusX(), getCenter().getY() - getRadiusY(), getRadiusX()*2, getRadiusY()*2);

        gc.strokeOval(getCenter().getX() - getRadiusX(), getCenter().getY() - getRadiusY(), getRadiusX()*2, getRadiusY()*2);
    }
}
