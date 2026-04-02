package puzzlesolver.puzzles.starbattle.puzzle;

import lombok.Value;
import puzzlesolver.exceptions.InvalidPuzzleSyntaxException;
import puzzlesolver.generics.puzzle.FillValue;
import puzzlesolver.generics.puzzle.Puzzle;
import puzzlesolver.generics.reasoners.Reasoner;
import puzzlesolver.puzzles.starbattle.reasoners.DefaultStarBattleReasoner;

import java.util.Scanner;

@Value
public class StarBattlePuzzle implements Puzzle<FillValue> {
    StarBattleGrid grid;

    public StarBattlePuzzle(Scanner sc) throws InvalidPuzzleSyntaxException {
        this.grid = new StarBattleGrid(sc);
    }

    @Override
    public Reasoner<FillValue> getDefaultReasoner() {
        return new DefaultStarBattleReasoner();
    }
}
