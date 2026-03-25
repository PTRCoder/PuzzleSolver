package puzzlesolver.puzzles.starbattle.puzzle;

import puzzlesolver.generics.puzzle.AbstractCell;
import puzzlesolver.generics.puzzle.FillValue;
import puzzlesolver.generics.puzzle.Grid;
import puzzlesolver.generics.puzzle.Position;

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
        return FillValue.nonEmptyValues;
    }
}
