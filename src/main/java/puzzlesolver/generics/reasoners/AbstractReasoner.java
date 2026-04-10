package puzzlesolver.generics.reasoners;

import org.slf4j.Logger;
import puzzlesolver.generics.puzzle.PuzzleValue;
import puzzlesolver.loc.LogStrings;

public abstract class AbstractReasoner<T extends PuzzleValue> implements Reasoner<T> {

    protected final void LOG_SUCCESS() {
        getLog().info(LogStrings.REASONER_SUCCESS.get(), getClass());
    }

    protected final void LOG_FAIL() {
        getLog().info(LogStrings.REASONER_FAIL.get(), getClass());
    }

    protected final void LOG_START() {
        getLog().info(LogStrings.REASONER_START.get(), getClass());
    }

    protected abstract Logger getLog();
}
