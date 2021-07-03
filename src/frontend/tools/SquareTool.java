package frontend.tools;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.Point;
import backend.model.Square;
import frontend.PaintPane;
import frontend.StatusPane;

public class SquareTool extends FigureByRectangleTool {
    public SquareTool(PaintPane paintPane) {
        super(paintPane);
    }

    @Override
    public Figure createInstance(Point startPoint, Point endPoint) {
        return new Square(startPoint, new Point(endPoint.getX(), startPoint.getY() + endPoint.getX() - startPoint.getX()));
    }
}
