package puzzlesolver.generics.reasoners;

import lombok.Value;
import lombok.extern.slf4j.XSlf4j;
import org.slf4j.ext.XLogger;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.generics.puzzle.Puzzle;

import java.util.List;

@XSlf4j
@Value
public class AllReasoner<T> extends AbstractReasoner<T> {
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
    protected XLogger getLog() {
        return log;
    }
}
