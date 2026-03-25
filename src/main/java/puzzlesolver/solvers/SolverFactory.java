package puzzlesolver.solvers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.*;
import org.jspecify.annotations.Nullable;
import puzzlesolver.generics.puzzle.Puzzle;

import java.util.List;

@EqualsAndHashCode
@ToString
public final class SolverFactory {
    @Getter
    @Nullable
    private Puzzle<?> puzzle;
    private final BooleanProperty useReasoner = new SimpleBooleanProperty(false);
    private final BooleanProperty useBacktrack = new SimpleBooleanProperty(true);

    public BooleanProperty reasonerProperty() {
        return useReasoner;
    }

    public BooleanProperty backtrackProperty() {
        return useBacktrack;
    }

    public SolverFactory withPuzzle(Puzzle<?> puzzle) {
        this.puzzle = puzzle;
        return this;
    }

    public SolverFactory withUseReasoner(boolean use) {
        this.useReasoner.set(use);
        return this;
    }

    public SolverFactory withUseBacktrack(boolean use) {
        this.useBacktrack.set(use);
        return this;
    }

    public boolean usesReasoner() {
        return useReasoner.get();
    }

    public boolean usesBacktrack() {
        return useBacktrack.get();
    }

    public Solver build() {
        boolean bt = useBacktrack.get();
        boolean r = useReasoner.get();
        if (puzzle == null || !bt && !r)
            throw new IllegalStateException();
        if (!r)
            return new BacktrackSolver<>(puzzle);
        Solver reasonSolver = new ReasonSolver<>(puzzle);
        if (!bt)
            return reasonSolver;
        return new CompositionSolver(puzzle,
                List.of(reasonSolver, new BacktrackSolver<>(puzzle))
        );
    }

}
