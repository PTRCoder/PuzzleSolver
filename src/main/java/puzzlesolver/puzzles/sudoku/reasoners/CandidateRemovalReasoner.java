package puzzlesolver.puzzles.sudoku.reasoners;

import puzzlesolver.commands.CandidateCommand;
import puzzlesolver.commands.Command;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.generics.puzzle.Cell;
import puzzlesolver.generics.reasoners.EmptyCellReasoner;

import java.util.ArrayList;
import java.util.List;

public class CandidateRemovalReasoner extends EmptyCellReasoner<Integer> {
    @Override
    public boolean applyToCell(Cell<Integer> cell, CompoundCommand comms) {
        List<Integer> candidates = new ArrayList<>(cell.getCandidates());
        List<Integer> allowed = cell.getAllowedValues();
        candidates.removeAll(allowed);
        if (!candidates.isEmpty()) {
            Command command = new CandidateCommand<>(cell, candidates);
            comms.add(command);
            return true;
        }
        return false;
    }
}
