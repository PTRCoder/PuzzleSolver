package puzzlesolver.generics.reasoners;

import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.generics.puzzle.Puzzle;
import puzzlesolver.generics.puzzle.PuzzleValue;

public interface Reasoner<T extends PuzzleValue> {
    boolean apply(Puzzle<T> puzzle, CompoundCommand comms);
}
