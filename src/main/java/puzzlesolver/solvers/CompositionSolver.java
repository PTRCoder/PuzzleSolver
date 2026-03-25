package puzzlesolver.solvers;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.generics.puzzle.Puzzle;

import java.util.Collection;

@Slf4j
@Value
public class CompositionSolver implements Solver {
    Puzzle<?> puzzle;
    Collection<Solver> solvers;

    @Override
    public boolean solve(CompoundCommand commands) {
        log.info("The CompositionSolver has started");
        for (Solver solver : solvers) {
            if (solver.solve(commands)) {
                log.info("The CompositionSolver has finished successfully");
                return true;
            }
        }
        log.info("The CompositionSolver has finished unsuccessfully");
        return false;
    }
}
