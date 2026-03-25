package puzzlesolver.puzzles.starbattle.puzzle;

import puzzlesolver.generics.puzzle.*;

import java.util.List;

public class StarBattleCell extends AbstractCell<FillValue> {

    public StarBattleCell(Grid<FillValue> grid, Position pos) {
        super(grid, pos);
    }

    @Override
    protected FillValue getEmpty() {
        return FillValue.EMPTY;
    }

    @Override
    public List<FillValue> getAllowedValues() {
        for (Group<FillValue> group : getGroups()) {
            AbstractStarBattleGroup g = (AbstractStarBattleGroup) group;
            if (!g.allowsStarProperty().get())
                return List.of(FillValue.CROSSED);
        }
        return FillValue.nonEmptyValues;
    }
}
