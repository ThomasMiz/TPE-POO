package backend;

import javafx.scene.paint.Color;

public interface Colorable {

    void setBorderSize(double size);
    double getBorderSize();

    void setBorderColor(Color color);
    Color getBorderColor();

    void setFillColor(Color color);
    Color getFillColor();
}
