package puzzlesolver.puzzles.binairo.puzzle;

import lombok.Value;
import puzzlesolver.exceptions.InvalidPuzzleSyntaxException;
import puzzlesolver.generics.puzzle.BinaryValue;
import puzzlesolver.generics.puzzle.Grid;
import puzzlesolver.generics.puzzle.Puzzle;
import puzzlesolver.generics.reasoners.Reasoner;
import puzzlesolver.puzzles.binairo.reasoners.SimpleBinairoReasoner;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;

@Value
public class BinairoPuzzle implements Puzzle<BinaryValue> {
    private static final Reasoner<BinaryValue> DEFAULT_REASONER = new SimpleBinairoReasoner();
    Grid<BinaryValue> grid;

    public BinairoPuzzle(Scanner sc) throws InvalidPuzzleSyntaxException {
        this.grid = new BinairoGrid(sc);
    }

    @Override
    public Collection<BinaryValue> getAllowedValues() {
        return List.of(BinaryValue.BLACK, BinaryValue.WHITE);
    }

    @Override
    public Reasoner<BinaryValue> getDefaultReasoner() {
        return DEFAULT_REASONER;
    }

    @Override
    public String valueToString(BinaryValue value) {
        return valueToStringStatic(value);
    }

    private static String valueToStringStatic(BinaryValue value) {
        return Character.toString(value.asChar());
    }
}
