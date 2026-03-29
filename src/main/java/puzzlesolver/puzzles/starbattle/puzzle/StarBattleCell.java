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
        if (allowsStar) {
            if (allowsCross)
                return FillValue.nonEmptyValues;
            else
                return List.of(FillValue.FILLED);
        }
        else {
            if (allowsCross)
                return List.of(FillValue.CROSSED);
            else
                return List.of();
        }
    }
}
