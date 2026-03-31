package puzzlesolver.loc;

import javafx.beans.property.Property;
import javafx.beans.value.ObservableStringValue;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NonNls;

import java.util.ResourceBundle;

@UtilityClass
public class LogStrings {
    @NonNls
    private final String BUNDLE_NAME = "log";
    private final Property<ResourceBundle> RESOURCE_BUNDLE_PROPERTY = LocaleManager.bundleProperty(BUNDLE_NAME);

    private ObservableStringValue get(String key) {
        return LocaleManager.localizedString(RESOURCE_BUNDLE_PROPERTY, key);
    }

    public final ObservableStringValue REASONER_FAIL = get("reasoner.fail");
    public final ObservableStringValue REASONER_SUCCESS = get("reasoner.success");
    public final ObservableStringValue REASONER_START = get("reasoner.start");
    public final ObservableStringValue SOLVER_FAIL = get("solver.fail");
    public final ObservableStringValue SOLVER_SUCCESS = get("solver.success");
    public final ObservableStringValue SOLVER_START = get("solver.start");
}
