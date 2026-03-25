package puzzlesolver.generics.reasoners;

import lombok.extern.slf4j.XSlf4j;
import org.slf4j.ext.XLogger;
import puzzlesolver.loc.LogStrings;

@XSlf4j
public abstract class AbstractReasoner<T> implements Reasoner<T> {

    protected final void LOG_SUCCESS() {
        getLog().info(LogStrings.REASONER_SUCCESS.get(), getClass());
        getLog().exit();
    }

    protected final void LOG_FAIL() {
        getLog().info(LogStrings.REASONER_FAIL.get(), getClass());
        getLog().exit();
    }

    protected final void LOG_START() {
        getLog().entry();
        getLog().info(LogStrings.REASONER_START.get(), getClass());
    }

    protected abstract XLogger getLog();
}
