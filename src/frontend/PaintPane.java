package frontend;

import backend.CanvasState;
import backend.model.Circle;
import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import frontend.tools.*;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.*;

public class PaintPane extends BorderPane {

	// BackEnd
	private final CanvasState canvasState;

	// Canvas y relacionados
	private final Canvas canvas = new Canvas(800, 600);
	private final GraphicsContext gc = canvas.getGraphicsContext2D();
	private final Color borderColor = Color.BLACK;
	private final Color fillColor = Color.YELLOW;
	private final double borderSize = 1;

	// Botones Barra Izquierda
	private final ToggleButton selectionButton = new ToggleButton("Seleccionar");
	private final ToggleButton rectangleButton = new ToggleButton("Rectángulo");
	private final ToggleButton circleButton = new ToggleButton("Círculo");
	private final ToggleButton squareButton = new ToggleButton("Cuadrado");
	private final ToggleButton ellipseButton = new ToggleButton("Elipse");
	private final ToggleButton lineButton = new ToggleButton("Línea");
	private final ToggleButton deleteButton = new ToggleButton("Borrar");
	private final ToggleButton toBottomButton = new ToggleButton("Al Fondo");
	private final ToggleButton toFrontButton = new ToggleButton("Al Frente");
	private final Slider borderSlider = new Slider(1, 50, 1);
	private final ColorPicker borderColorPicker = new ColorPicker(borderColor);
	private final ColorPicker fillColorPicker = new ColorPicker(fillColor);

	//Last startPoint after mouse pressed
	private Point startPoint;

	// Collection of selected figures
	private final Set<Figure> selectedFigures = new HashSet<>();

	// StatusBar
	private StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;

		//ButtonGroup
		ToggleButton[] figureToolsArr = {lineButton, squareButton, rectangleButton, circleButton, ellipseButton};
		ToggleButton[] functionalitiesToolsArr = {selectionButton, deleteButton, toFrontButton, toBottomButton};

		ToggleGroup figureToolsGroup = new ToggleGroup();
		ToggleGroup functionalitiesToolsGroup = new ToggleGroup();
		for (ToggleButton tool : figureToolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(figureToolsGroup);
			tool.setCursor(Cursor.HAND);
		}
		for (ToggleButton tool : functionalitiesToolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(functionalitiesToolsGroup);
			tool.setCursor(Cursor.HAND);
		}

		setButtonsData();
		//Interface
		borderSlider.setShowTickMarks(true);
		borderSlider.setShowTickLabels(true);
		Label borderLabel = new Label();
		Label fillLabel = new Label();
		borderLabel.textProperty().bind(Bindings.format("Borde:\t%.2f", borderSlider.valueProperty()));
		fillLabel.textProperty().setValue("Relleno: ");

		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(functionalitiesToolsArr);
		buttonsBox.getChildren().addAll(figureToolsArr);
		buttonsBox.getChildren().addAll(borderLabel, borderSlider, borderColorPicker, fillLabel, fillColorPicker);
		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);
		setLeft(buttonsBox);
		setRight(canvas);

		//Mouse events
		canvas.setOnMousePressed(event -> {
			startPoint = new Point(event.getX(), event.getY());
		});

		canvas.setOnMouseReleased(event -> {
			Point endPoint = new Point(event.getX(), event.getY());
			Toggle selectedButton = figureToolsGroup.getSelectedToggle();
			if(selectedButton != null) {
				((FigureTool) selectedButton.getUserData()).createFigure(startPoint, endPoint);
			}
			startPoint = null;
		});

		canvas.setOnMouseMoved(event -> { //realizado uwu
			Point eventPoint = new Point(event.getX(), event.getY());
			Figure topFigure = canvasState.getFigureAt(eventPoint);
			if(topFigure!=null) {
				statusPane.updateStatus(topFigure.toString());
			}
			else {
				statusPane.updateStatus(eventPoint.toString());
			}
		});

		/*canvas.setOnMouseClicked(event -> {
			Point clickedPoint = new Point(event.getX(), event.getY());
			//Toggle selectedButton = tools.getSelectedToggle();
			if(selectionButton.isSelected()) {
				//TODO seleccion múltiple y simple diferencia rectangulos y click en el mismo lugar
				redrawCanvas();
			}
		});*/

		/*canvas.setOnMouseDragged(event -> {
			if(selectionButton.isSelected() && !selectedFigures.isEmpty()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
				double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
				selectedFigure.move(diffX,diffY);
				redrawCanvas();
			}
		});*/

	}

	private void setButtonsData() {
		rectangleButton.setUserData(new RectangleTool(canvasState, this));
		circleButton.setUserData(new CircleTool(canvasState, this));
		ellipseButton.setUserData(new EllipseTool(canvasState, this));
		squareButton.setUserData(new SquareTool(canvasState, this));
		lineButton.setUserData(new LineTool(canvasState, this));
		//selectionButton.setUserData(new SelectionTool(canvasState, statusPane));
		//deleteButton.setUserData(new DeleteTool(canvasState, statusPane));
		//toBottomButton.setUserData(new ToBottomTool(canvasState, statusPane));
		//toFrontButton.setUserData(new ToFrontTool(canvasState, statusPane));
	}

	public void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(Figure figure : canvasState) {
			figure.draw(gc);
		}
	}

	public Color getFillColor() {
		return fillColor;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public double getBorderSize() {
		return borderSize;
	}

	public StatusPane getStatusPane() {
		return statusPane;
	}
}