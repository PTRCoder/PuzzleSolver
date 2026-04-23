package puzzlesolver.generics.reasoners;

import lombok.extern.slf4j.Slf4j;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.generics.puzzle.Puzzle;
import puzzlesolver.generics.puzzle.PuzzleValue;
import puzzlesolver.loc.LogStrings;

import java.util.List;

@Slf4j
public record AnyReasoner<T extends PuzzleValue>(List<Reasoner<T>> reasoners) implements Reasoner<T> {
    @Override
    public boolean apply(Puzzle<T> puzzle, CompoundCommand comms) {
        log.trace(LogStrings.REASONER_START.get(), this.getClass().getSimpleName());
        for (Reasoner<T> r : reasoners) {
            if (r.apply(puzzle, comms)) {
                log.trace(LogStrings.REASONER_SUCCESS.get(), this.getClass().getSimpleName());
                return true;
            }
        }
        log.trace(LogStrings.REASONER_FAIL.get(), this.getClass().getSimpleName());
        return false;
    }


}
