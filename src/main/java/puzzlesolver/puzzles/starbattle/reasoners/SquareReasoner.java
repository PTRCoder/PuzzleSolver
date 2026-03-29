package puzzlesolver.puzzles.starbattle.reasoners;

import puzzlesolver.commands.Command;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.commands.MultiCommand;
import puzzlesolver.commands.ValueCommand;
import puzzlesolver.generics.puzzle.Cell;
import puzzlesolver.generics.puzzle.FillValue;
import puzzlesolver.generics.puzzle.Group;
import puzzlesolver.generics.reasoners.UnfinishedGroupReasoner;
import puzzlesolver.puzzles.starbattle.puzzle.StarBattleSquare;

import java.util.LinkedList;
import java.util.List;

public class SquareReasoner extends UnfinishedGroupReasoner<FillValue> {
    @Override
    public boolean applyToGroup(Group<FillValue> group, CompoundCommand comms) {
        if (group instanceof StarBattleSquare sq && !sq.allowsStarProperty().get()) {
            List<Command> commands = new LinkedList<>();
            Command mc = new MultiCommand(commands);
            for (Cell<FillValue> c : sq) {
                if (c.isEmpty())
                    commands.add(new ValueCommand<>(c, FillValue.CROSSED));
            }
            comms.add(mc);
            return true;
        }
        else return false;
    }
}
