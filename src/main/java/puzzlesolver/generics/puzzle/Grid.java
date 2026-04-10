package puzzlesolver.generics.puzzle;

import javafx.scene.control.Label;
import org.jetbrains.annotations.NonNls;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public interface Grid<T extends PuzzleValue> extends Iterable<Cell<T>> {
    List<? extends List<? extends Cell<T>>> getCells();

    int getWidth();

    int getHeight();

    List<? extends Iterable<? extends Cell<T>>> getRows();

    List<? extends Iterable<? extends Cell<T>>> getCols();

    default boolean validate() {
        for (Group<T> g : getGroups()) {
            if (!g.validate())
                return false;
        }
        return true;
    }

    default @Nullable Cell<T> getCell(Position pos) {
        List<? extends List<? extends Cell<T>>> cells = this.getCells();
        int y = pos.y();
        if (cells.size() <= y || y < 0)
            return null;
        List<? extends Cell<T>> row = cells.get(y);
        int x = pos.x();
        if (row.size() <= x || x < 0)
            return null;
        return row.get(x);
    }

    default boolean isFinished() {
        for (Cell<T> c : this) {
            if (c.isEmpty())
                return false;
        }
        return true;
    }

    default void lock() {
        for (Cell<T> c : this) {
            if (!c.isEmpty())
                c.lock();
        }
    }

    default void unlock() {
        for (Cell<T> c : this) {
            c.unlock();
        }
    }

    @Override
    default Iterator<Cell<T>> iterator() {
        return new GridIterator<>(getCells());
    }

    void print(Label parent);

    List<? extends Group<T>> getGroups();


    class GridIterator<T extends PuzzleValue> implements Iterator<Cell<T>> {
        private final Iterator<? extends Collection<? extends Cell<T>>> cells;
        private Iterator<? extends Cell<T>> current;

        public GridIterator(Collection<? extends Collection<? extends Cell<T>>> cells) {
            this.cells = cells.iterator();
            this.current = this.cells.next().iterator();
        }

        @Override
        public boolean hasNext() {
            if (current.hasNext())
                return true;
            if (cells.hasNext()) {
                current = cells.next().iterator();
                return hasNext();
            }
            return false;
        }

        @Override
        public Cell<T> next() {
            if (current.hasNext())
                return current.next();
            if (cells.hasNext()) {
                current = cells.next().iterator();
                return next();
            }
            return current.next();
        }
    }

    @NonNls
    String encode();
}
