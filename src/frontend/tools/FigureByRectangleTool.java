package frontend.tools;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.Point;
import frontend.PaintPane;

public abstract class FigureByRectangleTool extends FigureTool {

    public FigureByRectangleTool(PaintPane paintPane) {
        super(paintPane);
    }


    protected void updatePoints(Point startPoint, Point endPoint) {
        if(!(startPoint.getX() < endPoint.getX() && startPoint.getY() > endPoint.getY())) {
            Point auxStart = new Point(startPoint.getX(), startPoint.getY());
            if(startPoint.getX() < endPoint.getX()) {
                if(startPoint.getY() < endPoint.getY()) {
                    startPoint.move(0, endPoint.getY() - auxStart.getY());
                    endPoint.move(0, auxStart.getY() - endPoint.getY());
                }
            }
            else if(startPoint.getX() > endPoint.getX()) {
                if(startPoint.getY() < endPoint.getY()) {
                    startPoint.move(endPoint.getX() - auxStart.getX(), endPoint.getY() - auxStart.getY());
                    endPoint.move(auxStart.getX() - endPoint.getX(), auxStart.getY() - endPoint.getY());
                }
                else if (startPoint.getY() > endPoint.getY()) {
                    startPoint.move(endPoint.getX() - auxStart.getX(), 0);
                    endPoint.move(auxStart.getX() - endPoint.getX(), 0);
                }
            }
        }
    }
}
