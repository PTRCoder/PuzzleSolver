package puzzlesolver.commands;

import lombok.ToString;
import puzzlesolver.generics.puzzle.Cell;

/**
 * Simple command that sets the value of a given cell.
 * @param <T>
 */
@ToString
public class ValueCommand<T> implements Command {
    private final Cell<T> cell;
    private final T value;
    private final T prevValue;

    public ValueCommand(Cell<T> cell, T value) {
        this.cell = cell;
        this.value = value;
        this.prevValue = cell.getValue();
    }

    @Override
    public void apply() {
        cell.setValue(value);
    }

    @Override
    public void undo() {
        cell.setValue(prevValue);
    }
}
