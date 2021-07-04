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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.*;

public class PaintPane extends BorderPane {

	private static final Color SELECTED_FIGURE_BORDER_COLOR = new Color(1, 0, 0, 1);

	private static final Color DEFAULT_FIGURE_FILL_COLOR = Color.YELLOW;
	private static final Color DEFAULT_FIGURE_BORDER_COLOR = Color.BLACK;
	private static final double DEFAULT_FIGURE_BORDER_SIZE = 1;

	private static final double MIN_FIGURE_BORDER_SIZE = 1;
	private static final double MAX_FIGURE_BORDER_sIZE = 50;

	// BackEnd
	private final CanvasState canvasState;

	// Canvas y relacionados
	private final Canvas canvas = new Canvas(800, 600);
	private final GraphicsContext gc = canvas.getGraphicsContext2D();

	// Propiedades de figuras seleccionadas (aplicadas a figuras nuevas)
	private Color borderColor = DEFAULT_FIGURE_BORDER_COLOR;
	private Color fillColor = DEFAULT_FIGURE_FILL_COLOR;
	private double borderSize = DEFAULT_FIGURE_BORDER_SIZE;

	// Botones Barra Izquierda
	private final ToggleButton selectionButton = new ToggleButton("Seleccionar");
	private final ToggleButton rectangleButton = new ToggleButton("Rectángulo");
	private final ToggleButton circleButton = new ToggleButton("Círculo");
	private final ToggleButton squareButton = new ToggleButton("Cuadrado");
	private final ToggleButton ellipseButton = new ToggleButton("Elipse");
	private final ToggleButton lineButton = new ToggleButton("Línea");
	private final ToggleGroup figureAndSelectionToolsGroup = new ToggleGroup();
	private final Button deleteButton = new Button("Borrar");
	private final Button toBottomButton = new Button("Al Fondo");
	private final Button toTopButton = new Button("Al Frente");
	private final Slider borderSlider = new Slider(MIN_FIGURE_BORDER_SIZE, MAX_FIGURE_BORDER_sIZE, DEFAULT_FIGURE_BORDER_SIZE);
	private final ColorPicker borderColorPicker = new ColorPicker(DEFAULT_FIGURE_BORDER_COLOR);
	private final ColorPicker fillColorPicker = new ColorPicker(DEFAULT_FIGURE_FILL_COLOR);

	// Last startPoint after mouse pressed
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

		for (ToggleButton tool : figureAndSelectionToolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(figureAndSelectionToolsGroup);
			tool.setCursor(Cursor.HAND);
		}
		for (ButtonBase tool : functionalitiesToolsArr) {
			tool.setMinWidth(90);
			tool.setCursor(Cursor.HAND);
		}

		rectangleButton.setUserData(new RectangleTool(canvasState, this));
		circleButton.setUserData(new CircleTool(canvasState, this));
		ellipseButton.setUserData(new EllipseTool(canvasState, this));
		squareButton.setUserData(new SquareTool(canvasState, this));
		lineButton.setUserData(new LineTool(canvasState, this));

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
		canvas.setOnMousePressed(this::onMousePressed);
		canvas.setOnMouseReleased(this::onMouseRelease);
		canvas.setOnMouseMoved(this::onMouseMoved);
		canvas.setOnMouseClicked(this::onMouseClicked);
		canvas.setOnMouseDragged(this::onMouseDragged);

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

		borderSlider.valueProperty().addListener(this::onBorderSliderValueChanged);
		borderColorPicker.valueProperty().addListener(this::onBorderColorChanged);
		fillColorPicker.valueProperty().addListener(this::onFillColorChanged);
	}

	private void onMousePressed(MouseEvent event){
		startPoint = new Point(event.getX(), event.getY());
	}

	private void onMouseRelease(MouseEvent event) {
		//If any figure creation button is selected then the figure will be drawn after releasing the mouse
		Toggle selectedFigureButton = figureAndSelectionToolsGroup.getSelectedToggle();
		if(selectedFigureButton != null && !selectionButton.isSelected()) {
			((FigureTool) selectedFigureButton.getUserData()).createFigure(startPoint, new Point(event.getX(), event.getY()));
		}
		//startPoint = null;
	}

	private void onMouseMoved(MouseEvent event) {
		Point eventPoint = new Point(event.getX(), event.getY());
		Figure topFigure = canvasState.getFigureAt(eventPoint); // Only the information from the front figure is displayed
		statusPane.updateStatus(topFigure == null ? eventPoint.toString() : topFigure.toString());
	}

	private void onMouseClicked(MouseEvent event) {
		if (selectionButton.isSelected()) {
			Point clickedPoint = new Point(event.getX(), event.getY());
			selectedFigures.clear();
			if (startPoint.distanceTo(clickedPoint) != 0) {
				try {
					int countSelected = 0;
					Rectangle container = Rectangle.from(startPoint, clickedPoint);
					for (Figure figure : canvasState) {
						if (figure.isContainedIn(container)) {
							selectedFigures.add(figure);
							countSelected++;
						}
					}
					statusPane.updateStatus(countSelected > 0 ? String.format("Se seleccionaron %d figuras", countSelected) : "No se encontraron figuras");
				} catch (Exception e) {
					statusPane.updateStatus(e.getMessage());
				}
			} else {
				Figure topFigure = canvasState.getFigureAt(clickedPoint);
				if (topFigure != null) {
					selectedFigures.add(topFigure);
					statusPane.updateStatus(String.format("Se seleccionó %s", topFigure));
				}
			}
			redrawCanvas();
		}
		startPoint = null;
	}

	private void onMouseDragged(MouseEvent event) {
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
	}

	private void onBorderSliderValueChanged(ObservableValue<? extends Number> observableValue, Number number, Number newValue) {
		for(Figure figure : selectedFigures) {
			figure.setBorderSize(newValue.doubleValue());
		}
		redrawCanvas();
	}

	private void onBorderColorChanged(ObservableValue<? extends Color> observableValue, Color color, Color newValue) {
		for(Figure figure : selectedFigures) {
			figure.setBorderColor(newValue);
		}
		redrawCanvas();
	}

	private void onFillColorChanged(ObservableValue<? extends Color> observableValue, Color color, Color newValue) {
		for(Figure figure : selectedFigures) {
			figure.setFillColor(newValue);
		}
		redrawCanvas();
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