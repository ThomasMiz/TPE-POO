package frontend.tools;

import backend.CanvasState;
import backend.model.Ellipse;
import backend.model.Figure;
import backend.model.Point;
import frontend.PaintPane;

public class EllipseTool extends FigureTool {
    public EllipseTool(CanvasState canvasState, PaintPane paintPane) {
        super(canvasState, paintPane);
    }

    @Override
    public Figure createInstance(Point startPoint, Point endPoint) {
        Point center = new Point((endPoint.getX() + startPoint.getX())/2, (endPoint.getY() + startPoint.getY())/2);
        double radiusX = Math.abs(endPoint.getX() - center.getX());
        double radiusY = Math.abs(endPoint.getY() - center.getY());
        return new Ellipse(center, radiusX, radiusY);
    }
}