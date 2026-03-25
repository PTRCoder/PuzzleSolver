package puzzlesolver.puzzles.sudoku.reasoners;

import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.commands.ValueCommand;
import puzzlesolver.generics.puzzle.Cell;
import puzzlesolver.generics.puzzle.Group;
import puzzlesolver.generics.reasoners.UnfinishedGroupReasoner;

import java.util.List;

public class SingleGroupCandidateReasoner extends UnfinishedGroupReasoner<Integer> {
    @Override
    public boolean applyToGroup(Group<Integer> group, CompoundCommand comms) {
        for (int x : group.getAllowedValues()) {
            Cell<Integer> c0 = null;
            boolean broken = false;
            for (Cell<Integer> cell : group) {
                if (cell.isLocked() || !cell.isEmpty())
                    continue;
                List<Integer> allowed = cell.getCandidates();
                if (allowed.contains(x)) {
                    if (c0 == null)
                        c0 = cell;
                    else {
                        broken = true;
                        break;
                    }
                }
            }
            if (!broken && c0 != null) {
                comms.add(new ValueCommand<>(c0, x));
                return true;
            }
        }
        return false;
    }
}
