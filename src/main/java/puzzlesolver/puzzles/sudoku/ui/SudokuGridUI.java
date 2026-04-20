package puzzlesolver.puzzles.sudoku.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.extern.slf4j.Slf4j;
import org.controlsfx.control.GridView;
import org.jetbrains.annotations.NonNls;
import puzzlesolver.puzzles.sudoku.puzzle.SudokuCell;
import puzzlesolver.puzzles.sudoku.puzzle.SudokuGrid;

import java.util.List;

@Slf4j
public final class SudokuGridUI extends GridView<SudokuCell> {
    private static final @NonNls String CSS_CLASS = "sudoku-grid";

    public SudokuGridUI(SudokuGrid data) {
        super();
        this.getStyleClass().clear();
        this.getStyleClass().add(CSS_CLASS);
        this.applyCss();

        double w = data.getWidth() * (this.getCellWidth() + 2);
        double h = data.getHeight() * (this.getCellHeight() + 2);
        this.setPrefWidth(w);
        this.setPrefHeight(h);
        log.info(this.getStyleClass().toString());
        log.info(this.getCssMetaData().toString());
        log.info("w: {}, h: {}", this.getCellWidth(), this.getCellHeight());
        ObservableList<SudokuCell> values =
                FXCollections.observableList(data.getCells().stream().flatMap(List::stream).toList());
        this.setCellFactory(g -> new SudokuCellUI());
        this.setItems(values);
    }
}
