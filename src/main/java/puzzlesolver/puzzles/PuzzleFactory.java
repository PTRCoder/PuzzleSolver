package puzzlesolver.puzzles;

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

public final class PuzzleFactory {
    private PuzzleFactory() {}

    public static Puzzle<?> create(Scanner sc) throws InvalidPuzzleNameException, InvalidPuzzleSyntaxException {
        PuzzleName name = resolvePuzzleName(sc);
        return resolvePuzzle(name, sc);
    }

    private static PuzzleName resolvePuzzleName(Scanner sc) throws InvalidPuzzleNameException {
        try {
            String name = sc.next();
            return switch (name.toLowerCase(Locale.ROOT)) {
                case "sudoku" -> PuzzleName.SUDOKU;
                case "kakurasu" -> PuzzleName.KAKURASU;
                case "binairo" -> PuzzleName.BINAIRO;
                case "starbattle", "star_battle" -> PuzzleName.STAR_BATTLE;
                case "star" -> {
                    if ("battle".equals(sc.next()))
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

    private static Puzzle<?> resolvePuzzle(PuzzleName name, Scanner sc) throws InvalidPuzzleSyntaxException{
        return switch (name) {
            case SUDOKU -> new SudokuPuzzle(sc);
            case KAKURASU -> new KakurasuPuzzle(sc);
            case BINAIRO -> new BinairoPuzzle(sc);
            case STAR_BATTLE -> new StarBattlePuzzle(sc);
        };
    }
}
