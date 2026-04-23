package puzzlesolver.generics.reasoners;

import lombok.extern.slf4j.Slf4j;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.generics.puzzle.Cell;
import puzzlesolver.generics.puzzle.Grid;
import puzzlesolver.generics.puzzle.Puzzle;
import puzzlesolver.generics.puzzle.PuzzleValue;
import puzzlesolver.loc.LogStrings;

@Slf4j
public abstract class EmptyCellReasoner<T extends PuzzleValue> implements Reasoner<T> {
    public abstract boolean applyToCell(Cell<T> cell, CompoundCommand comms);

    @Override
    public boolean apply(Puzzle<T> puzzle, CompoundCommand comms) {
        log.trace(LogStrings.REASONER_START.get(), this.getClass().getSimpleName());
        long t0 = System.nanoTime();
        Grid<T> grid = puzzle.getGrid();
        for (Cell<T> cell : grid) {
            if (cell.isEmpty() && applyToCell(cell, comms)) {
                log.debug("{} took {} ns to complete", this.getClass().getSimpleName(), System.nanoTime() - t0);
                log.trace(LogStrings.REASONER_SUCCESS.get(), this.getClass().getSimpleName());
                return true;
            }
        }
        log.debug("{} took {} ns to complete", this.getClass().getSimpleName(), System.nanoTime() - t0);
        log.trace(LogStrings.REASONER_FAIL.get(), this.getClass().getSimpleName());
        return false;
    }


}
