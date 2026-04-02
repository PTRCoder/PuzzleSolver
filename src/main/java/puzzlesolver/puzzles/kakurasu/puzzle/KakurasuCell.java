package puzzlesolver.puzzles.kakurasu.puzzle;

import puzzlesolver.generics.puzzle.AbstractCell;
import puzzlesolver.generics.puzzle.FillValue;
import puzzlesolver.generics.puzzle.Position;

import java.util.List;

public class KakurasuCell extends AbstractCell<FillValue> {

    public KakurasuCell(KakurasuGrid grid, Position pos) {
        super(grid, pos);
    }

    @Override
    public List<FillValue> getAllowedValues() {
        return List.of(FillValue.FILLED, FillValue.CROSSED);
    }
}
