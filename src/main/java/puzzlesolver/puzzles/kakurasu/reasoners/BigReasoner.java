package puzzlesolver.puzzles.kakurasu.reasoners;

import puzzlesolver.commands.Command;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.commands.MultiCommand;
import puzzlesolver.commands.ValueCommand;
import puzzlesolver.generics.puzzle.Group;
import puzzlesolver.puzzles.kakurasu.puzzle.KakurasuCell;
import puzzlesolver.puzzles.kakurasu.puzzle.KakurasuGroup;
import puzzlesolver.generics.puzzle.FillValue;
import puzzlesolver.generics.reasoners.UnfinishedGroupReasoner;

import java.util.LinkedList;
import java.util.List;

public class BigReasoner extends UnfinishedGroupReasoner<FillValue> {
    @Override
    public boolean applyToGroup(Group<FillValue> group, CompoundCommand comms) {
        KakurasuGroup g = (KakurasuGroup) group;
        int max = g.computeMaxSum();
        int goal = g.getSum();
        int diff = max - goal;
        int n = g.getSize();
        if (diff >= n)
            return false;
        List<KakurasuCell> cells = g.getCells();
        List<Command> commands = new LinkedList<>();
        Command mc = new MultiCommand(commands);
        for (int i = diff; i < n; i++) {
            KakurasuCell cell = cells.get(i);
            if (cell.getValue() == FillValue.EMPTY)
                commands.add(new ValueCommand<>(cell, FillValue.FILLED));
        }
        if (commands.isEmpty())
            return false;
        comms.add(mc);
        return true;
    }
}
