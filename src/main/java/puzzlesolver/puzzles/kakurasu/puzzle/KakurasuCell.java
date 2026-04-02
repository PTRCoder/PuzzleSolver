package puzzlesolver.puzzles.kakurasu.puzzle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import puzzlesolver.generics.puzzle.*;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class KakurasuCell implements Cell<FillValue> {
    @Accessors(fluent = true)
    private final BooleanProperty lockedProperty = new SimpleBooleanProperty();
    @Accessors(fluent = true)
    private final LockableProperty<FillValue> valueProperty =
            new LockableProperty<>(FillValue.EMPTY, lockedProperty);
    @Accessors(fluent = true)
    private final ListProperty<Group<FillValue>> groupsProperty =
            new SimpleListProperty<>(FXCollections.observableList(new LinkedList<>()));
    private final KakurasuGrid grid;
    private final Position position;

    @Override
    public List<FillValue> getAllowedValues() {
        return List.of(FillValue.FILLED, FillValue.CROSSED);
    }
}
