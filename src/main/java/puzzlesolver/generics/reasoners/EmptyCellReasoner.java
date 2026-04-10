package puzzlesolver.generics.reasoners;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.generics.puzzle.Cell;
import puzzlesolver.generics.puzzle.Grid;
import puzzlesolver.generics.puzzle.Puzzle;
import puzzlesolver.generics.puzzle.PuzzleValue;

@Slf4j
public abstract class EmptyCellReasoner<T extends PuzzleValue> extends AbstractReasoner<T> {
    private final String name = this.getClass().getSimpleName();

    public abstract boolean applyToCell(Cell<T> cell, CompoundCommand comms);

    @Override
    public boolean apply(Puzzle<T> puzzle, CompoundCommand comms) {
        LOG_START();
        long t0 = System.nanoTime();
        Grid<T> grid = puzzle.getGrid();
        for (Cell<T> cell : grid) {
            if (cell.isEmpty() && applyToCell(cell, comms)) {
                log.info(Long.toString(System.nanoTime() - t0));
                LOG_SUCCESS();
                return true;
            }
        }
        log.info(Long.toString(System.nanoTime() - t0));
        LOG_FAIL();
        return false;
    }

    @Override
    protected Logger getLog() {
        return log;
    }
}
