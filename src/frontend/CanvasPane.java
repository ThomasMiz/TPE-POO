package frontend;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

public class CanvasPane extends Pane {

    private final Canvas canvas;

    public CanvasPane(double width, double height) {
        setWidth(width);
        setHeight(height);
        canvas = new Canvas(width, height);
        canvas.widthProperty().bind(this.widthProperty());
        canvas.heightProperty().bind(this.heightProperty());
        getChildren().add(canvas);
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
