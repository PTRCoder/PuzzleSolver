package puzzlesolver.puzzles.binairo.reasoners;

import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.generics.puzzle.BinaryValue;
import puzzlesolver.generics.puzzle.Puzzle;
import puzzlesolver.generics.reasoners.AnyReasoner;
import puzzlesolver.generics.reasoners.Reasoner;
import puzzlesolver.generics.reasoners.RepeatedReasoner;

import java.util.List;

public class SimpleBinairoReasoner implements Reasoner<BinaryValue> {
    private final Reasoner<BinaryValue> reasoner = new RepeatedReasoner<>(
            new AnyReasoner<>(
                    List.of(new GapReasoner(), new DoubleReasoner(), new CountReasoner())
            )
    );

    @Override
    public boolean apply(Puzzle<BinaryValue> puzzle, CompoundCommand comms) {
        return reasoner.apply(puzzle, comms);
    }
}
