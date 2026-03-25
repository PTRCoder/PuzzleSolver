package puzzlesolver.puzzles.sudoku.reasoners;

import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.generics.puzzle.Puzzle;
import puzzlesolver.generics.reasoners.AllReasoner;
import puzzlesolver.generics.reasoners.AnyReasoner;
import puzzlesolver.generics.reasoners.Reasoner;
import puzzlesolver.generics.reasoners.RepeatedReasoner;

import java.util.List;

public class SimpleSudokuReasoner implements Reasoner<Integer> {
    private final Reasoner<Integer> reasoner = new RepeatedReasoner<>(
            new AllReasoner<>(
                    List.of(
                            new RepeatedReasoner<>(new CandidateRemovalReasoner()),
                            new AnyReasoner<>(
                                    List.of(
                                            new SingleGroupCandidateReasoner(),
                                            new SingleCellCandidateReasoner()
                                    )
                            )
                    )
            )
    );

    @Override
    public boolean apply(Puzzle<Integer> puzzle, CompoundCommand comms) {
        return reasoner.apply(puzzle, comms);
    }
}
