package puzzlesolver.puzzles.sudoku.puzzle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import puzzlesolver.exceptions.InvalidPuzzleSyntaxException;
import puzzlesolver.generics.puzzle.Cell;
import puzzlesolver.generics.puzzle.Grid;
import puzzlesolver.generics.puzzle.HexValue;
import puzzlesolver.generics.puzzle.Position;

import java.util.*;

@Value
public class SudokuGrid implements Grid<HexValue> {

    @Getter(AccessLevel.NONE)
    int size;
    List<SudokuGroup> groups;
    List<SudokuGroup> rows;
    List<SudokuGroup> cols;
    List<SudokuGroup> blocks;
    Map<Position, SudokuCell> positionMap;

    public SudokuGrid(Scanner sc) throws InvalidPuzzleSyntaxException {
        try (sc) {
            // get size and check validity
            this.size = sc.nextInt();
            if (size < 4)
                throw new InvalidPuzzleSyntaxException(SudokuPuzzle.class, "Size must be at least 4");
            int sqrt = (int) Math.sqrt(size);
            if (sqrt * sqrt != size)
                throw new InvalidPuzzleSyntaxException(SudokuPuzzle.class, "Size must be a square number");
            if (sqrt > 4)
                throw new InvalidPuzzleSyntaxException
                        (SudokuPuzzle.class, "Sudokus larger than 16×16 are not supported");

            // init collections
            this.cols = new ArrayList<>();
            this.rows = new ArrayList<>();
            this.blocks = new ArrayList<>();
            this.groups = new ArrayList<>();
            this.positionMap = new HashMap<>();

            // fill cells and rows
            for (int i = 0; i < size; i++) {
                ObservableList<SudokuCell> row = FXCollections.observableArrayList();
                SudokuGroup g = new SudokuGroup(row);
                rows.add(g);
                for (int j = 0; j < size; j++) {
                    HexValue val = HexValue.valueOf(sc.nextInt());
                    Position pos = new Position(j, i);
                    SudokuCell c = new SudokuCell(this, size, pos);
                    c.setValue(val);
                    if (!val.isEmpty()) {
                        c.lock();
                    }
                    row.add(c);
                    c.addGroup(g);
                    positionMap.put(pos, c);
                }
            }
            // fill cols
            for (int i = 0; i < size; i++) {
                ObservableList<SudokuCell> col = FXCollections.observableArrayList();
                SudokuGroup g = new SudokuGroup(col);
                cols.add(g);
                for (int j = 0; j < size; j++) {
                    SudokuCell c = positionMap.get(new Position(i, j));
                    col.add(c);
                    c.addGroup(g);
                }
            }
            // fill blocks
            for (int i = 0; i < size; i++) {
                ObservableList<SudokuCell> block = FXCollections.observableArrayList();
                SudokuGroup g = new SudokuGroup(block);
                blocks.add(g);
                int x0 = (i % sqrt) * sqrt;
                int y0 = (i / sqrt) * sqrt;
                for (int j = 0; j < size; j++) {
                    int x = x0 + j % sqrt;
                    int y = y0 + j / sqrt;
                    SudokuCell c = positionMap.get(new Position(x, y));
                    block.add(c);
                    c.addGroup(g);
                }
            }
            // fill groups
            groups.addAll(rows);
            groups.addAll(cols);
            groups.addAll(blocks);

            if (sc.hasNext())
                throw InvalidPuzzleSyntaxException.tooMany(SudokuPuzzle.class);
        }
        catch (InputMismatchException e) {
            throw new InvalidPuzzleSyntaxException(SudokuPuzzle.class, "", e);
        }
        catch (NoSuchElementException e) {
            throw InvalidPuzzleSyntaxException.notEnough(SudokuPuzzle.class, e);
        }
    }

    @Override
    public Collection<SudokuCell> getCells() {
        return Collections.unmodifiableCollection(positionMap.values());
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
    public String encode() {
        StringBuilder sb = new StringBuilder("sudoku").append(System.lineSeparator());
        sb.append("%d%n".formatted(size));
        for (SudokuGroup row : rows) {
            char[] cs = new char[2 * size - 1];
            int i = 1;
            Iterator<? extends Cell<HexValue>> it = row.iterator();
            cs[0] = HexValue.encode(it.next().getValue());
            while (it.hasNext()) {
                cs[i++] = ' ';
                cs[i++] = HexValue.encode(it.next().getValue());
            }
            sb.append(String.copyValueOf(cs));
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
