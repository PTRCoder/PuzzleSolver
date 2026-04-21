package puzzlesolver.puzzles.starbattle.puzzle;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import puzzlesolver.exceptions.InvalidPuzzleSyntaxException;
import puzzlesolver.generics.puzzle.*;

import java.util.*;

@Value
public class StarBattleGrid implements Grid<FillValue> {
    List<StarBattleGroup> rows;
    List<StarBattleGroup> cols;
    List<AbstractStarBattleGroup> groups;
    Map<Position, StarBattleCell> positionMap;
    @Getter(AccessLevel.NONE)
    int size;
    int count;
    @Getter(AccessLevel.NONE)
    Map<Integer, List<StarBattleCell>> groupMap;

    public StarBattleGrid(Scanner sc) throws InvalidPuzzleSyntaxException {
        try (sc) {
            this.size = sc.nextInt();
            this.count = sc.nextInt();

            this.rows = new ArrayList<>();
            this.cols = new ArrayList<>();
            this.groups = new ArrayList<>();
            this.groupMap = new HashMap<>();
            this.positionMap = new HashMap<>();

            for (int i = 0; i < size; i++) {
                List<StarBattleCell> cRow = new LinkedList<>();
                for (int j = 0; j < size; j++) {
                    int x = sc.nextInt();
                    groupMap.computeIfAbsent(x, (k) -> new LinkedList<>());
                    Position pos = new Position(j, i);
                    StarBattleCell c = new StarBattleCell(this, pos);
                    positionMap.put(pos, c);
                    groupMap.get(x).add(c);
                    cRow.add(c);
                }
                StarBattleGroup row = new StarBattleGroup(cRow, count);
                rows.add(row);
                groups.add(row);
            }

            for (int i = 0; i < size; i++) {
                List<StarBattleCell> cCol = new LinkedList<>();
                for (int j = 0; j < size; j++) {
                    StarBattleCell c = positionMap.get(new Position(i, j));
                    cCol.add(c);
                }
                StarBattleGroup col = new StarBattleGroup(cCol, count);
                cols.add(col);
                groups.add(col);
            }

            for (List<StarBattleCell> g : groupMap.values()) {
                StarBattleGroup group = new StarBattleGroup(g, count);
                groups.add(group);
            }

            for (int i = 0; i < size - 1; i++) {
                for (int j = 0; j < size - 1; j++) {
                    List<StarBattleCell> square = List.of(
                            positionMap.get(new Position(j, i)),
                            positionMap.get(new Position(j, i + 1)),
                            positionMap.get(new Position(j + 1, i)),
                            positionMap.get(new Position(j + 1, i + 1))
                    );
                    StarBattleSquare s = new StarBattleSquare(square);
                    groups.add(s);
                }
            }

            for (Group<FillValue> g : groups) {
                for (Cell<FillValue> c : g) {
                    c.addGroup(g);
                }
            }

        }
        catch (InputMismatchException e) {
            throw new InvalidPuzzleSyntaxException(StarBattlePuzzle.class, "This syntax is not correct", e);
        }
        catch (NoSuchElementException e) {
            throw InvalidPuzzleSyntaxException.notEnough(StarBattlePuzzle.class, e);
        }
        catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidPuzzleSyntaxException(StarBattlePuzzle.class, "OOPSIES", e);
        }
    }

    @Override
    public Collection<StarBattleCell> getCells() {
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
    public String encode() {
        throw new UnsupportedOperationException();
    }
}
