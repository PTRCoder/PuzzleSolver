package puzzlesolver.commands;

import lombok.AccessLevel;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Deque;
import java.util.LinkedList;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@ToString
public class CompoundCommand {
    Deque<Command> todo = new LinkedList<>();
    Deque<Command> done = new LinkedList<>();

    public void add(Command comm) {
        if (!todo.isEmpty()) {
            todo.clear();
        }
        done.add(comm);
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
}
