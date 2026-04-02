package puzzlesolver.generics.puzzle;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public interface Group<T extends PuzzleValue> extends Iterable<Cell<T>> {
    List<? extends Cell<T>> getCells();

    default int getSize() {
        return getCells().size();
    }

    boolean validate();

    default boolean isFinished() {
        for (Cell<T> c : this) {
            if (c.isEmpty())
                return false;
        }
        return true;
    }

    @Override
    default Iterator<Cell<T>> iterator() {
        return new GroupIterator<>(getCells());
    }

    Collection<T> getAllowedValues();

    class GroupIterator<T extends PuzzleValue> implements Iterator<Cell<T>> {
        private final Iterator<? extends Cell<T>> cells;

        public GroupIterator(List<? extends Cell<T>> cells) {
            this.cells = cells.iterator();
        }

        @Override
        public Cell<T> next() {
            return cells.next();
        }

        @Override
        public boolean hasNext() {
            return cells.hasNext();
        }
    }

}
