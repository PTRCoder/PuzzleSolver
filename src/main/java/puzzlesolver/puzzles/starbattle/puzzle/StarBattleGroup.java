package puzzlesolver.puzzles.starbattle.puzzle;

import javafx.beans.binding.Bindings;
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
public final class StarBattleGroup extends AbstractStarBattleGroup {
    @Getter
    @Accessors(fluent = true)
    IntegerProperty starCountProperty = new SimpleIntegerProperty(0);
    @Getter
    IntegerProperty emptyCountProperty = new SimpleIntegerProperty(0);
    @Getter
    @Accessors(fluent = true)
    BooleanExpression allowsStarProperty;
    int maxCount;
    ChangeListener<FillValue> listener = (ov, o, n) -> {
        if (n == FillValue.FILLED)
            starCountProperty.set(starCountProperty.get() + 1);
        if (o == FillValue.FILLED)
            starCountProperty.set(starCountProperty.get() - 1);
        if (o == FillValue.EMPTY)
            emptyCountProperty.set(emptyCountProperty.get() - 1);
        if (n == FillValue.EMPTY)
            emptyCountProperty.set(emptyCountProperty.get() + 1);
    };
    BooleanExpression validity;

    public StarBattleGroup(List<StarBattleCell> cells, int maxCount) {
        super(cells);
        this.maxCount = maxCount;
        for (StarBattleCell c : cells) {
            c.valueProperty().addListener(listener);
        }
        this.allowsStarProperty = starCountProperty.lessThan(maxCount);
        this.validity = Bindings.add(emptyCountProperty, starCountProperty).greaterThanOrEqualTo(maxCount)
                .and(starCountProperty.lessThanOrEqualTo(maxCount));
    }

    @Override
    public boolean validate() {
        return validity.get();
    }

    @Override
    public List<FillValue> getAllowedValues() {
        return FillValue.nonEmptyValues;
    }

    public int getStarCount() {
        return starCountProperty.get();
    }
}
