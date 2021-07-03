package frontend.tools;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import frontend.PaintPane;

public class RectangleTool extends FigureTool{

    public RectangleTool(CanvasState canvasState, PaintPane paintPane) {
        super(canvasState, paintPane);
    }

    @Override
    public Figure createInstance(Point startPoint, Point endPoint) {
        updatePoints(startPoint, endPoint);
        return new Rectangle(startPoint, endPoint);
    }

    private void updatePoints(Point startPoint, Point endPoint) {
        double topLeftX = Math.min(startPoint.getX(), endPoint.getX());
        double topLeftY = Math.min(startPoint.getY(), endPoint.getY());

        double bottomRightX = Math.max(startPoint.getX(), endPoint.getX());
        double bottomRightY = Math.max(startPoint.getY(), endPoint.getY());

        startPoint.move(topLeftX - startPoint.getX(), topLeftY - startPoint.getY());
        endPoint.move(bottomRightX - endPoint.getX(), bottomRightY - endPoint.getY());
    }
}