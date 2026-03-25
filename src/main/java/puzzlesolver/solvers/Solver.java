package puzzlesolver.solvers;

import puzzlesolver.commands.CompoundCommand;

public interface Solver {
    boolean solve(CompoundCommand commands);
}
