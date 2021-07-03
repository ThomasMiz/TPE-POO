package backend;

import javafx.scene.paint.Color;

public interface Colorable {

    void setBorderSize(double size);
    double getBorderSize();

    void setBorderColor(Color color);
    Color getBorderColor();

    void setFillColor(Color color);
    Color getFillColor();

    default void setColorProperties(double borderSize, Color borderColor, Color fillColor) {
        setBorderSize(borderSize);
        setBorderColor(borderColor);
        setFillColor(fillColor);
    }

}
