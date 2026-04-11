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
        log.info(LogStrings.REASONER_SUCCESS.get(), getClass());
        long t0 = System.nanoTime();
        Grid<T> grid = puzzle.getGrid();
        for (Cell<T> cell : grid) {
            if (cell.isEmpty() && applyToCell(cell, comms)) {
                log.info(Long.toString(System.nanoTime() - t0));
                log.info(LogStrings.REASONER_SUCCESS.get(), getClass());
                return true;
            }
        }
        log.info(Long.toString(System.nanoTime() - t0));
        log.info(LogStrings.REASONER_FAIL.get(), getClass());
        return false;
    }


}
