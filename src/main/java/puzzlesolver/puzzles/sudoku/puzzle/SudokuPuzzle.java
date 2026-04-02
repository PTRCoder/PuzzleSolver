package puzzlesolver.puzzles.sudoku.puzzle;

import lombok.Value;
import puzzlesolver.exceptions.InvalidPuzzleSyntaxException;
import puzzlesolver.generics.puzzle.HexValue;
import puzzlesolver.generics.puzzle.Puzzle;
import puzzlesolver.generics.reasoners.Reasoner;
import puzzlesolver.puzzles.sudoku.reasoners.SimpleSudokuReasoner;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

@Value
public class SudokuPuzzle implements Puzzle<HexValue> {
    private static final Reasoner<HexValue> DEFAULT_REASONER = new SimpleSudokuReasoner();
    private static List<HexValue> cachedAllowed = Collections.emptyList();
    private static int cachedSize;

    SudokuGrid grid;

    public SudokuPuzzle(Scanner sc) throws InvalidPuzzleSyntaxException {
        this.grid = new SudokuGrid(sc);
    }

    @Override
    public Collection<HexValue> getAllowedValues() {
        return staticAllowedValues(grid.getWidth());
    }

    public static List<HexValue> staticAllowedValues(int size) {
        if (size == cachedSize)
            return cachedAllowed;
        cachedSize = size;
        cachedAllowed = IntStream.rangeClosed(1, size).mapToObj(HexValue::valueOf).toList();
        return cachedAllowed;
    }

    @Override
    public Reasoner<HexValue> getDefaultReasoner() {
        return DEFAULT_REASONER;
    }

    @Override
    public String valueToString(HexValue value) {
        return value.toText();
    }

}
