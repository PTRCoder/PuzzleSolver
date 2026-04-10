package puzzlesolver.puzzles.sudoku.puzzle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import puzzlesolver.generics.puzzle.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@EqualsAndHashCode
@ToString
@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public final class SudokuCell implements Cell<HexValue> {
    List<HexValue> candidates;
    @Accessors(fluent = true)
    private final BooleanProperty lockedProperty = new SimpleBooleanProperty();
    @Accessors(fluent = true)
    private final LockableProperty<HexValue> valueProperty = new LockableProperty<>(lockedProperty);
    @Accessors(fluent = true)
    private final ListProperty<Group<HexValue>> groupsProperty =
            new SimpleListProperty<>(FXCollections.observableList(new LinkedList<>()));
    private final SudokuGrid grid;
    private final Position position;
    int size;

    public SudokuCell(SudokuGrid grid, int size, Position position) {
        this.size = size;
        this.grid = grid;
        this.position = position;
        candidates = new ArrayList<>(SudokuPuzzle.staticAllowedValues(size));
    }

    @Override
    public Collection<HexValue> getAllowedValues() {
        if (isLocked() || !valueProperty().getValue().isEmpty() && !valueProperty().getValue().isBlocked())
            return List.of(valueProperty().getValue());
        List<HexValue> allowed = new ArrayList<>(SudokuPuzzle.staticAllowedValues(size));
        allowed.removeIf(
                x -> {
                    for (Group<HexValue> g : groupsProperty()) {
                        for (Cell<HexValue> c : g) {
                            if (c.getValue().equals(x))
                                return true;
                        }
                    }
                    return false;
                }
        );
        return allowed;
    }

}
