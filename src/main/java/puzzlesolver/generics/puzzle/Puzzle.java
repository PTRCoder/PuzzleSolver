package puzzlesolver.generics.puzzle;

import javafx.scene.control.Label;
import puzzlesolver.generics.reasoners.Reasoner;

import java.util.Collection;

public interface Puzzle<T extends PuzzleValue> {
    Grid<T> getGrid();

    default void print(Label parent) {
        this.getGrid().print(parent);
    }

    default boolean isFinished() {
        return getGrid().isFinished();
    }

    Collection<T> getAllowedValues();

    Reasoner<T> getDefaultReasoner();

    String valueToString(T value);
}
