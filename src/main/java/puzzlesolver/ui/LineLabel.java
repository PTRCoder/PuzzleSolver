package puzzlesolver.ui;

import javafx.scene.control.Labeled;
import javafx.scene.control.Skin;
import javafx.scene.control.skin.LabeledSkinBase;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NonNls;

@EqualsAndHashCode(callSuper = true)
@Value
@Slf4j
public class LineLabel extends Labeled {
    private static final @NonNls String CSS_CLASS = "line-label";
    int value;

    public LineLabel(int value) {
        super();
        this.getStyleClass().add(CSS_CLASS);
        this.value = value;
        this.textProperty().set(Integer.toString(value));
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new LineLabelSkin(this);
    }

    private static class LineLabelSkin extends LabeledSkinBase<LineLabel> {

        public LineLabelSkin(LineLabel labeled) {
            super(labeled);
        }
    }
}
