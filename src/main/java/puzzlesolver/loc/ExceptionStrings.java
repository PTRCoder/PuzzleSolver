package puzzlesolver.loc;

import javafx.beans.property.Property;
import javafx.beans.value.ObservableStringValue;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NonNls;

import java.util.ResourceBundle;

@UtilityClass
public class ExceptionStrings {
    @NonNls
    private final String BUNDLE_NAME = "exceptions";
    private final Property<ResourceBundle> RESOURCE_BUNDLE_PROPERTY = LocaleManager.bundleProperty(BUNDLE_NAME);

    private ObservableStringValue get(String key) {
        return LocaleManager.localizedString(RESOURCE_BUNDLE_PROPERTY, key);
    }

    public final ObservableStringValue PUZZLE_NAME_DEFAULT = get("puzzle.name.default");
    public final ObservableStringValue PUZZLE_SYNTAX_LONG = get("puzzle.syntax.long");
    public final ObservableStringValue PUZZLE_SYNTAX_SHORT = get("puzzle.syntax.short");
}
