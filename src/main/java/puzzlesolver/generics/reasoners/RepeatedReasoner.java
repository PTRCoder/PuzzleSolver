package puzzlesolver.generics.reasoners;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.generics.puzzle.Puzzle;
import puzzlesolver.generics.puzzle.PuzzleValue;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Value
public class RepeatedReasoner<T extends PuzzleValue> extends AbstractReasoner<T> {
    Reasoner<T> reasoner;

    @Override
    public boolean apply(Puzzle<T> puzzle, CompoundCommand comms) {
        LOG_START();
        final boolean result = reasoner.apply(puzzle, comms);
        boolean x = result;
        while (x) {
            x = reasoner.apply(puzzle, comms);
        }
        LOG_FAIL();
        return result;
    }

    @Override
    protected Logger getLog() {
        return log;
    }
}
