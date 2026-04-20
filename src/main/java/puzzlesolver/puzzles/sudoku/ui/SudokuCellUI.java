package puzzlesolver.puzzles.sudoku.ui;

import lombok.extern.slf4j.Slf4j;
import org.controlsfx.control.GridCell;
import org.jetbrains.annotations.NonNls;
import puzzlesolver.generics.puzzle.HexValue;
import puzzlesolver.puzzles.sudoku.puzzle.SudokuCell;

@Slf4j
public class SudokuCellUI extends GridCell<SudokuCell> {
    private static final @NonNls String CSS = "sudoku-cell";

    public SudokuCellUI() {
        super();
        this.getStyleClass().add(CSS);
    }

    @Override
    protected void updateItem(SudokuCell cell, boolean empty) {
        super.updateItem(cell, empty);
        this.graphicProperty().unbind();
        if (empty) {
            this.textProperty().unbind();
            return;
        }
        this.textProperty().bind(cell.valueProperty().map(HexValue::toText));
    }
}
