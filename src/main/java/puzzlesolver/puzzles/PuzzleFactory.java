package puzzlesolver.puzzles;

import lombok.experimental.UtilityClass;
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

@UtilityClass
public final class PuzzleFactory {
    public final String ID_SUDOKU = "sudoku";
    public final String ID_KAKURASU = "kakurasu";
    public final String ID_BINAIRO = "binairo";
    public final String ID_STAR_BATTLE1 = "starbattle";
    public final String ID_STAR_BATTLE2 = "star_battle";

    public Puzzle<?> create(Scanner sc) throws InvalidPuzzleNameException, InvalidPuzzleSyntaxException {
        PuzzleName name = resolvePuzzleName(sc);
        return resolvePuzzle(name, sc);
    }

    private PuzzleName resolvePuzzleName(Scanner sc) throws InvalidPuzzleNameException {
        try {
            String name = sc.next();
            return switch (name.toLowerCase(Locale.ROOT)) {
                case ID_SUDOKU -> PuzzleName.SUDOKU;
                case ID_KAKURASU -> PuzzleName.KAKURASU;
                case ID_BINAIRO -> PuzzleName.BINAIRO;
                case ID_STAR_BATTLE1, ID_STAR_BATTLE2 -> PuzzleName.STAR_BATTLE;
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
