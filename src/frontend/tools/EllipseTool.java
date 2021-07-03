package frontend.tools;

import backend.CanvasState;
import backend.model.Ellipse;
import backend.model.Figure;
import backend.model.Point;
import frontend.PaintPane;
import frontend.StatusPane;

public class EllipseTool extends FigureByRectangleTool{
    public EllipseTool(PaintPane paintPane) {
        super(paintPane);
    }

    @Override
    public Figure createInstance(Point startPoint, Point endPoint) {
        updatePoints(startPoint, endPoint);
        Point center = new Point((endPoint.getX()-startPoint.getX())/2, (endPoint.getY()-startPoint.getY())/2);
        double radiusX = endPoint.getX() - center.getX();
        double radiusY = endPoint.getY() - center.getY();
        return new Ellipse(center, radiusX, radiusY);
    }

}
