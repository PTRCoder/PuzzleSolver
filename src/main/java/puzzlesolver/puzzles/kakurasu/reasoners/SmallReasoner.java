package puzzlesolver.puzzles.kakurasu.reasoners;

import puzzlesolver.commands.Command;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.commands.MultiCommand;
import puzzlesolver.commands.ValueCommand;
import puzzlesolver.generics.puzzle.FillValue;
import puzzlesolver.generics.puzzle.Group;
import puzzlesolver.generics.reasoners.UnfinishedGroupReasoner;
import puzzlesolver.puzzles.kakurasu.puzzle.KakurasuCell;
import puzzlesolver.puzzles.kakurasu.puzzle.KakurasuGroup;

import java.util.LinkedList;
import java.util.List;

public class SmallReasoner extends UnfinishedGroupReasoner<FillValue> {
    @Override
    public boolean applyToGroup(Group<FillValue> group, CompoundCommand comms) {
        KakurasuGroup g = (KakurasuGroup) group;
        int goal = g.getSum();
        int current = g.computeSum();
        int todo = goal - current;
        List<KakurasuCell> cells = g.getCells();
        List<Command> commands = new LinkedList<>();
        Command mc = new MultiCommand(commands);
        for (int i = todo + 1; i < g.getSize(); i++) {
            KakurasuCell cell = cells.get(i);
            if (cell.getValue() == FillValue.EMPTY)
                commands.add(new ValueCommand<>(cell, FillValue.CROSSED));
        }
        if (commands.isEmpty())
            return false;
        comms.add(mc);
        return true;
    }
}
