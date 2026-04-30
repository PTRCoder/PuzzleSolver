package puzzlesolver.puzzles.kakurasu.ui;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NonNls;
import puzzlesolver.puzzles.kakurasu.puzzle.KakurasuCell;
import puzzlesolver.ui.FillValueCellUI;

@Slf4j
public class KakurasuCellUI extends FillValueCellUI {
    private static final @NonNls String CSS = "kakurasu-cell";
    private static final int CROSSED_OFFSET = 10;
    private static final double CROSSED_WIDTH = 3;

    public KakurasuCellUI(KakurasuCell data) {
        super();
        this.getStyleClass().add(CSS);

        this.valueProperty().bindBidirectional(data.valueProperty());
        this.editableProperty().bind(data.lockedProperty().not());
    }

    @Override
    protected Node createFilled() {
        Rectangle s = new Rectangle();
        s.heightProperty().bind(this.heightProperty().subtract(5));
        s.widthProperty().bind(this.widthProperty().subtract(5));
        return s;
    }

    @Override
    protected Node createCrossed() {
        Line l1 = new Line();
        l1.startXProperty().set(CROSSED_OFFSET);
        l1.startYProperty().set(CROSSED_OFFSET);
        l1.endXProperty().bind(this.widthProperty().subtract(CROSSED_OFFSET));
        l1.endYProperty().bind(this.heightProperty().subtract(CROSSED_OFFSET));
        l1.setStrokeWidth(CROSSED_WIDTH);
        l1.setStroke(Color.RED);
        Line l2 = new Line();
        l2.startXProperty().bind(l1.startXProperty());
        l2.startYProperty().bind(l1.endYProperty());
        l2.endXProperty().bind(l1.endXProperty());
        l2.endYProperty().bind(l1.startYProperty());
        l2.setStrokeWidth(CROSSED_WIDTH);
        l2.setStroke(Color.RED);

        StackPane cross = new StackPane();
        cross.getChildren().addAll(l1, l2);
        return cross;
    }
}
