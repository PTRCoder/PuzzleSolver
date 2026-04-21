package puzzlesolver.puzzles.kakurasu.puzzle;

import lombok.Value;
import puzzlesolver.exceptions.InvalidPuzzleSyntaxException;
import puzzlesolver.generics.puzzle.FillValue;
import puzzlesolver.generics.puzzle.Grid;
import puzzlesolver.generics.puzzle.Position;

import java.util.*;

@Value
public class KakurasuGrid implements Grid<FillValue> {

    int width;
    int height;
    List<KakurasuGroup> groups;
    List<KakurasuGroup> rows;
    List<KakurasuGroup> cols;
    Map<Position, KakurasuCell> positionMap;

    public KakurasuGrid(Scanner sc) throws InvalidPuzzleSyntaxException {
        try (sc) {
            // get height and width
            this.height = sc.nextInt();
            this.width = sc.nextInt();
            // init collections
            this.cols = new ArrayList<>();
            this.rows = new ArrayList<>();
            this.groups = new ArrayList<>();
            this.positionMap = new HashMap<>();

            // fill cells and rows
            for (int i = 0; i < height; i++) {
                int sum = sc.nextInt();
                List<KakurasuCell> row = new ArrayList<>();
                KakurasuGroup g = new KakurasuGroup(sum, row);
                rows.add(g);
                for (int j = 0; j < width; j++) {
                    Position pos = new Position(j, i);
                    KakurasuCell c = new KakurasuCell(this, pos);
                    positionMap.put(pos, c);
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
                    KakurasuCell c = positionMap.get(new Position(i, j));
                    col.add(c);
                    c.addGroup(g);
                }
            }
            // fill groups
            groups.addAll(rows);
            groups.addAll(cols);
            if (sc.hasNext())
                throw InvalidPuzzleSyntaxException.tooMany(KakurasuPuzzle.class);
        }
        catch (NoSuchElementException e) {
            throw InvalidPuzzleSyntaxException.notEnough(KakurasuPuzzle.class, e);
        }
    }

    @Override
    public String encode() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<KakurasuCell> getCells() {
        return Collections.unmodifiableCollection(positionMap.values());
    }

}
