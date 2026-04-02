package puzzlesolver.puzzles.binairo.puzzle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import puzzlesolver.generics.puzzle.*;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class BinairoCell implements Cell<BinaryValue> {
    private final ListProperty<BinaryValue> allowed = new SimpleListProperty<>();
    @Accessors(fluent = true)
    private final BooleanProperty lockedProperty = new SimpleBooleanProperty();
    @Accessors(fluent = true)
    private final LockableProperty<BinaryValue> valueProperty =
            new LockableProperty<>(BinaryValue.EMPTY, lockedProperty);
    @Accessors(fluent = true)
    private final ListProperty<Group<BinaryValue>> groupsProperty =
            new SimpleListProperty<>(FXCollections.observableList(new LinkedList<>()));
    private final BinairoGrid grid;
    private final Position position;

    @Override
    public List<BinaryValue> getAllowedValues() {
        boolean white = true;
        boolean black = true;
        for (Group<BinaryValue> group : groupsProperty()) {
            BinairoLane g = (BinairoLane) group;
            white = white && g.whiteLeftProperty().get();
            black = black && g.blackLeftProperty().get();
            if (!black && !white)
                break;
        }
        return black && white ? List.of(BinaryValue.BLACK, BinaryValue.WHITE) :
                black ? List.of(BinaryValue.BLACK) :
                        white ? List.of(BinaryValue.WHITE) :
                                List.of();
    }
}
