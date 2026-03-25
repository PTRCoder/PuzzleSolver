package puzzlesolver.generics.reasoners;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.slf4j.XSlf4j;
import org.slf4j.ext.XLogger;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.generics.puzzle.Puzzle;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@XSlf4j
@Value
public class AnyReasoner<T> extends AbstractReasoner<T> {
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
    protected XLogger getLog() {
        return log;
    }
}
