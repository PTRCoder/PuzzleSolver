package puzzlesolver.generics.reasoners;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.generics.puzzle.Puzzle;
import puzzlesolver.generics.puzzle.PuzzleValue;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Value
public class AnyReasoner<T extends PuzzleValue> extends AbstractReasoner<T> {
    String name = this.getClass().getSimpleName();
    List<Reasoner<T>> reasoners;

    @Override
    public boolean apply(Puzzle<T> puzzle, CompoundCommand comms) {
        LOG_START();
        for (Reasoner<T> r : reasoners) {
            if (r.apply(puzzle, comms)) {
                LOG_SUCCESS();
                return true;
            }
        }
        LOG_FAIL();
        return false;
    }

    @Override
    protected Logger getLog() {
        return log;
    }
}
