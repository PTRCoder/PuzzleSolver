package puzzlesolver.generics.puzzle;

import org.jetbrains.annotations.NonNls;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface Grid<T extends PuzzleValue> extends Iterable<Cell<T>> {
    Collection<? extends Cell<T>> getCells();

    Map<Position, ? extends Cell<T>> getPositionMap();

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
        int y = pos.y();
        int x = pos.x();
        if (this.getHeight() <= y || y < 0 || this.getWidth() <= x || x < 0)
            return null;
        return getPositionMap().get(pos);
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

    @SuppressWarnings("unchecked")
    @Override
    default Iterator<Cell<T>> iterator() {
        return (Iterator<Cell<T>>) getCells().iterator();
    }

    List<? extends Group<T>> getGroups();

    @NonNls
    String encode();
}
