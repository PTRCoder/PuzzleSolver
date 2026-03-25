package puzzlesolver.puzzles.sudoku.puzzle;

import lombok.Value;
import puzzlesolver.generics.puzzle.Group;

import java.util.List;

@Value
public class SudokuGroup implements Group<Integer> {
    List<SudokuCell> cells;

    @Override
    public boolean validate() {
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
