package puzzlesolver.solvers;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.generics.puzzle.Puzzle;
import puzzlesolver.generics.reasoners.Reasoner;

@Slf4j
@Value
public class ReasonSolver<T> implements Solver {
    Puzzle<T> puzzle;
    Reasoner<T> reasoner;

    public ReasonSolver(Puzzle<T> puzzle) {
        this.puzzle = puzzle;
        this.reasoner = puzzle.getDefaultReasoner();
    }

    @Override
    public boolean solve(CompoundCommand commands) {
        log.info("The ReasonSolver has started");
        reasoner.apply(puzzle, commands);
        boolean result = puzzle.isFinished();
        log.info("The ReasonSolver has finished {}successfully", result ? "" : "un");
        return result;
    }
}
