package puzzlesolver.commands;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import lombok.AccessLevel;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.LinkedList;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@ToString
public class CompoundCommand {
    ListProperty<Command> todo = new SimpleListProperty<>(FXCollections.observableList(new LinkedList<>()));
    ListProperty<Command> done = new SimpleListProperty<>(FXCollections.observableList(new LinkedList<>()));
    ObservableBooleanValue allDone = todo.emptyProperty();
    ObservableBooleanValue allNotDone = done.emptyProperty();

    public void add(Command comm) {
        if (!todo.isEmpty()) {
            todo.clear();
        }
        done.add(comm);
        if (!comm.isApplied())
            comm.apply();
    }

    public void undo() {
        if (!done.isEmpty()) {
            Command comm = done.removeLast();
            comm.undo();
            todo.addFirst(comm);
        }
    }

    public void apply() {
        if (!todo.isEmpty()) {
            Command comm = todo.removeFirst();
            comm.apply();
            done.addLast(comm);
        }
    }

    public void applyAll() {
        while (!todo.isEmpty())
            apply();
    }

    public void undoAll() {
        while (!done.isEmpty())
            undo();
    }

    public void clear() {
        todo.clear();
        done.clear();
    }

    public ObservableBooleanValue allDoneProperty() {
        return allDone;
    }

    public ObservableBooleanValue allNotDoneProperty() {
        return allNotDone;
    }
}
