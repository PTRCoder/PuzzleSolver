package puzzlesolver.solvers;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.commands.ValueCommand;
import puzzlesolver.generics.puzzle.Cell;
import puzzlesolver.generics.puzzle.Grid;
import puzzlesolver.generics.puzzle.Puzzle;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Value
public class BacktrackSolver<T> implements Solver {
    Puzzle<T> puzzle;
    private static int count = 0;
    private static boolean locked;

    @Override
    public boolean solve(CompoundCommand comms) {
        log.info("The BacktrackSolver has started");
        Grid<T> grid = puzzle.getGrid();
        List<Cell<T>> cells = new ArrayList<>();
        grid.iterator().forEachRemaining(cells::add);
        boolean result = solve(comms, cells, 0);
        log.info("The BacktrackSolver has finished {}successfully", result ? "" : "un");
        return result;
    }

    private boolean solve(CompoundCommand comms, List<? extends Cell<T>> cells, int index) {
        if (index == cells.size())
            return puzzle.getGrid().validate();
        Cell<T> c = cells.get(index);
        if (c.isLocked())
            return solve(comms, cells, index + 1);
        List<T> vals = c.getAllowedValues();
        for (T x : vals) {
            comms.add(new ValueCommand<>(c, x));
            if (solve(comms, cells, index + 1))
                return true;
            comms.undo();
        }
        return false;
    }

}
