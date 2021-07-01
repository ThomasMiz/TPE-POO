package frontend;

import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawableRectangle extends Rectangle implements DrawableFigure {

    //Podriamos hacer un metodo isSelected() que te dice si esta o no seleccionada la figura
    public DrawableRectangle(Point pointA, Point pointB) {
        super(pointA, pointB);
    }

    @Override
    public void redrawFigure(GraphicsContext gc) {

        gc.fillRect(getTopLeft().getX(), getTopLeft().getY(), Math.abs(getTopLeft().getX() - getBottomRight().getX()), Math.abs(getTopLeft().getY() - getBottomRight().getY()));

        gc.strokeRect(getTopLeft().getX(), getTopLeft().getY(), Math.abs(getTopLeft().getX() - getBottomRight().getX()), Math.abs(getTopLeft().getY() - getBottomRight().getY()));
    }
}
