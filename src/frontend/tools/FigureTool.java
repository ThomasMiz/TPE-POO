package frontend.tools;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.Point;
import frontend.PaintPane;

public abstract class FigureTool {
    private final CanvasState canvasState;
    private final PaintPane paintPane;

    public FigureTool(CanvasState canvasState,PaintPane paintPane) {
        this.canvasState = canvasState;
        this.paintPane = paintPane;
    }

    public void createFigure(Point startPoint, Point endPoint) {
        try {
            Figure figure = createInstance(startPoint, endPoint);
            figure.setColorableProperties(paintPane.getBorderSize(), paintPane.getBorderColor(), paintPane.getFillColor());
            canvasState.addFigure(figure);
            paintPane.redrawCanvas();
        }
        catch (Exception e){
            paintPane.getStatusPane().updateStatus(e.getMessage());
        }
    }

    protected abstract Figure createInstance(Point startPoint, Point endPoint);
}
