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
        double dx = endPoint.getX() - startPoint.getX();
        double dy = endPoint.getY() - startPoint.getY();
        double size = Math.min(Math.abs(dx), Math.abs(dy));

        Point topLeft = new Point(
                startPoint.getX() - (dx < 0 ? size : 0),
                startPoint.getY() - (dy < 0 ? size : 0)
        );

        return new Square(topLeft, size);
    }
}
