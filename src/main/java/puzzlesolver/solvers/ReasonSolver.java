package puzzlesolver.solvers;

import lombok.extern.slf4j.Slf4j;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.generics.puzzle.Puzzle;
import puzzlesolver.generics.puzzle.PuzzleValue;
import puzzlesolver.generics.reasoners.Reasoner;
import puzzlesolver.loc.LogStrings;

@Slf4j
public record ReasonSolver<T extends PuzzleValue>(Puzzle<T> puzzle, Reasoner<T> reasoner) implements Solver {

    public ReasonSolver(Puzzle<T> puzzle) {
        this(puzzle, puzzle.getDefaultReasoner());
    }

    @Override
    public boolean solve(CompoundCommand commands) {
        log.info(LogStrings.SOLVER_START.get(), this.getClass());
        reasoner.apply(puzzle, commands);
        boolean result = puzzle.isFinished();
        log.info(result ? LogStrings.SOLVER_SUCCESS.get() : LogStrings.SOLVER_FAIL.get(), this.getClass());
        return result;
    }
}
