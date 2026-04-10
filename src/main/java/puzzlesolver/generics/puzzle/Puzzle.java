package puzzlesolver.generics.puzzle;

import javafx.scene.control.Label;
import org.jetbrains.annotations.NonNls;
import puzzlesolver.generics.reasoners.Reasoner;

public interface Puzzle<T extends PuzzleValue> {
    Grid<T> getGrid();

    default void print(Label parent) {
        this.getGrid().print(parent);
    }

    default boolean isFinished() {
        return getGrid().isFinished();
    }

    Reasoner<T> getDefaultReasoner();

    @NonNls
    default String encodeCurrentState() {
        return getGrid().encode();
    }
}
