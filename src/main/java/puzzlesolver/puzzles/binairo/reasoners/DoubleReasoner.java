package puzzlesolver.puzzles.binairo.reasoners;

import org.jspecify.annotations.Nullable;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.commands.ValueCommand;
import puzzlesolver.generics.puzzle.BinaryValue;
import puzzlesolver.generics.puzzle.Cell;
import puzzlesolver.generics.puzzle.Position;
import puzzlesolver.puzzles.binairo.puzzle.BinairoCell;
import puzzlesolver.puzzles.binairo.puzzle.BinairoGrid;
import puzzlesolver.generics.reasoners.EmptyCellReasoner;

import java.util.function.Function;

public class DoubleReasoner extends EmptyCellReasoner<BinaryValue> {
    @Override
    public boolean applyToCell(Cell<BinaryValue> cell, CompoundCommand comms) {
        BinairoGrid grid = (BinairoGrid) cell.getGrid();
        Position pos = cell.getPos();
        for (Dir d : Dir.values()) {
            Doubles x = getDoubles(grid, pos, d);
            if (x != null && x.v1 == x.v2 && x.v1 != BinaryValue.EMPTY) {
                comms.add(new ValueCommand<>(cell, x.v1.getComplement()));
                return true;
            }
        }
        return false;
    }

    private static @Nullable Doubles getDoubles(BinairoGrid grid, Position pos, Dir dir) {
        int x = pos.x();
        int y = pos.y();
        Function<Position, Position> retriever = getRetriever(dir);
        Position p1 = retriever.apply(pos);
        BinairoCell first = (BinairoCell) grid.getCell(p1);
        if (first == null)
            return null;
        BinaryValue f = first.getValue();
        Position p2 = retriever.apply(p1);
        BinairoCell second = (BinairoCell) grid.getCell(p2);
        if (second == null)
            return null;
        BinaryValue s = second.getValue();
        return new Doubles(f, s);
    }

    private static Function<Position, Position> getRetriever(Dir dir) {
        return switch (dir) {
            case LEFT -> p -> new Position(p.x() - 1, p.y());
            case RIGHT -> p -> new Position(p.x() + 1, p.y());
            case UP -> p -> new Position(p.x(), p.y() - 1);
            case DOWN -> p -> new Position(p.x(), p.y() + 1);
        };
    }

    private record Doubles(BinaryValue v1, BinaryValue v2) {
    }

    private enum Dir {
        LEFT, RIGHT, UP, DOWN
    }
}
