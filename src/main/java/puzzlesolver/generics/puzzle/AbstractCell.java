package puzzlesolver.generics.puzzle;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class AbstractCell<T> implements Cell<T> {
    private final Grid<T> grid;
    private final Position pos;
    @Accessors(fluent = true)
    private final BooleanProperty lockedProperty;
    @Accessors(fluent = true)
    private final ObjectProperty<T> valueProperty;
    @Accessors(fluent = true)
    private final ListProperty<Group<T>> groupsProperty =
            new SimpleListProperty<>(FXCollections.observableList(new LinkedList<>()));

    protected AbstractCell(Grid<T> grid, Position pos) {
        this.grid = grid;
        this.pos = pos;
        this.lockedProperty = new SimpleBooleanProperty(false);
        this.valueProperty = new LockableObjectProperty(getEmpty());
    }

    @Override
    public void lock() {
        lockedProperty().set(true);
    }

    @Override
    public void unlock() {
        lockedProperty().set(false);
    }

    @Override
    public boolean isLocked() {
        return lockedProperty().get();
    }

    abstract protected T getEmpty();

    @Override
    public boolean isEmpty() {
        return Objects.equals(this.getEmpty(), valueProperty().get());
    }

    @Override
    public void setValue(T value) {
        this.valueProperty().set(value);
    }

    @Override
    public T getValue() {
        return valueProperty().get();
    }

    @Override
    public void addGroup(Group<T> group) {
        this.groupsProperty().add(group);
    }

    @Override
    public List<? extends Group<T>> getGroups() {
        return groupsProperty().get();
    }

    @Override
    public List<T> getCandidates() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return getPos().toString();
    }

    private class LockableObjectProperty extends SimpleObjectProperty<T> {
        public LockableObjectProperty(T value) {super(value);}

        @Override
        public void set(T val) {
            if (!lockedProperty().get())
                super.set(val);
        }

        @Override
        public void setValue(T val) {
            if (!lockedProperty().get())
                super.setValue(val);
        }
    }
}
