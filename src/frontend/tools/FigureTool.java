package frontend.tools;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.Point;
import frontend.PaintPane;
import frontend.StatusPane;

public abstract class FigureTool {
    private final PaintPane paintPane;

    public FigureTool(PaintPane paintPane) {
        this.paintPane = paintPane;
    }

    public void createFigure(Point startPoint, Point endPoint) {
        try {
            Figure figure = createInstance(startPoint, endPoint);
            figure.setColorProperties(paintPane.getBorderSize(), paintPane.getBorderColor(), paintPane.getFillColor());
        }
        catch (Exception e){
            paintPane.getStatusPane().updateStatus(e.getMessage());
        }
    }

    protected abstract Figure createInstance(Point startPoint, Point endPoint);
}
