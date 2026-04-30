package puzzlesolver.puzzles.kakurasu.puzzle;

import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import lombok.Value;
import lombok.experimental.Accessors;
import puzzlesolver.generics.puzzle.*;
import puzzlesolver.util.ListBindings;
import puzzlesolver.util.LogicBindings;

import java.util.LinkedList;
import java.util.List;

@Value
public class KakurasuCell implements Cell<FillValue> {
    @Accessors(fluent = true)
    BooleanProperty lockedProperty = new SimpleBooleanProperty();
    @Accessors(fluent = true)
    LockableProperty<FillValue> valueProperty =
            new LockableProperty<>(FillValue.EMPTY, lockedProperty);
    @Accessors(fluent = true)
    ListProperty<Group<FillValue>> groupsProperty =
            new SimpleListProperty<>(FXCollections.observableList(new LinkedList<>()));
    @Accessors(fluent = true)
    ObservableBooleanValue validProperty =
            LogicBindings.forall(ListBindings.mapEach(groupsProperty, g -> ((KakurasuGroup) g).validProperty()));
    @Accessors(fluent = true)
    ObservableBooleanValue emptyProperty = BooleanExpression.booleanExpression(valueProperty.map(FillValue::isEmpty));
    KakurasuGrid grid;
    Position position;

    @Override
    public List<FillValue> getAllowedValues() {
        return List.of(FillValue.FILLED, FillValue.CROSSED);
    }

    @Override
    public boolean isValid() {
        return validProperty.get();
    }
}
