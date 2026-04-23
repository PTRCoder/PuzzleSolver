package puzzlesolver.puzzles.kakurasu.ui;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NonNls;
import puzzlesolver.generics.puzzle.Position;
import puzzlesolver.puzzles.kakurasu.puzzle.KakurasuCell;
import puzzlesolver.puzzles.kakurasu.puzzle.KakurasuGrid;

import java.util.Collection;

@Slf4j
public final class KakurasuGridUI extends GridPane {
    private static final @NonNls String CSS_CLASS = "kakurasu-grid";
    private static final int CELL_SIZE = 30;

    public KakurasuGridUI(KakurasuGrid data) {
        super();
        this.getStyleClass().clear();
        this.getStyleClass().add(CSS_CLASS);
        this.applyCss();

        ColumnConstraints cCons = new ColumnConstraints(CELL_SIZE, CELL_SIZE, CELL_SIZE);
        RowConstraints rCons = new RowConstraints(CELL_SIZE, CELL_SIZE, CELL_SIZE);

        int w = data.getWidth();
        int h = data.getHeight();

        Collection<KakurasuCell> cells = data.getCells();

        for (int i = 0; i < w; i++) {
            this.getColumnConstraints().add(cCons);
        }
        for (int i = 0; i < h; i++) {
            this.getRowConstraints().add(rCons);
        }
        for (var cell : cells) {
            Position pos = cell.getPosition();
            this.add(new KakurasuCellUI(cell), pos.x(), pos.y());
        }

        this.setGridLinesVisible(true);
    }
}
