package puzzlesolver.puzzles.sudoku.ui;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NonNls;
import puzzlesolver.generics.puzzle.HexValue;
import puzzlesolver.puzzles.sudoku.puzzle.SudokuCell;

@Slf4j
public class SudokuCellUI extends Label {
    private static final @NonNls String CSS = "sudoku-cell";
    private static final Paint LOCKED_COLOR = Paint.valueOf("black");
    private static final Paint UNLOCKED_COLOR = Paint.valueOf("blue");

    public SudokuCellUI(SudokuCell data) {
        super();
        this.getStyleClass().clear();
        this.getStyleClass().add(CSS);
        this.applyCss();

        this.textProperty().bind(data.valueProperty().map(HexValue::toText));

        this.graphicProperty().bind(
                Bindings.when(BooleanExpression.booleanExpression(data.valueProperty().map(HexValue::isEmpty)))
                        .then((Node) null /* use subgrid */)
                        .otherwise((Node) null)
        );
        this.textFillProperty().bind(Bindings.when(data.lockedProperty()).then(LOCKED_COLOR).otherwise(UNLOCKED_COLOR));
    }

}
