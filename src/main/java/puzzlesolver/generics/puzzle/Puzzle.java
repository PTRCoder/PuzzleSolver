package puzzlesolver.generics.puzzle;

import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NonNls;
import puzzlesolver.generics.reasoners.Reasoner;

public interface Puzzle<T extends PuzzleValue> {
    Grid<T> getGrid();

    default GridPane getView() {
        throw new UnsupportedOperationException();
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
