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
    private final Position position;
    @Accessors(fluent = true)
    private final BooleanProperty lockedProperty;
    @Accessors(fluent = true)
    private final ObjectProperty<T> valueProperty;
    @Accessors(fluent = true)
    private final ListProperty<Group<T>> groupsProperty =
            new SimpleListProperty<>(FXCollections.observableList(new LinkedList<>()));

    protected AbstractCell(Grid<T> grid, Position position) {
        this.grid = grid;
        this.position = position;
        this.lockedProperty = new SimpleBooleanProperty(false);
        this.valueProperty = new LockableProperty<>(this.lockedProperty);
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
        return getPosition().toString();
    }
}
