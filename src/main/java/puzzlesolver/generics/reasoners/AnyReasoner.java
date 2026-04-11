package puzzlesolver.generics.reasoners;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.generics.puzzle.Puzzle;
import puzzlesolver.generics.puzzle.PuzzleValue;
import puzzlesolver.loc.LogStrings;

import java.util.List;

@Slf4j
@Value
public class AnyReasoner<T extends PuzzleValue> implements Reasoner<T> {
    List<Reasoner<T>> reasoners;

    @Override
    public boolean apply(Puzzle<T> puzzle, CompoundCommand comms) {
        log.info(LogStrings.REASONER_SUCCESS.get(), getClass());
        for (Reasoner<T> r : reasoners) {
            if (r.apply(puzzle, comms)) {
                log.info(LogStrings.REASONER_SUCCESS.get(), getClass());
                return true;
            }
        }
        log.info(LogStrings.REASONER_FAIL.get(), getClass());
        return false;
    }


}
