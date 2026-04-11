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
public class AllReasoner<T extends PuzzleValue> implements Reasoner<T> {
    String name = this.getClass().getSimpleName();
    List<Reasoner<T>> reasoners;

    @Override
    public boolean apply(Puzzle<T> puzzle, CompoundCommand comms) {
        log.info(LogStrings.REASONER_SUCCESS.get(), getClass());
        boolean result = false;
        for (Reasoner<T> r : reasoners) {
            result |= r.apply(puzzle, comms);
        }
        if (result) log.info(LogStrings.REASONER_SUCCESS.get(), getClass());
        else log.info(LogStrings.REASONER_FAIL.get(), getClass());
        return result;
    }
}
