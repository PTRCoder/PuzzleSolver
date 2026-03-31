package puzzlesolver.puzzles.sudoku.reasoners;

import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.commands.ValueCommand;
import puzzlesolver.generics.puzzle.Cell;
import puzzlesolver.generics.puzzle.HexValue;
import puzzlesolver.generics.reasoners.EmptyCellReasoner;

import java.util.List;

public class SingleCellCandidateReasoner extends EmptyCellReasoner<HexValue> {
    @Override
    public boolean applyToCell(Cell<HexValue> cell, CompoundCommand comms) {
        List<HexValue> allowed = cell.getCandidates();
        if (allowed.size() == 1) {
            HexValue value = allowed.getFirst();
            comms.add(new ValueCommand<>(cell, value));
            return true;
        }
        return false;
    }
}
