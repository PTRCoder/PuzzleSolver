package puzzlesolver.generics.reasoners;

import lombok.extern.slf4j.Slf4j;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.generics.puzzle.Puzzle;
import puzzlesolver.generics.puzzle.PuzzleValue;
import puzzlesolver.loc.LogStrings;

@Slf4j
public record RepeatedReasoner<T extends PuzzleValue>(Reasoner<T> reasoner) implements Reasoner<T> {
    @Override
    public boolean apply(Puzzle<T> puzzle, CompoundCommand comms) {
        log.trace(LogStrings.REASONER_START.get(), this.getClass().getSimpleName());
        final boolean result = reasoner.apply(puzzle, comms);
        boolean x = result;
        while (x) {
            x = reasoner.apply(puzzle, comms);
        }
        log.trace(result ? LogStrings.REASONER_SUCCESS.get() : LogStrings.REASONER_FAIL.get(),
                this.getClass().getSimpleName());
        return result;
    }


}
