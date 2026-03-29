package puzzlesolver.puzzles.binairo.reasoners;

import lombok.extern.slf4j.XSlf4j;
import puzzlesolver.commands.Command;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.commands.MultiCommand;
import puzzlesolver.commands.ValueCommand;
import puzzlesolver.generics.puzzle.BinaryValue;
import puzzlesolver.generics.puzzle.Cell;
import puzzlesolver.generics.puzzle.Group;
import puzzlesolver.generics.reasoners.UnfinishedGroupReasoner;
import puzzlesolver.puzzles.binairo.puzzle.BinairoLane;

import java.util.LinkedList;
import java.util.List;

@XSlf4j
public class CountReasoner extends UnfinishedGroupReasoner<BinaryValue> {

    @Override
    public boolean applyToGroup(Group<BinaryValue> group, CompoundCommand comms) {
        BinairoLane g = (BinairoLane) group;
        int n = group.getSize();
        int half = n / 2;
        long t0 = System.nanoTime();
        int wSum = g.wSumProperty().get();
        int bSum = g.bSumProperty().get();
        BinaryValue filler = half == wSum ? BinaryValue.BLACK :
                half == bSum ? BinaryValue.WHITE : BinaryValue.EMPTY;
        if (filler != BinaryValue.EMPTY) {
            List<Command> commands = new LinkedList<>();
            Command mc = new MultiCommand(commands);
            for (Cell<BinaryValue> c : group) {
                if (c.isEmpty())
                    commands.add(new ValueCommand<>(c, filler));
            }
            comms.add(mc);
            return true;
        }
        return false;
    }
}
