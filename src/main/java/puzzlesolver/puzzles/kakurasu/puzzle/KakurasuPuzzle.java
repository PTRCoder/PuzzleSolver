package puzzlesolver.puzzles.kakurasu.puzzle;

import puzzlesolver.exceptions.InvalidPuzzleSyntaxException;
import puzzlesolver.generics.puzzle.FillValue;
import puzzlesolver.generics.puzzle.Puzzle;
import lombok.Value;
import puzzlesolver.generics.reasoners.Reasoner;
import puzzlesolver.puzzles.kakurasu.reasoners.SimpleKakurasuReasoner;

import java.util.List;
import java.util.Scanner;

@Value
public class KakurasuPuzzle implements Puzzle<FillValue> {
    private static final Reasoner<FillValue> DEFAULT_REASONER = new SimpleKakurasuReasoner();
    KakurasuGrid grid;

    public KakurasuPuzzle(Scanner sc) throws InvalidPuzzleSyntaxException {
        this.grid = new KakurasuGrid(sc);
    }

    @Override
    public List<FillValue> getAllowedValues() {
        return List.of(FillValue.FILLED, FillValue.CROSSED);
    }

    @Override
    public Reasoner<FillValue> getDefaultReasoner() {
        return DEFAULT_REASONER;
    }

}
