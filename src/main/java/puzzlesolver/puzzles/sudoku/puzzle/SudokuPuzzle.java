package puzzlesolver.puzzles.sudoku.puzzle;

import lombok.Value;
import org.controlsfx.control.GridView;
import puzzlesolver.exceptions.InvalidPuzzleSyntaxException;
import puzzlesolver.generics.puzzle.HexValue;
import puzzlesolver.generics.puzzle.Puzzle;
import puzzlesolver.generics.reasoners.Reasoner;
import puzzlesolver.puzzles.sudoku.reasoners.SimpleSudokuReasoner;
import puzzlesolver.puzzles.sudoku.ui.SudokuGridUI;

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
    GridView<SudokuCell> view;

    public SudokuPuzzle(Scanner sc) throws InvalidPuzzleSyntaxException {
        this.grid = new SudokuGrid(sc);
        this.view = new SudokuGridUI(grid);
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

}
