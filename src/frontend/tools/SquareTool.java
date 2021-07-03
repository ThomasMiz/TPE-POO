package frontend.tools;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.Point;
import backend.model.Square;
import frontend.PaintPane;

public class SquareTool extends FigureTool {
    public SquareTool(CanvasState canvasState, PaintPane paintPane) {
        super(canvasState, paintPane);
    }

    @Override
    public Figure createInstance(Point startPoint, Point endPoint) {
        updatePoints(startPoint, endPoint);
        return new Square(startPoint, new Point(endPoint.getX(), startPoint.getY() + endPoint.getX() - startPoint.getX()));
    }

    private void updatePoints(Point startPoint, Point endPoint) {

    }
}


