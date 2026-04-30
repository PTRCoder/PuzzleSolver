package puzzlesolver.puzzles.kakurasu.ui;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NonNls;
import puzzlesolver.generics.puzzle.Position;
import puzzlesolver.puzzles.kakurasu.puzzle.KakurasuCell;
import puzzlesolver.puzzles.kakurasu.puzzle.KakurasuGrid;
import puzzlesolver.puzzles.kakurasu.puzzle.KakurasuGroup;
import puzzlesolver.ui.LineLabel;

import java.util.Collection;

@Slf4j
public final class KakurasuGridUI extends GridPane {
    private static final @NonNls String CSS_CLASS = "kakurasu-grid";
    private static final @NonNls String LABEL_CSS_CLASS = "kakurasu-label";
    private static final double CELL_MIN_SIZE = 30;

    public KakurasuGridUI(KakurasuGrid data) {
        super();
        this.getStyleClass().add(CSS_CLASS);

        int w = data.getWidth() + 1;
        int h = data.getHeight() + 1;

        this.minHeightProperty().set(h * CELL_MIN_SIZE);
        this.maxHeightProperty().set(w * CELL_MIN_SIZE);

        double cellMaxSize = Math.min(this.getMaxHeight() / h, this.getMaxWidth() / w);

        ColumnConstraints cCons = new ColumnConstraints(CELL_MIN_SIZE, cellMaxSize, cellMaxSize);
        RowConstraints rCons = new RowConstraints(CELL_MIN_SIZE, cellMaxSize, cellMaxSize);

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

        int i = 0;
        for (KakurasuGroup group : data.getCols()) {
            LineLabel label = new LineLabel(group.getSum());
            label.textFillProperty().bind(createLabelPaint(group));
            label.getStyleClass().add(LABEL_CSS_CLASS);
            this.add(label, i++, h - 1);
        }
        i = 0;
        for (KakurasuGroup group : data.getRows()) {
            LineLabel label = new LineLabel(group.getSum());
            label.textFillProperty().bind(createLabelPaint(group));
            label.getStyleClass().add(LABEL_CSS_CLASS);
            this.add(label, w - 1, i++);
        }
    }

    private static ObservableValue<Color> createLabelPaint(KakurasuGroup group) {
        return Bindings.when(group.validProperty())
                .then(
                        Bindings.when(group.finishedProperty())
                                .then(Color.LIGHTGRAY)
                                .otherwise(Color.BLUE)
                ).otherwise(Color.RED);
    }
}
