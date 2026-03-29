package puzzlesolver.generics.reasoners;

import org.slf4j.ext.XLogger;
import puzzlesolver.loc.LogStrings;

public abstract class AbstractReasoner<T> implements Reasoner<T> {

    protected final void LOG_SUCCESS() {
        getLog().info(LogStrings.REASONER_SUCCESS.get(), getClass());
        getLog().exit(true);
    }

    protected final void LOG_FAIL() {
        getLog().info(LogStrings.REASONER_FAIL.get(), getClass());
        getLog().exit(false);
    }

    protected final void LOG_START() {
        getLog().entry();
        getLog().info(LogStrings.REASONER_START.get(), getClass());
    }

    protected abstract XLogger getLog();
}
