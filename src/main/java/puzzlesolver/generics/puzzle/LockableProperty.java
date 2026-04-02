package puzzlesolver.generics.puzzle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableBooleanValue;

public class LockableProperty<T extends PuzzleValue> extends SimpleObjectProperty<T> {
    private final ObservableBooleanValue lock;

    public LockableProperty(T value, ObservableBooleanValue lock) {
        super(value);
        this.lock = lock;
    }

    public LockableProperty(ObservableBooleanValue lock) {
        this(null, lock);
    }

    @Override
    public void setValue(T t) {
        if (!lock.get())
            super.setValue(t);
    }

    @Override
    public void set(T t) {
        if (!lock.get())
            super.set(t);
    }
}
