package puzzlesolver.generics.reasoners;

import lombok.extern.slf4j.XSlf4j;
import org.slf4j.ext.XLogger;
import puzzlesolver.Production;

import java.util.Locale;
import java.util.ResourceBundle;

@XSlf4j
public abstract class AbstractReasoner<T> implements Reasoner<T> {
    private final ResourceBundle bundle = ResourceBundle.getBundle("main");

    protected final void LOG_SUCCESS() {
        getLog().info(bundle.getString(Production.REASONER_SUCCESS.toString().toLowerCase()), getClass());
        getLog().exit();
    }

    protected final void LOG_FAIL() {
        getLog().info(bundle.getString("reason_fail"), getClass());
        getLog().exit();
    }

    protected final void LOG_START() {
        getLog().entry();
        getLog().info(bundle.getString("reason_starter"), getClass());
    }

    protected abstract XLogger getLog();
}
