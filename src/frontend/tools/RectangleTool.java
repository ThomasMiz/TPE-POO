package frontend.tools;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import frontend.PaintPane;
import frontend.StatusPane;

public class RectangleTool extends FigureByRectangleTool{

    public RectangleTool(PaintPane paintPane) {
        super(paintPane);
    }

    @Override
    public Figure createInstance(Point startPoint, Point endPoint) {
        return new Rectangle(startPoint, endPoint);
    }


}
