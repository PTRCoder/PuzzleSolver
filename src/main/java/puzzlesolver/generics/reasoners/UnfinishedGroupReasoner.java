package puzzlesolver.generics.reasoners;


import lombok.extern.slf4j.Slf4j;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.generics.puzzle.Group;
import puzzlesolver.generics.puzzle.Puzzle;
import puzzlesolver.generics.puzzle.PuzzleValue;
import puzzlesolver.loc.LogStrings;

@Slf4j
public abstract class UnfinishedGroupReasoner<T extends PuzzleValue> implements Reasoner<T> {
    public abstract boolean applyToGroup(Group<T> group, CompoundCommand comms);

    @Override
    public boolean apply(Puzzle<T> puzzle, CompoundCommand comms) {
        log.trace(LogStrings.REASONER_START.get(), this.getClass().getSimpleName());
        long t0 = System.nanoTime();
        for (Group<T> g : puzzle.getGrid().getGroups()) {
            if (g.isFinished())
                continue;
            if (applyToGroup(g, comms)) {
                log.debug("{} took {} ns to complete", this.getClass().getSimpleName(), System.nanoTime() - t0);
                log.trace(LogStrings.REASONER_SUCCESS.get(), this.getClass().getSimpleName());
                return true;
            }
        }
        log.debug("{} took {} ns to complete", this.getClass().getSimpleName(), System.nanoTime() - t0);
        log.trace(LogStrings.REASONER_FAIL.get(), this.getClass().getSimpleName());
        return false;
    }


}
