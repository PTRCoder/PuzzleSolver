package puzzlesolver.puzzles.sudoku.ui;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Line;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NonNls;
import puzzlesolver.generics.puzzle.Position;
import puzzlesolver.puzzles.sudoku.puzzle.SudokuCell;
import puzzlesolver.puzzles.sudoku.puzzle.SudokuGrid;

import java.util.Collection;

@Slf4j
public final class SudokuGridUI extends GridPane {
    private static final @NonNls String CSS_CLASS = "sudoku-grid";
    private static final int CELL_SIZE = 60;
    private static final int BORDER_WIDTH = 6;
    private static final int HLINE_OFFSET = -(CELL_SIZE / 2 + 1);
    private static final int HLINE_OFFSET2 = CELL_SIZE / 2 - 1;
    private static final int VLINE_OFFSET = -(BORDER_WIDTH / 2);
    private static final int VLINE_OFFSET2 = CELL_SIZE - BORDER_WIDTH / 2;

    public SudokuGridUI(SudokuGrid data) {
        super();
        this.getStyleClass().clear();
        this.getStyleClass().add(CSS_CLASS);
        this.applyCss();

        ColumnConstraints cCons = new ColumnConstraints(CELL_SIZE, CELL_SIZE, CELL_SIZE);
        RowConstraints rCons = new RowConstraints(CELL_SIZE, CELL_SIZE, CELL_SIZE);

        int size = data.getWidth();
        int sqrt = (int) Math.sqrt(size);

        Collection<SudokuCell> cells = data.getCells();

        for (int i = 0; i < size; i++) {
            this.getColumnConstraints().add(cCons);
            this.getRowConstraints().add(rCons);
        }
        for (var cell : cells) {
            Position pos = cell.getPosition();
            this.add(new SudokuCellUI(cell), pos.x(), pos.y());
        }

        for (int i = 0; i < sqrt; i++) {
            this.add(createVLine(VLINE_OFFSET), i * sqrt, sqrt + 1);
            this.add(createHLine(HLINE_OFFSET), 0, i * sqrt);
        }
        this.add(createVLine(VLINE_OFFSET2), size - 1, sqrt + 1);
        this.add(createHLine(HLINE_OFFSET2), 0, size - 1);
    }

    private Line createVLine(int translate) {
        Line line = new Line();
        line.startXProperty().setValue(0);
        line.startYProperty().setValue(1);
        line.endXProperty().setValue(0);
        line.endYProperty().bind(this.heightProperty());
        line.setTranslateX(translate);
        line.setTranslateY(-1);
        line.setStrokeWidth(6);
        return line;
    }

    private Line createHLine(int translate) {
        Line line = new Line();
        line.startXProperty().setValue(0);
        line.startYProperty().setValue(0);
        line.endYProperty().setValue(0);
        line.endXProperty().bind(this.heightProperty());
        line.setTranslateY(translate);
        line.setTranslateX(-3);
        line.setStrokeWidth(BORDER_WIDTH);
        return line;
    }
}
