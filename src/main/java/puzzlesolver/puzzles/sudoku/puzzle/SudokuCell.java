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
public class SudokuCell extends AbstractCell<Integer> {
    public static final int EMPTY = 0;
    int size;
    List<Integer> candidates;

    public SudokuCell(Grid<Integer> grid, int size, Position pos) {
        super(grid, pos);
        this.size = size;
        candidates = new ArrayList<>(SudokuPuzzle.staticAllowedValues(size));
    }

    @Override
    public List<Integer> getAllowedValues() {
        if (isLocked() || !valueProperty().getValue().equals(EMPTY))
            return List.of(valueProperty().getValue());
        List<Integer> allowed = new ArrayList<>(SudokuPuzzle.staticAllowedValues(size));
        allowed.removeIf(
                x -> {
                    for (Group<Integer> g : groupsProperty()) {
                        for (Cell<Integer> c : g) {
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
    protected Integer getEmpty() {
        return EMPTY;
    }

}
