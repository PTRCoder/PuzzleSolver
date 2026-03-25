package puzzlesolver.puzzles.kakurasu.puzzle;

import javafx.scene.control.Label;
import puzzlesolver.generics.puzzle.FillValue;
import puzzlesolver.generics.puzzle.PuzzlePrinter;
import puzzlesolver.exceptions.InvalidPuzzleSyntaxException;
import puzzlesolver.generics.puzzle.Grid;
import lombok.*;
import puzzlesolver.generics.puzzle.Position;

import java.util.*;

@Value
public class KakurasuGrid implements Grid<FillValue> {
    private static final PuzzlePrinter<FillValue> printer = new PuzzlePrinter<>();

    static {
        printer.setVBlockSize(0);
        printer.setHBlockSize(0);
        printer.setConnect(false);
        printer.setUseBorder(true);
        printer.setUseSpaces(false);
        printer.setToString(x -> Character.toString(x.asChar()));
    }

    int width;
    int height;
    List<List<KakurasuCell>> cells;
    List<KakurasuGroup> groups;
    List<KakurasuGroup> rows;
    List<KakurasuGroup> cols;

    public KakurasuGrid(Scanner sc) throws InvalidPuzzleSyntaxException {
        try (sc) {
            // get height and width
            this.height = sc.nextInt();
            this.width = sc.nextInt();
            // init collections
            this.cells = new ArrayList<>();
            this.cols = new ArrayList<>();
            this.rows = new ArrayList<>();
            this.groups = new ArrayList<>();

            // fill cells and rows
            for (int i = 0; i < height; i++) {
                int sum = sc.nextInt();
                List<KakurasuCell> row = new ArrayList<>();
                cells.add(row);
                KakurasuGroup g = new KakurasuGroup(sum, row);
                rows.add(g);
                for (int j = 0; j < width; j++) {
                    KakurasuCell c = new KakurasuCell(this, new Position(j, i));
                    row.add(c);
                    c.addGroup(g);
                }
            }
            // fill cols
            for (int i = 0; i < height; i++) {
                int sum = sc.nextInt();
                List<KakurasuCell> col = new ArrayList<>();
                KakurasuGroup g = new KakurasuGroup(sum, col);
                cols.add(g);
                for (int j = 0; j < width; j++) {
                    KakurasuCell c = cells.get(j).get(i);
                    col.add(c);
                    c.addGroup(g);
                }
            }
            // fill groups
            groups.addAll(rows);
            groups.addAll(cols);
            if (sc.hasNext())
                throw new InvalidPuzzleSyntaxException(KakurasuPuzzle.class, "Too many values");
        }
        catch (NoSuchElementException e) {
            throw new InvalidPuzzleSyntaxException(KakurasuPuzzle.class, "Not enough values");
        }
    }

    @Override
    public void print(Label parent) {
        printer.print(this, parent);
    }

}
