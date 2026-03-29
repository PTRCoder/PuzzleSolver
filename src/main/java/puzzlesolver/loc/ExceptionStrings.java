package puzzlesolver.loc;

import javafx.beans.property.Property;
import javafx.beans.value.ObservableStringValue;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NonNls;

import java.util.ResourceBundle;

@UtilityClass
public class ExceptionStrings {
    @NonNls
    private static final String BUNDLE_NAME = "exceptions";
    private static final Property<ResourceBundle> RESOURCE_BUNDLE_PROPERTY = LocaleManager.bundleProperty(BUNDLE_NAME);

    public static final ObservableStringValue PUZZLE_NAME_DEFAULT =
            LocaleManager.localizedString(RESOURCE_BUNDLE_PROPERTY, "puzzle.name.default");
    public static final ObservableStringValue PUZZLE_SYNTAX_LONG =
            LocaleManager.localizedString(RESOURCE_BUNDLE_PROPERTY, "puzzle.syntax.long");
    public static final ObservableStringValue PUZZLE_SYNTAX_SHORT =
            LocaleManager.localizedString(RESOURCE_BUNDLE_PROPERTY, "puzzle.syntax.short");
}
