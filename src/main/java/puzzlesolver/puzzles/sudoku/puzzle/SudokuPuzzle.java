package puzzlesolver.puzzles.sudoku.puzzle;

import lombok.Value;
import puzzlesolver.exceptions.InvalidPuzzleSyntaxException;
import puzzlesolver.generics.puzzle.Puzzle;
import puzzlesolver.generics.reasoners.Reasoner;
import puzzlesolver.puzzles.sudoku.reasoners.SimpleSudokuReasoner;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

@Value
public class SudokuPuzzle implements Puzzle<Integer> {
    private static final Reasoner<Integer> DEFAULT_REASONER = new SimpleSudokuReasoner();
    private static List<Integer> cachedAllowed = Collections.emptyList();
    private static int cachedSize;

    SudokuGrid grid;

    public SudokuPuzzle(Scanner sc) throws InvalidPuzzleSyntaxException {
        this.grid = new SudokuGrid(sc);
    }

    @Override
    public List<Integer> getAllowedValues() {
        return staticAllowedValues(grid.getWidth());
    }

    public static List<Integer> staticAllowedValues(int size) {
        if (size == cachedSize)
            return cachedAllowed;
        cachedSize = size;
        cachedAllowed = IntStream.rangeClosed(1, size).boxed().toList();
        return cachedAllowed;
    }

    @Override
    public Reasoner<Integer> getDefaultReasoner() {
        return DEFAULT_REASONER;
    }

    @Override
    public String valueToString(Integer value) {
        return valueToStringStatic(value);
    }

    private static String valueToStringStatic(Integer value) {
        if (value == SudokuCell.EMPTY) {
            return " ";
        }
        if (value < 10)
            return Integer.toString(value);
        else
            return Character.toString('A' + (value - 10));
    }
}
