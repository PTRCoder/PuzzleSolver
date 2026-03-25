package puzzlesolver.puzzles.sudoku.puzzle;

import puzzlesolver.generics.puzzle.Group;
import lombok.Value;

import java.util.List;

@Value
public class SudokuGroup implements Group<Integer> {
    List<SudokuCell> cells;
//    @Getter(value = AccessLevel.NONE)
//    @NonFinal
//    BooleanProperty valid = null;

    @Override
    public boolean validate() {
//        if (valid == null) {
//            valid = new SimpleBooleanProperty(true);
//            ObservableList<Integer> uniques = 
//            for (SudokuCell cell : cells) {
//                ObjectProperty<Integer> value = cell.valueProperty();
//            }
//        }
//        return valid.get();
        boolean[] checks = new boolean[getSize()];
        for (SudokuCell c : cells) {
            if (!c.isValid())
                return false;
            int x = c.getValue();
            if (x == SudokuCell.EMPTY)
                continue;
            if (checks[x - 1])
                return false;
            checks[x - 1] = true;
        }
        return true;
    }

    @Override
    public List<Integer> getAllowedValues() {
        return SudokuPuzzle.staticAllowedValues(getSize());
    }
}
