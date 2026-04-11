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
        log.info(LogStrings.REASONER_SUCCESS.get(), getClass());
        final boolean result = reasoner.apply(puzzle, comms);
        boolean x = result;
        while (x) {
            x = reasoner.apply(puzzle, comms);
        }
        log.info(LogStrings.REASONER_FAIL.get(), getClass());
        return result;
    }


}
