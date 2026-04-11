package puzzlesolver.puzzles.kakurasu.reasoners;

import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.generics.puzzle.FillValue;
import puzzlesolver.generics.puzzle.Puzzle;
import puzzlesolver.generics.reasoners.AnyReasoner;
import puzzlesolver.generics.reasoners.Reasoner;
import puzzlesolver.generics.reasoners.RepeatedReasoner;

import java.util.List;

public class SimpleKakurasuReasoner implements Reasoner<FillValue> {
    private final Reasoner<FillValue> reasoner = new RepeatedReasoner<>(
            new AnyReasoner<>(
                    List.of(
                            new SmallReasoner(),
                            new BigReasoner(),
                            new EqualityReasoner(),
                            new SubsetReasoner()
                    )
            )
    );

    @Override
    public boolean apply(Puzzle<FillValue> puzzle, CompoundCommand comms) {
        return reasoner.apply(puzzle, comms);
    }
}
