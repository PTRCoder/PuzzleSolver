package puzzlesolver.puzzles.sudoku.puzzle;

import javafx.scene.control.Label;
import puzzlesolver.generics.puzzle.PuzzlePrinter;
import puzzlesolver.exceptions.InvalidPuzzleSyntaxException;
import puzzlesolver.generics.puzzle.Grid;
import lombok.*;
import puzzlesolver.generics.puzzle.Position;

import java.util.*;

@Value
public class SudokuGrid implements Grid<Integer> {
    private static final PuzzlePrinter<Integer> printer = new PuzzlePrinter<>();

    static {
        printer.setToString(x -> {
            if (x == SudokuCell.EMPTY)
                return " ";
            if (x > 9) {
                return Character.toString((char) ((x - 9) + 'A'));
            }
            if (0 <= x)
                return Integer.toString(x);
            throw new IllegalArgumentException();
        });
        printer.setUseBorder(true);
        printer.setUseSpaces(true);
        printer.setConnect(false);
    }

    @Getter(AccessLevel.NONE)
    int size;
    List<List<SudokuCell>> cells;
    List<SudokuGroup> groups;
    List<SudokuGroup> rows;
    List<SudokuGroup> cols;
    List<SudokuGroup> blocks;

    public SudokuGrid(Scanner sc) throws InvalidPuzzleSyntaxException {
        try (sc) {
            // get size and check validity
            this.size = sc.nextInt();
            int sqrt = (int) Math.sqrt(size);
            if (sqrt * sqrt != size)
                throw new InvalidPuzzleSyntaxException(SudokuPuzzle.class, "Size must be a square number");

            // init collections
            this.cells = new ArrayList<>();
            this.cols = new ArrayList<>();
            this.rows = new ArrayList<>();
            this.blocks = new ArrayList<>();
            this.groups = new ArrayList<>();

            // fill cells and rows
            for (int i = 0; i < size; i++) {
                List<SudokuCell> row = new ArrayList<>();
                cells.add(row);
                SudokuGroup g = new SudokuGroup(row);
                rows.add(g);
                for (int j = 0; j < size; j++) {
                    int val = sc.nextInt();
                    SudokuCell c = new SudokuCell(this, size, new Position(j, i));
                    c.setValue(val);
                    if (val != SudokuCell.EMPTY) {
                        c.lock();
                    }
                    row.add(c);
                    c.addGroup(g);
                }
            }
            // fill cols
            for (int i = 0; i < size; i++) {
                List<SudokuCell> col = new ArrayList<>();
                SudokuGroup g = new SudokuGroup(col);
                cols.add(g);
                for (int j = 0; j < size; j++) {
                    SudokuCell c = cells.get(j).get(i);
                    col.add(c);
                    c.addGroup(g);
                }
            }
            // fill blocks
            for (int i = 0; i < size; i++) {
                List<SudokuCell> block = new ArrayList<>();
                SudokuGroup g = new SudokuGroup(block);
                blocks.add(g);
                int x0 = (i % sqrt) * sqrt;
                int y0 = (i / sqrt) * sqrt;
                for (int j = 0; j < size; j++) {
                    int x = x0 + j % sqrt;
                    int y = y0 + j / sqrt;
                    SudokuCell c = cells.get(y).get(x);
                    block.add(c);
                    c.addGroup(g);
                }
            }
            // fill groups
            groups.addAll(rows);
            groups.addAll(cols);
            groups.addAll(blocks);

            if (sc.hasNext())
                throw new InvalidPuzzleSyntaxException(SudokuPuzzle.class, "Too many values");
        }
        catch (NoSuchElementException e) {
            throw new InvalidPuzzleSyntaxException(SudokuPuzzle.class, "Not enough values");
        }
    }

    @Override
    public int getHeight() {
        return size;
    }

    @Override
    public int getWidth() {
        return size;
    }

    @Override
    public void print(Label parent) {
        int sqrt = (int) Math.sqrt(size);
        printer.setVBlockSize(sqrt);
        printer.setHBlockSize(sqrt);
        printer.print(this, parent);
    }

}
