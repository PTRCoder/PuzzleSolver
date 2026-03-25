package puzzlesolver.generics.puzzle;

import javafx.scene.control.Label;
import puzzlesolver.generics.reasoners.Reasoner;

import java.util.List;

public interface Puzzle<T> {
    Grid<T> getGrid();

    default void print(Label parent) {
        this.getGrid().print(parent);
    }

    default boolean isFinished() {
        return getGrid().isFinished();
    }

    List<T> getAllowedValues();

    Reasoner<T> getDefaultReasoner();
}
