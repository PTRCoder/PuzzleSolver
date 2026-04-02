package puzzlesolver.puzzles.starbattle.reasoners;

import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.commands.ValueCommand;
import puzzlesolver.generics.puzzle.Cell;
import puzzlesolver.generics.puzzle.FillValue;
import puzzlesolver.generics.reasoners.EmptyCellReasoner;

public class SingularReasoner extends EmptyCellReasoner<FillValue> {
    @Override
    public boolean applyToCell(Cell<FillValue> cell, CompoundCommand comms) {
        if (cell.getAllowedValues().size() == 1) {
            FillValue x = cell.getAllowedValues().iterator().next();
            comms.add(new ValueCommand<>(cell, x));
            return true;
        }
        return false;
    }
}
