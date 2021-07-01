package frontend;

import backend.model.Line;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;

public class DrawableLine extends Line implements DrawableFigure {

    public DrawableLine(Point from, Point to) {
        super(from, to);
    }

    @Override
    public void redrawFigure(GraphicsContext gc) {
        gc.strokeLine(getFrom().getX(), getFrom().getY(), getTo().getX(), getTo().getY());
    }
}
