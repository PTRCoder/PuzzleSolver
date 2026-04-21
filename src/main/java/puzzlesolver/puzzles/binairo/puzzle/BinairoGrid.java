package puzzlesolver.puzzles.binairo.puzzle;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import puzzlesolver.exceptions.InvalidPuzzleSyntaxException;
import puzzlesolver.generics.puzzle.BinaryValue;
import puzzlesolver.generics.puzzle.Grid;
import puzzlesolver.generics.puzzle.Position;

import java.util.*;

@Value
public class BinairoGrid implements Grid<BinaryValue> {

    @Getter(AccessLevel.NONE)
    int size;
    Map<Position, BinairoCell> positionMap;
    List<BinairoLane> groups;
    List<BinairoLane> rows;
    List<BinairoLane> cols;

    public BinairoGrid(Scanner sc) throws InvalidPuzzleSyntaxException {
        try (sc) {
            this.size = sc.nextInt();
            if ((size & 1) == 1)
                throw new InvalidPuzzleSyntaxException(BinairoPuzzle.class, "Size must be an even number");

            this.groups = new ArrayList<>();
            this.rows = new ArrayList<>();
            this.cols = new ArrayList<>();
            this.positionMap = new HashMap<>();

            for (int i = 0; i < size; i++) {
                List<BinairoCell> row = new ArrayList<>();
                for (int j = 0; j < size; j++) {
                    String c = sc.next();
                    if (c.length() > 1)
                        throw new InvalidPuzzleSyntaxException(BinairoPuzzle.class,
                                "Symbols must be properly separated by spaces");
                    Position pos = new Position(j, i);
                    BinairoCell cell = new BinairoCell(this, pos);
                    cell.setValue(BinaryValue.decode(c.charAt(0)));
                    row.add(cell);
                    positionMap.put(pos, cell);
                }
                BinairoLane g = new BinairoLane(Collections.unmodifiableList(row));
                groups.add(g);
                rows.add(g);
            }

            for (int i = 0; i < size; i++) {
                List<BinairoCell> col = new ArrayList<>();
                for (int j = 0; j < size; j++) {
                    col.add(positionMap.get(new Position(i, j)));
                }
                BinairoLane g = new BinairoLane(Collections.unmodifiableList(col));
                cols.add(g);
                groups.add(g);
            }

            for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++) {
                    BinairoCell c = positionMap.get(new Position(j, i));
                    c.addGroup(rows.get(i));
                    c.addGroup(cols.get(j));
                }

        }
        catch (NoSuchElementException e) {
            throw InvalidPuzzleSyntaxException.notEnough(BinairoPuzzle.class, e);
        }
        catch (IllegalArgumentException e) {
            throw new InvalidPuzzleSyntaxException(BinairoPuzzle.class, "Values must be either ' ', 'W', or 'B'", e);
        }
    }

    @Override
    public Collection<BinairoCell> getCells() {
        return Collections.unmodifiableCollection(positionMap.values());
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
    public String encode() {
        throw new UnsupportedOperationException();
    }
}
