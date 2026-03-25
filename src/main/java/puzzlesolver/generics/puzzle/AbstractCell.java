package puzzlesolver.generics.puzzle;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public abstract class AbstractCell<T> implements Cell<T> {
    private final Grid<T> grid;
    private final Position pos;
    private boolean locked = false;
    @Getter(AccessLevel.NONE)
    private final ObjectProperty<T> value = new SimpleObjectProperty<>(getEmpty());
    @Getter(AccessLevel.NONE)
    protected final ListProperty<Group<T>> groups =
            new SimpleListProperty<>(FXCollections.observableList(new LinkedList<>()));

    @Override
    public void lock() {
        this.locked = true;
    }

    @Override
    public void unlock() {
        this.locked = false;
    }

    abstract protected T getEmpty();

    @Override
    public boolean isEmpty() {
        return Objects.equals(this.getEmpty(), value.get());
    }

    @Override
    public void setValue(T value) {
        if (!this.locked)
            this.valueProperty().set(value);
    }

    @Override
    public T getValue() {
        return valueProperty().get();
    }

    @Override
    public ObjectProperty<T> valueProperty() {
        return value;
    }

    @Override
    public void addGroup(Group<T> group) {
        this.groups.add(group);
    }

    @Override
    public ListProperty<? extends Group<T>> groupsProperty() {
        return groups;
    }

    @Override
    public List<? extends Group<T>> getGroups() {
        return groups.get();
    }

    @Override
    public List<T> getCandidates() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return getPos().toString();
    }
}
