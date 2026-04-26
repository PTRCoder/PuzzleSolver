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

import java.util.List;
import java.util.stream.IntStream;

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
//                            log.debug("goal: {}, current: {}, max: {}, computed: ({}, {})",
//                                    sum, currentSum.get(), maxSum.get(),
//                                    computeSum(), computeMaxSum());
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
    }

    @Override
    public boolean validate() {
        return validProperty.get();
    }

    private int computeSum() {
        return IntStream.range(0, cells.size())
                .map(k -> cells.get(k).getValue() == FillValue.FILLED ? k + 1 : 0)
                .sum();
    }

    private int computeMaxSum() {
        return IntStream.range(0, cells.size())
                .map(k -> cells.get(k).getValue() != FillValue.CROSSED ? k + 1 : 0)
                .sum();
    }

    @Override
    public List<FillValue> getAllowedValues() {
        return List.of(FillValue.FILLED, FillValue.CROSSED);
    }
}
