package puzzlesolver.puzzles;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NonNls;
import puzzlesolver.exceptions.InvalidPuzzleNameException;
import puzzlesolver.exceptions.InvalidPuzzleSyntaxException;
import puzzlesolver.generics.puzzle.Puzzle;
import puzzlesolver.puzzles.binairo.puzzle.BinairoPuzzle;
import puzzlesolver.puzzles.kakurasu.puzzle.KakurasuPuzzle;
import puzzlesolver.puzzles.starbattle.puzzle.StarBattlePuzzle;
import puzzlesolver.puzzles.sudoku.puzzle.SudokuPuzzle;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

@NonNls
@UtilityClass
public final class PuzzleFactory {
    private final String SUDOKU = "sudoku";
    private final String KAKURASU = "kakurasu";
    private final String BINAIRO = "binairo";
    private final String STAR_BATTLE1 = "starbattle";
    private final String STAR_BATTLE2 = "star_battle";
    private final String STAR_ = "star";
    @NonNls
    private final String _BATTLE = "battle";

    public Puzzle<?> create(Scanner sc) throws InvalidPuzzleNameException, InvalidPuzzleSyntaxException {
        PuzzleName name = resolvePuzzleName(sc);
        return resolvePuzzle(name, sc);
    }

    private PuzzleName resolvePuzzleName(Scanner sc) throws InvalidPuzzleNameException {
        try {
            String name = sc.next();
            return switch (name.toLowerCase(Locale.ROOT)) {
                case SUDOKU -> PuzzleName.SUDOKU;
                case KAKURASU -> PuzzleName.KAKURASU;
                case BINAIRO -> PuzzleName.BINAIRO;
                case STAR_BATTLE1, STAR_BATTLE2 -> PuzzleName.STAR_BATTLE;
                case STAR_ -> {
                    if (_BATTLE.equals(sc.next()))
                        yield PuzzleName.STAR_BATTLE;
                    else throw new InvalidPuzzleNameException();
                }
                default -> throw new InvalidPuzzleNameException();
            };
        }
        catch (NoSuchElementException e) {
            throw new InvalidPuzzleNameException();
        }
    }

    private static Puzzle<?> resolvePuzzle(PuzzleName name, Scanner sc) throws InvalidPuzzleSyntaxException {
        return switch (name) {
            case SUDOKU -> new SudokuPuzzle(sc);
            case KAKURASU -> new KakurasuPuzzle(sc);
            case BINAIRO -> new BinairoPuzzle(sc);
            case STAR_BATTLE -> new StarBattlePuzzle(sc);
        };
    }
}
