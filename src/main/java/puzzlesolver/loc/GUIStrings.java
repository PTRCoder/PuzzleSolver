package puzzlesolver.loc;

import javafx.beans.property.Property;
import javafx.beans.value.ObservableStringValue;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NonNls;

import java.util.ResourceBundle;

@UtilityClass
public final class GUIStrings {
    @NonNls
    private static final String BUNDLE_NAME = "gui";
    private static final Property<ResourceBundle> RESOURCE_BUNDLE_PROPERTY = LocaleManager.bundleProperty(BUNDLE_NAME);

    public static final ObservableStringValue TITLE =
            LocaleManager.localizedString(RESOURCE_BUNDLE_PROPERTY, "title");
    public static final ObservableStringValue FILE_MENU_NAME =
            LocaleManager.localizedString(RESOURCE_BUNDLE_PROPERTY, "menu.file");
    public static final ObservableStringValue EDIT_MENU_NAME =
            LocaleManager.localizedString(RESOURCE_BUNDLE_PROPERTY, "menu.edit");
    public static final ObservableStringValue PUZZLE_MENU_NAME =
            LocaleManager.localizedString(RESOURCE_BUNDLE_PROPERTY, "menu.puzzle");
    public static final ObservableStringValue SETTINGS_MENU_NAME =
            LocaleManager.localizedString(RESOURCE_BUNDLE_PROPERTY, "menu.settings");
}
