package backend.model;

public class Square extends Rectangle {

    /**
     * Creates a Square from a topLeft point and it's desired size.
     */
    public Square(Point topLeft, double size) {
        super(topLeft, new Point(topLeft.getX() + validateSquareSize(size), topLeft.getY() + size));
    }

    private static double validateSquareSize(double size){
        if (size <= 0) throw new IllegalArgumentException("El tamaño de un cuadrado debe ser mayor a 0");
        return size;
    }

    @Override
    public String toString() {
        return String.format("Cuadrado [ %s, %s ]", getTopLeft(), getBottomRight());
    }
}
