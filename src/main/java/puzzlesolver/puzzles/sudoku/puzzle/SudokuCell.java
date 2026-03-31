package puzzlesolver.puzzles.sudoku.puzzle;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import puzzlesolver.generics.puzzle.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SudokuCell extends AbstractCell<HexValue> {
    public static final HexValue EMPTY = new HexValue(0);
    int size;
    List<HexValue> candidates;

    public SudokuCell(Grid<HexValue> grid, int size, Position pos) {
        super(grid, pos);
        this.size = size;
        candidates = new ArrayList<>(SudokuPuzzle.staticAllowedValues(size));
    }

    @Override
    public List<HexValue> getAllowedValues() {
        if (isLocked() || !valueProperty().getValue().equals(EMPTY))
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


    @Override
    protected HexValue getEmpty() {
        return EMPTY;
    }

}
