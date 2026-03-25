package puzzlesolver.generics.puzzle;

import java.util.List;
import java.util.Iterator;

public interface Group<T> extends Iterable<Cell<T>> {
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

    List<T> getAllowedValues();

    class GroupIterator<T> implements Iterator<Cell<T>> {
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
