package puzzlesolver.generics.puzzle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;

import java.util.List;

public interface Cell<T> {
    List<T> getAllowedValues();

    T getValue();

    void setValue(T value);

    ObjectProperty<T> valueProperty();

    Position getPos();

    boolean isEmpty();

    boolean isLocked();

    BooleanProperty lockedProperty();

    void lock();

    void unlock();

    default boolean isValid() {
        return this.isEmpty() || this.getAllowedValues().contains(this.getValue());
    }

    List<? extends Group<T>> getGroups();

    ListProperty<? extends Group<T>> groupsProperty();

    void addGroup(Group<T> group);

    Grid<T> getGrid();

    List<T> getCandidates();
}
