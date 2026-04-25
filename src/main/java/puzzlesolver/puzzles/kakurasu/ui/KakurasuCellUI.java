package puzzlesolver.puzzles.kakurasu.ui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NonNls;
import puzzlesolver.generics.puzzle.FillValue;
import puzzlesolver.puzzles.kakurasu.puzzle.KakurasuCell;
import puzzlesolver.ui.FillValueCellUI;

@Slf4j
public class KakurasuCellUI extends FillValueCellUI {
    private static final @NonNls String CSS = "kakurasu-cell";

    public KakurasuCellUI(KakurasuCell data) {
        super();
//        this.getStyleClass().clear();
        this.getStyleClass().add(CSS);
        this.applyCss();

        this.valueProperty().bindBidirectional(data.valueProperty());
    }

    @Override
    protected Node conversion(FillValue value) {
        return new Label(value.toText());
    }
}
