package puzzlesolver.puzzles.kakurasu.puzzle;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import lombok.Value;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import puzzlesolver.generics.puzzle.FillValue;
import puzzlesolver.generics.puzzle.Group;
import puzzlesolver.util.ListBindings;
import puzzlesolver.util.LogicBindings;

import java.util.List;

@Slf4j
@Value
public class KakurasuGroup implements Group<FillValue> {
    int sum;
    ObservableList<KakurasuCell> cells;
    @Accessors(fluent = true)
    ReadOnlyIntegerProperty currentSumProperty;
    @Accessors(fluent = true)
    ReadOnlyIntegerProperty maxSumProperty;
    @Accessors(fluent = true)
    ReadOnlyBooleanProperty validProperty;
    @Accessors(fluent = true)
    ReadOnlyBooleanProperty finishedProperty;

    public KakurasuGroup(int sum, ObservableList<KakurasuCell> cells) {
        this.sum = sum;
        this.cells = cells;
        ReadOnlyIntegerWrapper currentSum = new ReadOnlyIntegerWrapper();
        currentSumProperty = currentSum.getReadOnlyProperty();
        ReadOnlyIntegerWrapper maxSum = new ReadOnlyIntegerWrapper();
        maxSumProperty = maxSum.getReadOnlyProperty();
        this.cells.addListener((ListChangeListener<? super KakurasuCell>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    int i = c.getFrom() + 1;
                    for (var x : c.getAddedSubList()) {
                        final int weight = i++;
                        x.valueProperty().addListener((ob, o, n) -> {
                            if (n == FillValue.FILLED) {
                                currentSum.set(currentSum.get() + weight);
                            }
                            else if (n == FillValue.CROSSED) {
                                maxSum.set(maxSum.get() - weight);
                            }
                            if (o == FillValue.FILLED) {
                                currentSum.set(currentSum.get() - weight);
                            }
                            else if (o == FillValue.CROSSED) {
                                maxSum.set(maxSum.get() + weight);
                            }
                        });
                        maxSum.set(maxSum.get() + weight);
                    }
                }
            }
        });
        ReadOnlyBooleanWrapper validWrapper = new ReadOnlyBooleanWrapper();
        validWrapper.bind(Bindings.and(
                currentSumProperty.lessThanOrEqualTo(sum),
                maxSumProperty.greaterThanOrEqualTo(sum)
        ));
        this.validProperty = validWrapper.getReadOnlyProperty();
        ReadOnlyBooleanWrapper finishedWrapper = new ReadOnlyBooleanWrapper();
        finishedWrapper.bind(
                LogicBindings.forall(ListBindings.mapEach(cells, c -> Bindings.not(c.emptyProperty())))
        );
        this.finishedProperty = finishedWrapper.getReadOnlyProperty();
    }

    @Override
    public boolean validate() {
        return validProperty.get();
    }

    @Override
    public List<FillValue> getAllowedValues() {
        return List.of(FillValue.FILLED, FillValue.CROSSED);
    }
}
