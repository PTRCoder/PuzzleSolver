package puzzlesolver.generics.reasoners;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.generics.puzzle.Puzzle;
import puzzlesolver.generics.puzzle.PuzzleValue;
import puzzlesolver.loc.LogStrings;

@Slf4j
@Value
public class RepeatedReasoner<T extends PuzzleValue> implements Reasoner<T> {
    Reasoner<T> reasoner;

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
