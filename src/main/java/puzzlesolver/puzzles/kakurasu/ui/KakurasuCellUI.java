package puzzlesolver.puzzles.kakurasu.ui;

import javafx.scene.control.CheckBox;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NonNls;
import puzzlesolver.generics.puzzle.FillValue;
import puzzlesolver.puzzles.kakurasu.puzzle.KakurasuCell;

@Slf4j
public class KakurasuCellUI extends CheckBox {
    private static final @NonNls String CSS = "kakurasu-cell";

    public KakurasuCellUI(KakurasuCell data) {
        super();
//        this.getStyleClass().clear();
        this.getStyleClass().add(CSS);
        this.applyCss();

        this.setMouseTransparent(true);
        this.focusTraversableProperty().set(false);
        this.selectedProperty().bind(data.valueProperty().isEqualTo(FillValue.CROSSED));
        this.indeterminateProperty().bind(data.valueProperty().isEqualTo(FillValue.FILLED));
//        this.textProperty().bind(this.selectedProperty().map(Object::toString));
    }

}
