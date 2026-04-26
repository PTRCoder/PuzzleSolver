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
import javafx.scene.layout.StackPane;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import puzzlesolver.generics.puzzle.FillValue;

import java.util.concurrent.atomic.AtomicReference;

@EqualsAndHashCode(callSuper = true)
@ToString
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
@Accessors(fluent = true)
@Slf4j
public abstract class FillValueCellUI extends Labeled {
    private static final AtomicReference<@Nullable FillValue> replacement = new AtomicReference<>(null);

    @Getter(AccessLevel.NONE)
    Node filled = createFilled();
    @Getter(AccessLevel.NONE)
    Node crossed = createCrossed();

    Property<FillValue> valueProperty = new SimpleObjectProperty<>(FillValue.EMPTY);
    BooleanProperty editableProperty = new SimpleBooleanProperty();

    public FillValueCellUI() {
        super();
        this.setOnMousePressed(this::input);
        this.setOnDragDetected(e -> this.startFullDrag());
        this.setOnMouseDragEntered(this::inputDrag);
        this.setOnMouseDragReleased(e -> replacement.set(null));
        StackPane pane = new StackPane();
        pane.getChildren().addAll(filled, crossed);
        this.setGraphic(pane);
        this.editableProperty().setValue(true);
        crossed.visibleProperty().bind(valueProperty().map(x -> x == FillValue.CROSSED));
        filled.visibleProperty().bind(valueProperty().map(x -> x == FillValue.FILLED));
    }

    protected abstract Node createFilled();

    protected abstract Node createCrossed();

    private void input(MouseEvent e) {
        if (editableProperty().get()) {
            MouseButton mb = e.getButton();
            log.debug("{}", mb);
            FillValue old = valueProperty().getValue();
            FillValue n;
            switch (mb) {
                case PRIMARY -> n = traverseL(old);
                case SECONDARY -> n = traverseR(old);
                default -> {return;}
            }
            valueProperty().setValue(n);
            replacement.set(n);
        }
    }

    private void inputDrag(MouseEvent e) {
        FillValue rep = replacement.get();
        if (editableProperty().get() && rep != null) {
            log.debug("{}", rep);
            valueProperty().setValue(rep);
        }
    }

    private static FillValue traverseL(FillValue old) {
        return switch (old) {
            case EMPTY, CROSSED -> FillValue.FILLED;
            case FILLED -> FillValue.EMPTY;
            default -> throw new IllegalArgumentException();
        };
    }

    private static FillValue traverseR(FillValue old) {
        return switch (old) {
            case EMPTY, FILLED -> FillValue.CROSSED;
            case CROSSED -> FillValue.EMPTY;
            default -> throw new IllegalArgumentException();
        };
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
