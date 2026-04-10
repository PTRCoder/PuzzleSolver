package puzzlesolver.puzzles.starbattle.reasoners;

import lombok.extern.slf4j.Slf4j;
import puzzlesolver.commands.Command;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.commands.ValueCommand;
import puzzlesolver.generics.puzzle.Cell;
import puzzlesolver.generics.puzzle.FillValue;
import puzzlesolver.generics.puzzle.Group;
import puzzlesolver.generics.reasoners.UnfinishedGroupReasoner;
import puzzlesolver.puzzles.starbattle.puzzle.StarBattleGroup;

@Slf4j
public class BruteGroupReasoner extends UnfinishedGroupReasoner<FillValue> {
//    private static final SquareReasoner sqr = new SquareReasoner();

    @Override
    public boolean applyToGroup(Group<FillValue> group, CompoundCommand comms) {
        if (group instanceof StarBattleGroup g) {
            for (Cell<FillValue> c : g) {
                if (!c.isEmpty())
                    continue;
                for (FillValue x : g.getAllowedValues()) {
                    Command command = new ValueCommand<>(c, FillValue.FILLED);
                    command.apply();
                    if (brute(g)) {
                        comms.add(command);
                        return true;
                    }
                    command.undo();
                }
            }
        }
        return false;
    }

    private boolean brute(StarBattleGroup g) {
        boolean result;
        for (Cell<FillValue> c : g) {
            if (!c.isEmpty())
                continue;
            for (FillValue x : c.getAllowedValues()) {
                c.setValue(x);
                result = brute(g);
                c.setValue(FillValue.EMPTY);
                if (result) {
                    return true;
                }
            }
        }
        return false;
    }
}
