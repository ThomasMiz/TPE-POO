package frontend;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import frontend.tools.*;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.*;

public class PaintPane extends HBox {

	private static final Color SELECTED_FIGURE_BORDER_COLOR = new Color(1, 0, 0, 1);

	private static final Color DEFAULT_FIGURE_FILL_COLOR = Color.YELLOW;
	private static final Color DEFAULT_FIGURE_BORDER_COLOR = Color.BLACK;
	private static final double DEFAULT_FIGURE_BORDER_SIZE = 1;

	private static final double MIN_FIGURE_BORDER_SIZE = 1;
	private static final double MAX_FIGURE_BORDER_sIZE = 50;

	// BackEnd
	private final CanvasState canvasState;

	// Canvas y relacionados
	private final Canvas canvas;
	private final GraphicsContext gc;

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
	private final Slider borderSizeSlider = new Slider(MIN_FIGURE_BORDER_SIZE, MAX_FIGURE_BORDER_sIZE, DEFAULT_FIGURE_BORDER_SIZE);
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
		CanvasPane canvasPane = new CanvasPane(600, 800);
		this.canvas = canvasPane.getCanvas();
		this.gc = canvas.getGraphicsContext2D();

		// Buttons
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

		// Interface
		borderSizeSlider.setShowTickMarks(true);
		borderSizeSlider.setShowTickLabels(true);
		Label borderLabel = new Label();
		Label fillLabel = new Label();
		borderLabel.textProperty().bind(Bindings.format("Borde:\t%.2f", borderSizeSlider.valueProperty()));
		fillLabel.textProperty().setValue("Relleno: ");
		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(figureAndSelectionToolsArr);
		buttonsBox.getChildren().addAll(functionalitiesToolsArr);
		buttonsBox.getChildren().addAll(borderLabel, borderSizeSlider, borderColorPicker, fillLabel, fillColorPicker);
		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);

		canvasPane.prefHeightProperty().bind(this.heightProperty());
		canvasPane.prefWidthProperty().bind(this.widthProperty());

		getChildren().addAll(buttonsBox, canvasPane);

		figureAndSelectionToolsGroup.selectedToggleProperty().addListener(this::onSelectedButtonChanged);

		canvasPane.widthProperty().addListener(this::onWindowSizeChanged);
		canvasPane.heightProperty().addListener(this::onWindowSizeChanged);

		// Mouse events
		canvas.setOnMousePressed(this::onMousePressed);
		canvas.setOnMouseReleased(this::onMouseRelease);
		canvas.setOnMouseMoved(this::onMouseMoved);
		canvas.setOnMouseDragged(this::onMouseDragged);

		// Functionality Buttons
		deleteButton.setOnAction(event -> {
			canvasState.removeFigures(selectedFigures);
			selectedFigures.clear();
			onSelectionChanged();
		});

		toTopButton.setOnAction(event -> {
			canvasState.sendToTop(selectedFigures);
			redrawCanvas();
		});

		toBottomButton.setOnAction(event -> {
			canvasState.sendToBottom(selectedFigures);
			redrawCanvas();
		});

		borderSizeSlider.valueProperty().addListener(this::onBorderSliderValueChanged);
		borderColorPicker.valueProperty().addListener(this::onBorderColorChanged);
		fillColorPicker.valueProperty().addListener(this::onFillColorChanged);
	}

	private void onWindowSizeChanged(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
		redrawCanvas();
	}

	private void onSelectedButtonChanged(ObservableValue<? extends Toggle> observableValue, Toggle value, Toggle newValue) {
		if (value == selectionButton && newValue != selectionButton && !selectedFigures.isEmpty()) {
			selectedFigures.clear();
			onSelectionChanged();
		}
	}

	private void onSelectionChanged() {
		if (selectedFigures.isEmpty()) {
			fillColorPicker.setValue(fillColor);
			borderColorPicker.setValue(borderColor);
			borderSizeSlider.setValue(borderSize);
		} else {
			Iterator<Figure> iter = selectedFigures.iterator();
			Figure f = iter.next();
			Color fc = f.getFillColor();
			Color bc = f.getBorderColor();
			double bs = f.getBorderSize();

			// We go through all the figures and check if they all share the same value in any property.
			while ((fc != null || bc != null || bs >= 0) && iter.hasNext()) {
				f = iter.next();
				if (fc != null && !fc.equals(f.getFillColor())) fc = null;
				if (bc != null && !bc.equals(f.getBorderColor())) bc = null;
				if (bs >= 0 && bs != f.getBorderSize()) bs = -1;
			}

			if (bs >= 0) borderSizeSlider.setValue(bs);
			fillColorPicker.setValue(fc);
			borderColorPicker.setValue(bc);
		}

		redrawCanvas();
	}

	private void onMousePressed(MouseEvent event) {
		startPoint = new Point(event.getX(), event.getY());
	}

	private void onMouseRelease(MouseEvent event) {
		//If any figure creation button is selected then the figure will be drawn after releasing the mouse
		Toggle selectedButton = figureAndSelectionToolsGroup.getSelectedToggle();

		if (selectedButton == null)
			return;

		Point releasePoint = new Point(event.getX(), event.getY());
		if (selectedButton == selectionButton) {
			selectedFigures.clear();
			if (startPoint.distanceSquaredTo(releasePoint) > 1) {
				try {
					Rectangle container = Rectangle.from(startPoint, releasePoint);
					canvasState.getFiguresOnRectangle(container, selectedFigures);

					String status;
					if (selectedFigures.isEmpty())
						status = "No se encontraron figuras en el area";
					else if (selectedFigures.size() == 1)
						status = String.format("Se seleccionó %s", selectedFigures.iterator().next());
					else
						status = String.format("Se seleccionaron %d figuras", selectedFigures.size());
					statusPane.updateStatus(status);

				} catch (Exception e) {
					statusPane.updateStatus(e.getMessage());
				}
			} else {
				Figure topFigure = canvasState.getFigureAt(releasePoint);
				if (topFigure != null) {
					selectedFigures.add(topFigure);
					statusPane.updateStatus(String.format("Se seleccionó %s", topFigure));
				}
			}

			onSelectionChanged();
		} else
			((FigureTool) selectedButton.getUserData()).createFigure(startPoint, releasePoint);
	}

	private void onMouseMoved(MouseEvent event) {
		if (selectedFigures.isEmpty()) {
			Point eventPoint = new Point(event.getX(), event.getY());
			Figure topFigure = canvasState.getFigureAt(eventPoint); // Only the information from the front figure is displayed
			statusPane.updateStatus(topFigure == null ? eventPoint.toString() : topFigure.toString());
		}
	}

	private void onMouseDragged(MouseEvent event) {
		if (!selectedFigures.isEmpty()) {
			double diffX = event.getX() - startPoint.getX();
			double diffY = event.getY() - startPoint.getY();
			for (Figure figure : selectedFigures)
				figure.move(diffX, diffY);
			redrawCanvas();
			startPoint.move(diffX, diffY);
		}
	}

	private void onBorderSliderValueChanged(ObservableValue<? extends Number> observableValue, Number number, Number newValue) {
		if (selectedFigures.isEmpty())
			borderSize = newValue.doubleValue();
		else {
			double val = newValue.doubleValue();
			for (Figure figure : selectedFigures)
				figure.setBorderSize(val);
			redrawCanvas();
		}
	}

	private void onBorderColorChanged(ObservableValue<? extends Color> observableValue, Color color, Color newValue) {
		if (newValue == null)
			return;

		if (selectedFigures.isEmpty()) {
			borderColor = newValue;
		} else {
			for (Figure figure : selectedFigures)
				figure.setBorderColor(newValue);
			redrawCanvas();
		}
	}

	private void onFillColorChanged(ObservableValue<? extends Color> observableValue, Color color, Color newValue) {
		if (newValue == null)
			return;

		if (selectedFigures.isEmpty()) {
			fillColor = newValue;
		} else {
			for (Figure figure : selectedFigures)
				figure.setFillColor(newValue);
			redrawCanvas();
		}
	}

	public void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for (Figure figure : canvasState) {
			gc.setStroke(selectedFigures.contains(figure) ? SELECTED_FIGURE_BORDER_COLOR : figure.getBorderColor());
			gc.setFill(figure.getFillColor());
			figure.draw(gc);
		}
	}

	public void reset() {
		// We clear the figures
		selectedFigures.clear();
		canvasState.clear();

		// We reset the UI. Since selectedFigures is empty, setting the pickers (and slider) will
		// automatically set the borderColor, borderSize and fillColor variables (in the callbacks)
		figureAndSelectionToolsGroup.selectToggle(null);
		borderColorPicker.setValue(DEFAULT_FIGURE_BORDER_COLOR);
		fillColorPicker.setValue(DEFAULT_FIGURE_FILL_COLOR);
		borderSizeSlider.setValue(DEFAULT_FIGURE_BORDER_SIZE);

		redrawCanvas();
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
