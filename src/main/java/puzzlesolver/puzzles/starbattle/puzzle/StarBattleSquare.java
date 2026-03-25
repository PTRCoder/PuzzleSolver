package puzzlesolver.puzzles.starbattle.puzzle;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import puzzlesolver.generics.puzzle.FillValue;

import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode(callSuper = true)
public final class StarBattleSquare extends AbstractStarBattleGroup {
    IntegerProperty starCount = new SimpleIntegerProperty(0);
    BooleanBinding validity = Bindings.lessThanOrEqual(1, starCount);
    @Getter
    @Accessors(fluent = true)
    BooleanExpression allowsStarProperty = starCount.lessThan(1);
    ChangeListener<FillValue> listener = (ov, o, n) -> {
        if (o == FillValue.FILLED)
            starCount.set(starCount.get() - 1);
        if (n == FillValue.FILLED)
            starCount.set(starCount.get() + 1);
    };

    public StarBattleSquare(List<StarBattleCell> cells) {
        super(cells);
        if (cells.size() != 4)
            throw new IllegalArgumentException("A square must contain exactly 4 cells");
        for (StarBattleCell c : cells) {
            c.valueProperty().addListener(listener);
        }
    }

    @Override
    public IntegerProperty starCountProperty() {
        return starCount;
    }

    @Override
    public boolean validate() {
        return validity.get();
    }

    @Override
    public List<FillValue> getAllowedValues() {
        return List.of();
    }
}
