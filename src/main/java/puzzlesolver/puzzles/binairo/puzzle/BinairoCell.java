package puzzlesolver.puzzles.binairo.puzzle;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import puzzlesolver.generics.puzzle.AbstractCell;
import puzzlesolver.generics.puzzle.BinaryValue;
import puzzlesolver.generics.puzzle.Group;
import puzzlesolver.generics.puzzle.Position;

import java.util.List;

public class BinairoCell extends AbstractCell<BinaryValue> {
    private final ListProperty<BinaryValue> allowed = new SimpleListProperty<>();

    public BinairoCell(BinairoGrid grid, Position pos) {
        super(grid, pos);
    }

    @Override
    protected BinaryValue getEmpty() {
        return BinaryValue.EMPTY;
    }

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
