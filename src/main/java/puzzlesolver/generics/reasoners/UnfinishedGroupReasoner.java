package puzzlesolver.generics.reasoners;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.generics.puzzle.Group;
import puzzlesolver.generics.puzzle.Puzzle;
import puzzlesolver.generics.puzzle.PuzzleValue;

@Slf4j
public abstract class UnfinishedGroupReasoner<T extends PuzzleValue> extends AbstractReasoner<T> {
    public abstract boolean applyToGroup(Group<T> group, CompoundCommand comms);

    @Override
    public boolean apply(Puzzle<T> puzzle, CompoundCommand comms) {
        LOG_START();
        long t0 = System.nanoTime();
        for (Group<T> g : puzzle.getGrid().getGroups()) {
            if (g.isFinished())
                continue;
            if (applyToGroup(g, comms)) {
                log.info(Long.toString(System.nanoTime() - t0));
                LOG_SUCCESS();
                return true;
            }
        }
        log.info(Long.toString(System.nanoTime() - t0));
        LOG_FAIL();
        return false;
    }

    @Override
    protected Logger getLog() {
        return log;
    }
}
