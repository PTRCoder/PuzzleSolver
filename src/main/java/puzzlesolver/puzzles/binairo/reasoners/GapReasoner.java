package puzzlesolver.puzzles.binairo.reasoners;

import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.commands.ValueCommand;
import puzzlesolver.generics.puzzle.BinaryValue;
import puzzlesolver.generics.puzzle.Cell;
import puzzlesolver.generics.puzzle.Position;
import puzzlesolver.generics.reasoners.EmptyCellReasoner;
import puzzlesolver.puzzles.binairo.puzzle.BinairoCell;
import puzzlesolver.puzzles.binairo.puzzle.BinairoGrid;

public class GapReasoner extends EmptyCellReasoner<BinaryValue> {
    @Override
    public boolean applyToCell(Cell<BinaryValue> cell, CompoundCommand comms) {
        BinairoGrid grid = (BinairoGrid) cell.getGrid();
        Position pos = cell.getPosition();
        int x = pos.x();
        int y = pos.y();
        BinairoCell left = (BinairoCell) grid.getCell(new Position(x - 1, y));
        BinairoCell right = (BinairoCell) grid.getCell(new Position(x + 1, y));
        BinairoCell up = (BinairoCell) grid.getCell(new Position(x, y - 1));
        BinairoCell down = (BinairoCell) grid.getCell(new Position(x, y + 1));

        if (left != null && right != null) {
            BinaryValue l = left.getValue();
            BinaryValue r = right.getValue();
            if (l == r && l != BinaryValue.EMPTY) {
                comms.add(new ValueCommand<>(cell, l.getComplement()));
                return true;
            }
        }
        if (up != null && down != null) {
            BinaryValue u = up.getValue();
            BinaryValue d = down.getValue();
            if (u == d && u != BinaryValue.EMPTY) {
                comms.add(new ValueCommand<>(cell, u.getComplement()));
                return true;
            }
        }
        return false;
    }
}
