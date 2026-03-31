package puzzlesolver.commands;

import lombok.Getter;
import lombok.ToString;
import puzzlesolver.generics.puzzle.Cell;
import puzzlesolver.generics.puzzle.PuzzleValue;

/**
 * Simple command that sets the value of a given cell.
 * @param <T>
 */
@ToString
public class ValueCommand<T extends PuzzleValue> implements Command {
    private final Cell<T> cell;
    private final T value;
    private final T prevValue;
    @Getter
    private boolean applied;

    public ValueCommand(Cell<T> cell, T value) {
        this.cell = cell;
        this.value = value;
        this.prevValue = cell.getValue();
    }

    @Override
    public void apply() {
        applied = true;
        cell.setValue(value);
    }

    @Override
    public void undo() {
        applied = false;
        cell.setValue(prevValue);
    }
}
