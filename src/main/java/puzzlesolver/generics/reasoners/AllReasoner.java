package puzzlesolver.generics.reasoners;

import lombok.extern.slf4j.Slf4j;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.generics.puzzle.Puzzle;
import puzzlesolver.generics.puzzle.PuzzleValue;
import puzzlesolver.loc.LogStrings;

import java.util.List;

@Slf4j
public record AllReasoner<T extends PuzzleValue>(List<Reasoner<T>> reasoners) implements Reasoner<T> {
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
