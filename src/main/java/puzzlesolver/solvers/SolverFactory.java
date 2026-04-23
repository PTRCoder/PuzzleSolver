package puzzlesolver.solvers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Value;
import lombok.experimental.Accessors;
import org.jspecify.annotations.Nullable;
import puzzlesolver.generics.puzzle.Puzzle;

import java.util.List;

@Value
@Accessors(fluent = true)
public class SolverFactory {
    Property<@Nullable Puzzle<?>> puzzle;
    BooleanProperty reasonerProperty = new SimpleBooleanProperty(false);
    BooleanProperty backtrackProperty = new SimpleBooleanProperty(true);

    public Solver build() {
        boolean bt = backtrackProperty.get();
        boolean r = reasonerProperty.get();
        Puzzle<?> p = puzzle.getValue();
        if (p == null || !bt && !r)
            throw new IllegalStateException();
        if (!r)
            return new BacktrackSolver<>(p);
        Solver reasonSolver = new ReasonSolver<>(p);
        if (!bt)
            return reasonSolver;
        return new CompositionSolver(p,
                List.of(reasonSolver, new BacktrackSolver<>(p))
        );
    }

}
