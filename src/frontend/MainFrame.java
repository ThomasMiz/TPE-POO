package frontend;

import backend.CanvasState;
import javafx.scene.layout.BorderPane;

public class MainFrame extends BorderPane {

    public MainFrame(CanvasState canvasState) {
        StatusPane statusPane = new StatusPane();
        PaintPane paintPane = new PaintPane(canvasState, statusPane);
        paintPane.setPrefWidth(600);

        setTop(new AppMenuBar(paintPane));
        setCenter(paintPane);
        setBottom(statusPane);
    }
}
