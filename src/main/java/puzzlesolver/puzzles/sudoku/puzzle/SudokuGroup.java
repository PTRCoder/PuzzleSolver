package puzzlesolver.puzzles.sudoku.puzzle;

import javafx.collections.ObservableList;
import lombok.Value;
import puzzlesolver.generics.puzzle.Group;
import puzzlesolver.generics.puzzle.HexValue;

import java.util.List;

@Value
public class SudokuGroup implements Group<HexValue> {
    ObservableList<SudokuCell> cells;

    @Override
    public boolean validate() {
        boolean[] checks = new boolean[getSize()];
        for (SudokuCell c : cells) {
            if (!c.isValid())
                return false;
            HexValue x = c.getValue();
            if (x == SudokuCell.EMPTY)
                continue;
            if (checks[x.ordinal() - 1])
                return false;
            checks[x.ordinal() - 1] = true;
        }
        return true;
    }

    @Override
    public List<HexValue> getAllowedValues() {
        return SudokuPuzzle.staticAllowedValues(getSize());
    }
}
