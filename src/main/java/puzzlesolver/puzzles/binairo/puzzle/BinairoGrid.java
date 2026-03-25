package puzzlesolver.puzzles.binairo.puzzle;

import javafx.scene.control.Label;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import puzzlesolver.generics.puzzle.PuzzlePrinter;
import puzzlesolver.exceptions.InvalidPuzzleSyntaxException;
import puzzlesolver.generics.puzzle.BinaryValue;
import puzzlesolver.generics.puzzle.Grid;
import puzzlesolver.generics.puzzle.Position;

import java.util.*;

@Value
public class BinairoGrid implements Grid<BinaryValue> {
    private static final PuzzlePrinter<BinaryValue> printer = new PuzzlePrinter<>();

    static {
        printer.setConnect(false);
        printer.setUseSpaces(false);
        printer.setToString(x -> Character.toString(x.asChar()));
        printer.setVBlockSize(0);
        printer.setHBlockSize(0);
        printer.setUseBorder(true);
    }

    @Getter(AccessLevel.NONE)
    int size;
    List<List<BinairoCell>> cells;
    List<BinairoLane> groups;
    List<BinairoLane> rows;
    List<BinairoLane> cols;

    public BinairoGrid(Scanner sc) throws InvalidPuzzleSyntaxException {
        try (sc) {
            this.size = sc.nextInt();
            if ((size & 1) == 1)
                throw new InvalidPuzzleSyntaxException(BinairoPuzzle.class, "Size must be an even number");

            this.cells = new ArrayList<>();
            this.groups = new ArrayList<>();
            this.rows = new ArrayList<>();
            this.cols = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                List<BinairoCell> row = new ArrayList<>();
                cells.add(row);
                for (int j = 0; j < size; j++) {
                    String c = sc.next();
                    if (c.length() > 1)
                        throw new InvalidPuzzleSyntaxException(BinairoPuzzle.class,
                                "Symbols must be properly separated by spaces");
                    BinairoCell cell = new BinairoCell(this, new Position(j, i));
                    cell.setValue(BinaryValue.fromChar(c.charAt(0)));
                    row.add(cell);
                }
                BinairoLane g = new BinairoLane(Collections.unmodifiableList(row));
                groups.add(g);
                rows.add(g);
            }

            for (int i = 0; i < size; i++) {
                List<BinairoCell> col = new ArrayList<>();
                for (int j = 0; j < size; j++) {
                    col.add(cells.get(j).get(i));
                }
                BinairoLane g = new BinairoLane(Collections.unmodifiableList(col));
                cols.add(g);
                groups.add(g);
            }

            for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++) {
                    BinairoCell c = cells.get(i).get(j);
                    c.addGroup(rows.get(i));
                    c.addGroup(cols.get(j));
                }

        }
        catch (NoSuchElementException e) {
            throw new InvalidPuzzleSyntaxException(BinairoPuzzle.class, "Not enough values", e);
        }
        catch (IllegalArgumentException e) {
            throw new InvalidPuzzleSyntaxException(BinairoPuzzle.class, "Values must be either ' ', 'W', or 'B'", e);
        }
    }

    @Override
    public int getWidth() {
        return size;
    }

    @Override
    public int getHeight() {
        return size;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void print(Label parent) {
        printer.print(this, parent);
    }
}
