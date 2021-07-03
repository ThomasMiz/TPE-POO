package frontend.tools;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.Line;
import backend.model.Point;
import frontend.PaintPane;
import frontend.StatusPane;

public class LineTool extends FigureTool{

    public LineTool(PaintPane paintPane) {
        super(paintPane);
    }

    @Override
    public Figure createInstance(Point startPoint, Point endPoint) {
        return new Line(startPoint, endPoint);
    }

}
