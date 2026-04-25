package puzzlesolver.ui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.control.Skin;
import javafx.scene.control.skin.LabeledSkinBase;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import puzzlesolver.generics.puzzle.FillValue;

@EqualsAndHashCode(callSuper = true)
@ToString
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
@Accessors(fluent = true)
@Slf4j
public abstract class FillValueCellUI extends Labeled {
    Property<FillValue> valueProperty = new SimpleObjectProperty<>(FillValue.EMPTY);
    BooleanProperty editableProperty = new SimpleBooleanProperty();

    public FillValueCellUI() {
        super();
        this.setOnMouseReleased(this::input);
        this.graphicProperty().bind(valueProperty.map(this::conversion));
        this.editableProperty().setValue(true);
    }

    protected abstract @Nullable Node conversion(FillValue value);

    private void input(MouseEvent e) {
        if (editableProperty().get()) {
            MouseButton mb = e.getButton();
            log.debug("{}", mb);
            switch (mb) {
                case PRIMARY -> traverseL(valueProperty());
                case SECONDARY -> traverseR(valueProperty());
            }
        }
    }

    private static void traverseL(Property<FillValue> prop) {
        FillValue old = prop.getValue();
        FillValue n = switch (old) {
            case EMPTY, CROSSED -> FillValue.FILLED;
            case FILLED -> FillValue.EMPTY;
            default -> throw new IllegalArgumentException();
        };
        prop.setValue(n);
    }

    private static void traverseR(Property<FillValue> prop) {
        FillValue old = prop.getValue();
        FillValue n = switch (old) {
            case EMPTY, FILLED -> FillValue.CROSSED;
            case CROSSED -> FillValue.EMPTY;
            default -> throw new IllegalArgumentException();
        };
        prop.setValue(n);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new FillValueCellUISkin(this);
    }

    private static class FillValueCellUISkin extends LabeledSkinBase<FillValueCellUI> {

        public FillValueCellUISkin(FillValueCellUI labeled) {
            super(labeled);
        }
    }
}
