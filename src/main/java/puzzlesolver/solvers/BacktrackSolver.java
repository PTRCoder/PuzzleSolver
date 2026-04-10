package puzzlesolver.solvers;

import lombok.extern.slf4j.Slf4j;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.commands.ValueCommand;
import puzzlesolver.generics.puzzle.Cell;
import puzzlesolver.generics.puzzle.Grid;
import puzzlesolver.generics.puzzle.Puzzle;
import puzzlesolver.generics.puzzle.PuzzleValue;
import puzzlesolver.loc.LogStrings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public record BacktrackSolver<T extends PuzzleValue>(Puzzle<T> puzzle) implements Solver {

    @Override
    public boolean solve(CompoundCommand comms) {
        log.info(LogStrings.SOLVER_START.get(), this.getClass());
        Grid<T> grid = puzzle.getGrid();
        List<Cell<T>> cells = new ArrayList<>();
        grid.iterator().forEachRemaining(cells::add);
        boolean result = solve(comms, cells, 0);
        log.info(result ? LogStrings.SOLVER_SUCCESS.get() : LogStrings.SOLVER_FAIL.get(), this.getClass());
        return result;
    }

    private boolean solve(CompoundCommand comms, List<? extends Cell<T>> cells, int index) {
        if (index == cells.size())
            return puzzle.getGrid().validate();
        Cell<T> c = cells.get(index);
        if (c.isLocked())
            return solve(comms, cells, index + 1);
        Collection<T> vals = c.getAllowedValues();
        for (T x : vals) {
            comms.add(new ValueCommand<>(c, x));
            if (solve(comms, cells, index + 1))
                return true;
            comms.undo();
        }
        return false;
    }

}
