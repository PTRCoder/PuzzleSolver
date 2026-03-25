package puzzlesolver.puzzles.sudoku.reasoners;

import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.commands.ValueCommand;
import puzzlesolver.generics.puzzle.Cell;
import puzzlesolver.generics.reasoners.EmptyCellReasoner;

import java.util.List;

public class SingleCellCandidateReasoner extends EmptyCellReasoner<Integer> {
    @Override
    public boolean applyToCell(Cell<Integer> cell, CompoundCommand comms) {
        List<Integer> allowed = cell.getCandidates();
        if (allowed.size() == 1) {
            int value = allowed.getFirst();
            comms.add(new ValueCommand<>(cell, value));
            return true;
        }
        return false;
    }
}
