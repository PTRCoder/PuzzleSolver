package puzzlesolver.generics.reasoners;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.generics.puzzle.Puzzle;
import puzzlesolver.generics.puzzle.PuzzleValue;

import java.util.List;

@Slf4j
@Value
@EqualsAndHashCode(callSuper = true)
public class AllReasoner<T extends PuzzleValue> extends AbstractReasoner<T> {
    String name = this.getClass().getSimpleName();
    List<Reasoner<T>> reasoners;

    @Override
    public boolean apply(Puzzle<T> puzzle, CompoundCommand comms) {
        LOG_START();
        boolean result = false;
        for (Reasoner<T> r : reasoners) {
            result |= r.apply(puzzle, comms);
        }
        if (result) LOG_SUCCESS();
        else LOG_FAIL();
        return result;
    }

    @Override
    protected Logger getLog() {
        return log;
    }
}
