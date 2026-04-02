package puzzlesolver.generics.puzzle;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.LinkedList;
import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class AbstractCell<T extends PuzzleValue> implements Cell<T> {
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
    public void addGroup(Group<T> group) {
        this.groupsProperty().add(group);
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
