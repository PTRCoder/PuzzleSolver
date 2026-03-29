package puzzlesolver.puzzles.starbattle.reasoners;

import lombok.Value;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.generics.puzzle.FillValue;
import puzzlesolver.generics.puzzle.Puzzle;
import puzzlesolver.generics.reasoners.AnyReasoner;
import puzzlesolver.generics.reasoners.Reasoner;
import puzzlesolver.generics.reasoners.RepeatedReasoner;

import java.util.List;

@Value
public class DefaultStarBattleReasoner implements Reasoner<FillValue> {
    Reasoner<FillValue> reasoner = new RepeatedReasoner<>(new AnyReasoner<>(List.of(
            new SquareReasoner(), new SingularReasoner()//, new BruteGroupReasoner()
    )));

    @Override
    public boolean apply(Puzzle<FillValue> puzzle, CompoundCommand comms) {
        return reasoner.apply(puzzle, comms);
    }
}
