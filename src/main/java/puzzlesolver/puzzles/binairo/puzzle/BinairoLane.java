package puzzlesolver.puzzles.binairo.puzzle;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import puzzlesolver.generics.puzzle.BinaryValue;
import puzzlesolver.generics.puzzle.Cell;
import puzzlesolver.generics.puzzle.Group;

import java.util.Collection;
import java.util.List;

@Value
public class BinairoLane implements Group<BinaryValue> {
    ListProperty<BinairoCell> cells = new SimpleListProperty<>();
    @Getter(AccessLevel.NONE)
    IntegerProperty bSum = new SimpleIntegerProperty();
    @Getter(AccessLevel.NONE)
    IntegerProperty wSum = new SimpleIntegerProperty();
    @Getter(AccessLevel.NONE)
    ChangeListener<BinaryValue> listener = (ob, o, n) -> {
        if (o == BinaryValue.EMPTY) {
            IntegerProperty p = n == BinaryValue.BLACK ? bSum : wSum;
            p.set(p.get() + 1);
        }
        else if (n == BinaryValue.EMPTY) {
            IntegerProperty p = o == BinaryValue.BLACK ? bSum : wSum;
            p.set(p.get() - 1);
        }
    };
    @Getter(AccessLevel.NONE)
    BooleanBinding validity = Bindings.and(Bindings.lessThanOrEqual(bSum, cells.sizeProperty().divide(2)),
            Bindings.lessThanOrEqual(wSum, cells.sizeProperty().divide(2)));
    @Getter(AccessLevel.NONE)
    BooleanBinding blackLeft = Bindings.lessThan(bSum, cells.sizeProperty().divide(2));
    @Getter(AccessLevel.NONE)
    BooleanBinding whiteLeft = Bindings.lessThan(wSum, cells.sizeProperty().divide(2));

    public BinairoLane(List<BinairoCell> cells) {
        this.cells.setValue(FXCollections.observableList(cells));
        int bs = 0;
        int ws = 0;
        List<ObjectProperty<BinaryValue>> values = cells.stream().map(Cell::valueProperty).toList();
        for (ObjectProperty<BinaryValue> value : values) {
            value.addListener((ob, o, n) -> {
                if (o == BinaryValue.EMPTY) {
                    IntegerProperty p = n == BinaryValue.BLACK ? bSum : wSum;
                    p.set(p.get() + 1);
                }
                else if (n == BinaryValue.EMPTY) {
                    IntegerProperty p = o == BinaryValue.BLACK ? bSum : wSum;
                    p.set(p.get() - 1);
                }
            });
            switch (value.get()) {
                case BLACK -> bs++;
                case WHITE -> ws++;
                default -> {/* do nothing */}
            }
        }
        this.bSum.set(bs);
        this.wSum.set(ws);
    }

    @Override
    public boolean validate() {
        return validity.get();
    }

    @Override
    public Collection<BinaryValue> getAllowedValues() {
        return List.of(BinaryValue.BLACK, BinaryValue.WHITE);
    }

    public IntegerProperty bSumProperty() {
        return bSum;
    }

    public IntegerProperty wSumProperty() {
        return wSum;
    }

    public ObservableBooleanValue whiteLeftProperty() {
        return whiteLeft;
    }

    public ObservableBooleanValue blackLeftProperty() {
        return blackLeft;
    }
}
