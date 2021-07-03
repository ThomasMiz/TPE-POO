package frontend.tools;

import backend.CanvasState;
import backend.model.Circle;
import backend.model.Figure;
import backend.model.Point;
import frontend.PaintPane;
import frontend.StatusPane;

public class CircleTool extends FigureTool{

    public CircleTool(PaintPane paintPane) {
        super(paintPane);
    }

    @Override
    public Figure createInstance(Point startPoint, Point endPoint) {
        double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
        return new Circle(startPoint, circleRadius);
    }
}