package puzzlesolver.puzzles.kakurasu.reasoners;

import puzzlesolver.commands.Command;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.commands.MultiCommand;
import puzzlesolver.commands.ValueCommand;
import puzzlesolver.generics.puzzle.Cell;
import puzzlesolver.generics.puzzle.Group;
import puzzlesolver.puzzles.kakurasu.puzzle.KakurasuGroup;
import puzzlesolver.generics.puzzle.FillValue;
import puzzlesolver.generics.reasoners.UnfinishedGroupReasoner;

import java.util.LinkedList;
import java.util.List;

public class EqualityReasoner extends UnfinishedGroupReasoner<FillValue> {
    @Override
    public boolean applyToGroup(Group<FillValue> group, CompoundCommand comms) {
        KakurasuGroup g = (KakurasuGroup) group;
        int max = g.computeMaxSum();
        int current = g.computeSum();
        int goal = g.getSum();
        List<Command> commands = new LinkedList<>();
        Command mc = new MultiCommand(commands);
        if (max == current) {
            for (Cell<FillValue> c : g) {
                if (c.isEmpty())
                    commands.add(new ValueCommand<>(c, FillValue.FILLED));
            }
            comms.add(mc);
            return true;
        }
        if (goal == current) {
            for (Cell<FillValue> c : g) {
                if (c.isEmpty())
                    commands.add(new ValueCommand<>(c, FillValue.CROSSED));
            }
            comms.add(mc);
            return true;
        }
        return false;
    }
}
