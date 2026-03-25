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

    public final ObservableStringValue REASONER_FAIL =
            LocaleManager.localizedString(RESOURCE_BUNDLE_PROPERTY, "reasoner.fail");
    public final ObservableStringValue REASONER_SUCCESS =
            LocaleManager.localizedString(RESOURCE_BUNDLE_PROPERTY, "reasoner.success");
    public final ObservableStringValue REASONER_START =
            LocaleManager.localizedString(RESOURCE_BUNDLE_PROPERTY, "reasoner.start");
    public final ObservableStringValue SOLVER_FAIL =
            LocaleManager.localizedString(RESOURCE_BUNDLE_PROPERTY, "solver.fail");
    public final ObservableStringValue SOLVER_SUCCESS =
            LocaleManager.localizedString(RESOURCE_BUNDLE_PROPERTY, "solver.success");
    public final ObservableStringValue SOLVER_START =
            LocaleManager.localizedString(RESOURCE_BUNDLE_PROPERTY, "solver.start");
}
