package puzzlesolver.solvers;

import lombok.extern.slf4j.Slf4j;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.generics.puzzle.Puzzle;
import puzzlesolver.loc.LogStrings;

import java.util.Collection;

@Slf4j
public record CompositionSolver(Puzzle<?> puzzle, Collection<Solver> solvers) implements Solver {
    @Override
    public boolean solve(CompoundCommand commands) {
        log.info(LogStrings.SOLVER_START.get(), this.getClass());
        for (Solver solver : solvers) {
            if (solver.solve(commands)) {
                log.info(LogStrings.SOLVER_SUCCESS.get(), this.getClass());
                return true;
            }
        }
        log.info(LogStrings.SOLVER_FAIL.get(), this.getClass());
        return false;
    }
}
