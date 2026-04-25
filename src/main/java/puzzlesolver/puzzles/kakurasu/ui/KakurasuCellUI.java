package puzzlesolver.puzzles.kakurasu.ui;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NonNls;
import org.jspecify.annotations.Nullable;
import puzzlesolver.generics.puzzle.FillValue;
import puzzlesolver.puzzles.kakurasu.puzzle.KakurasuCell;
import puzzlesolver.ui.FillValueCellUI;

@Slf4j
public class KakurasuCellUI extends FillValueCellUI {
    private static final @NonNls String CSS = "kakurasu-cell";
    private static final int CROSSED_OFFSET = 10;
    private static final double CROSSED_WIDTH = 3;
    private static final Paint RED = Paint.valueOf("red");

    public KakurasuCellUI(KakurasuCell data) {
        super();
        this.getStyleClass().add(CSS);

        this.valueProperty().bindBidirectional(data.valueProperty());
    }

    @Override
    protected @Nullable Node conversion(FillValue value) {
        return switch (value) {
            case EMPTY, BLOCKED -> null;
            case CROSSED -> {
                Line l1 = new Line();
                l1.startXProperty().set(CROSSED_OFFSET);
                l1.startYProperty().set(CROSSED_OFFSET);
                l1.endXProperty().bind(this.widthProperty().subtract(CROSSED_OFFSET));
                l1.endYProperty().bind(this.heightProperty().subtract(CROSSED_OFFSET));
                l1.setStrokeWidth(CROSSED_WIDTH);
                l1.setStroke(RED);
                Line l2 = new Line();
                l2.startXProperty().bind(l1.startXProperty());
                l2.startYProperty().bind(l1.endYProperty());
                l2.endXProperty().bind(l1.endXProperty());
                l2.endYProperty().bind(l1.startYProperty());
                l2.setStrokeWidth(CROSSED_WIDTH);
                l2.setStroke(RED);

                StackPane cross = new StackPane();
                cross.getChildren().addAll(l1, l2);
                yield cross;
            }
            case FILLED -> {
                Rectangle s = new Rectangle();
                s.heightProperty().bind(this.heightProperty().subtract(5));
                s.widthProperty().bind(this.widthProperty().subtract(5));
                yield s;
            }
        };
    }
}
