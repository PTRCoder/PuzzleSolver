package puzzlesolver.generics.reasoners;

import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.generics.puzzle.Puzzle;

public interface Reasoner<T> {
    boolean apply(Puzzle<T> puzzle, CompoundCommand comms);
}
