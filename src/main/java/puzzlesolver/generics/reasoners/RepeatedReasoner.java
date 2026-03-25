package puzzlesolver.generics.reasoners;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.slf4j.XSlf4j;
import org.slf4j.ext.XLogger;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.generics.puzzle.Puzzle;

@EqualsAndHashCode(callSuper = true)
@XSlf4j
@Value
public class RepeatedReasoner<T> extends AbstractReasoner<T> {
    Reasoner<T> reasoner;

    @Override
    public boolean apply(Puzzle<T> puzzle, CompoundCommand comms) {
        log.info("Started applying the RepeatedReasoner");
        final boolean result = reasoner.apply(puzzle, comms);
        boolean x = result;
        while (x) {
            x = reasoner.apply(puzzle, comms);
        }
        log.info("The RepeatedReasoner has {}been successful", result ? "" : "not ");
        return result;
    }

    @Override
    protected XLogger getLog() {
        return log;
    }
}
