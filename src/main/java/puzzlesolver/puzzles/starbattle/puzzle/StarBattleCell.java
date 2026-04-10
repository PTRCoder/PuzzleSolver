package puzzlesolver.puzzles.starbattle.puzzle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import puzzlesolver.generics.puzzle.*;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@EqualsAndHashCode
@Getter
@ToString
@RequiredArgsConstructor
public class StarBattleCell implements Cell<FillValue> {
    @Accessors(fluent = true)
    private final BooleanProperty lockedProperty = new SimpleBooleanProperty();
    @Accessors(fluent = true)
    private final LockableProperty<FillValue> valueProperty = new LockableProperty<>(lockedProperty);
    @Accessors(fluent = true)
    private final ListProperty<Group<FillValue>> groupsProperty =
            new SimpleListProperty<>(FXCollections.observableList(new LinkedList<>()));
    private final StarBattleGrid grid;
    private final Position position;

    @Override
    public Collection<FillValue> getAllowedValues() {
        boolean allowsStar = true;
        boolean allowsCross = true;
        for (Group<FillValue> group : getGroups()) {
            AbstractStarBattleGroup g = (AbstractStarBattleGroup) group;
            if (allowsStar && !g.allowsStarProperty().get()) {
                allowsStar = false;
            }
            if (allowsCross && !g.allowsCrossProperty().get()) {
                allowsCross = false;
            }
            if (!allowsCross && !allowsStar)
                break;
        }
        List<FillValue> list = new LinkedList<>();
        if (allowsStar)
            list.add(FillValue.FILLED);
        if (allowsCross)
            list.add(FillValue.CROSSED);
        return Collections.unmodifiableList(list);
    }
}
