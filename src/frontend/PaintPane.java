package frontend;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import frontend.tools.*;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

	private static final Color SELECTED_FIGURE_BORDER_COLOR = new Color(0.4, 0.4, 1, 1);

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
	private final Button deleteButton = new Button("Borrar");
	private final Button toBottomButton = new Button("Al Fondo");
	private final Button toTopButton = new Button("Al Frente");
	private final Slider borderSlider = new Slider(1, 50, 1);
	private final ColorPicker borderColorPicker = new ColorPicker(borderColor);
	private final ColorPicker fillColorPicker = new ColorPicker(fillColor);

	//Last startPoint after mouse pressed
	private Point startPoint;

	// Collection of selected figures
	private final Set<Figure> selectedFigures = new HashSet<>();

	// StatusBar
	private final StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;

		//Buttons
		ToggleButton[] figureAndSelectionToolsArr = {selectionButton, lineButton, squareButton, rectangleButton, circleButton, ellipseButton};
		ButtonBase[] functionalitiesToolsArr = {deleteButton, toBottomButton, toTopButton};

		ToggleGroup figureAndSelectionToolsGroup = new ToggleGroup();

		for (ToggleButton tool : figureAndSelectionToolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(figureAndSelectionToolsGroup);
			tool.setCursor(Cursor.HAND);
		}
		for (ButtonBase tool : functionalitiesToolsArr) {
			tool.setMinWidth(90);
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
		buttonsBox.getChildren().addAll(figureAndSelectionToolsArr);
		buttonsBox.getChildren().addAll(functionalitiesToolsArr);
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

		//If any figure creation button is selected then the figure will be drawn after releasing the mouse
		canvas.setOnMouseReleased(event -> {
			Toggle selectedFigureButton = figureAndSelectionToolsGroup.getSelectedToggle();
			if(selectedFigureButton != null && !selectionButton.isSelected()) {
				((FigureTool) selectedFigureButton.getUserData()).createFigure(startPoint, new Point(event.getX(), event.getY()));
			}
			//startPoint = null;
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			Figure topFigure = canvasState.getFigureAt(eventPoint); //Only the information from the front figure is displayed
			if(topFigure != null) {
				statusPane.updateStatus(topFigure.toString());
			}
			else {
				statusPane.updateStatus(eventPoint.toString());
			}
		});

		canvas.setOnMouseClicked(event -> {
			if(selectionButton.isSelected()) {
				Point clickedPoint = new Point(event.getX(), event.getY());
				selectedFigures.clear();
				if(startPoint.distanceTo(clickedPoint) != 0) {
					try {
						int countSelected = 0;
						Rectangle container = Rectangle.from(startPoint, clickedPoint);
						for (Figure figure : canvasState) {
							if (figure.isContainedIn(container)) {
								selectedFigures.add(figure);
								countSelected++;
							}
						}
						statusPane.updateStatus(countSelected>0 ? String.format("Se seleccionaron %d figuras", countSelected) : "No se encontraron figuras");
					}
					catch(Exception e) {
						statusPane.updateStatus(e.getMessage());
					}
				}
				else {
					Figure topFigure = canvasState.getFigureAt(clickedPoint);
					if(topFigure != null) {
						selectedFigures.add(topFigure);
						statusPane.updateStatus(String.format("Se seleccionó %s", topFigure));
					}
				}
				redrawCanvas();
			}
			startPoint = null;
		});

		canvas.setOnMouseDragged(event -> {
			if(selectionButton.isSelected() && !selectedFigures.isEmpty()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
				double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
				for(Figure figure : selectedFigures) {
					figure.move(diffX,diffY);
				}
				redrawCanvas();
			}
			else{
				selectedFigures.clear();
			}
		});

		//FunctionalityButtons

		deleteButton.setOnAction(event -> {
			canvasState.removeFigures(selectedFigures);
			selectedFigures.clear();
			redrawCanvas();
		});

		toTopButton.setOnAction(event -> {
			canvasState.sendToTop(selectedFigures);
			redrawCanvas();
		});

		toBottomButton.setOnAction(event -> {
			canvasState.sendToBottom(selectedFigures);
			redrawCanvas();
		});

		borderSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number number, Number newValue) {
				for(Figure figure : selectedFigures) {
					figure.setBorderSize(newValue.doubleValue());
				}
				redrawCanvas();
			}
		});

		borderColorPicker.valueProperty().addListener(new ChangeListener<Color>() {
			@Override
			public void changed(ObservableValue<? extends Color> observableValue, Color color, Color newValue) {
				for(Figure figure : selectedFigures) {
					figure.setBorderColor(newValue);
				}
				redrawCanvas();
			}
		});

		fillColorPicker.valueProperty().addListener(new ChangeListener<Color>() {
			@Override
			public void changed(ObservableValue<? extends Color> observableValue, Color color, Color newValue) {
				for(Figure figure : selectedFigures) {
					figure.setFillColor(newValue);
				}
				redrawCanvas();
			}
		});
	}

	private void setButtonsData() {
		rectangleButton.setUserData(new RectangleTool(canvasState, this));
		circleButton.setUserData(new CircleTool(canvasState, this));
		ellipseButton.setUserData(new EllipseTool(canvasState, this));
		squareButton.setUserData(new SquareTool(canvasState, this));
		lineButton.setUserData(new LineTool(canvasState, this));
	}

	public void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for (Figure figure : canvasState) {
			gc.setStroke(selectedFigures.contains(figure) ? SELECTED_FIGURE_BORDER_COLOR : figure.getBorderColor());
			gc.setFill(figure.getFillColor());
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