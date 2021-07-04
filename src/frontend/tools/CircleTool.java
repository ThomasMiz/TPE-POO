package frontend.tools;

import backend.CanvasState;
import backend.model.Circle;
import backend.model.Figure;
import backend.model.Point;
import frontend.PaintPane;

public class CircleTool extends FigureTool {

    public CircleTool(CanvasState canvasState, PaintPane paintPane) {
        super(canvasState, paintPane);
    }

    @Override
    public Figure createInstance(Point startPoint, Point endPoint) {
        return new Circle(startPoint, startPoint.distanceTo(endPoint));
    }
}