package puzzlesolver.generics.puzzle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;

import java.util.List;

public interface Cell<T extends PuzzleValue> {
    List<T> getAllowedValues();

    default T getValue() {
        return valueProperty().get();
    }

    default void setValue(T value) {
        valueProperty().setValue(value);
    }

    ObjectProperty<T> valueProperty();

    Position getPosition();

    default boolean isEmpty() {
        return valueProperty().get().isEmpty();
    }

    default boolean isLocked() {
        return lockedProperty().get();
    }

    BooleanProperty lockedProperty();

    default void lock() {
        lockedProperty().set(true);
    }

    default void unlock() {
        lockedProperty().set(false);
    }

    default boolean isValid() {
        return this.isEmpty() || this.getAllowedValues().contains(this.getValue());
    }

    default List<? extends Group<T>> getGroups() {
        return groupsProperty().get();
    }

    ListProperty<Group<T>> groupsProperty();

    default void addGroup(Group<T> group) {
        groupsProperty().add(group);
    }

    Grid<T> getGrid();

    default List<T> getCandidates() {
        throw new UnsupportedOperationException();
    }
}
