package backend;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;

import java.util.*;

public class CanvasState implements Iterable<Figure> {

    /**
     * The list of Figures in this canvas, ordered by descending depth.
     * <p>
     * A figure's depth is defined by it's position on the list, which is ordered in descending depth.
     * This is so when adding a figure, it can simply be added to the end of the list, which means it will
     * have the lowest depth, and therefore is at the top.
     */
    private final List<Figure> list = new ArrayList<>();

    public void addFigure(Figure figure) {
        list.add(figure);
    }

    public boolean removeFigures(Collection<Figure> figures) {
        return list.removeAll(figures);
    }

    public void sendToTop(Collection<Figure> figures) {
        // TODO
    }

    public void sendToBottom(Collection<Figure> figures) {
        // TODO
    }

    /**
     * Gets the Figure with the lowest depth that contains the given point, or null if none was found.
     */
    public Figure getFigureAt(Point point) {
        for (int i = list.size() - 1; i >= 0; i--) {
            Figure f = list.get(i);
            if (f.contains(point))
                return f;
        }

        return null;
    }

    /**
     * Adds all the figures that are fully contained within a given rectangle to the specified collection.
     * Returns the amount of figures that were added to the collection.
     */
    public int getFiguresOnRectangle(Rectangle rectangle, Collection<Figure> result) {
        int count = 0;
        for (Figure f : list) {
            if (f.isContainedIn(rectangle))
            {
                result.add(f);
                count++;
            }
        }
        return count;
    }

    /**
     * Returns an iterator through the Figures in this CanvasState in descending depth order (from bottom to top).
     */
    public Iterator<Figure> iterator() {
        return list.iterator();
    }

    /**
     * Returns an iterator through the Figures in this CanvasState in ascending depth order (from top to bottom).
     */
    public Iterator<Figure> ascendingDepthIterator() {
        return new ListInvertedIterator<>(list);
    }

    private static class ListInvertedIterator<T> implements Iterator<T> {
        private final List<T> list;
        private int index;

        public ListInvertedIterator(List<T> list) {
            this.list = list;
            index = list.size() - 1;
        }

        @Override
        public boolean hasNext() {
            return index >= 0 && index < list.size();
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            return list.get(index--);
        }
    }
}
