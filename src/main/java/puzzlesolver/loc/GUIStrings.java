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
    public static final ObservableStringValue FILE_OPEN_NAME =
            LocaleManager.localizedString(RESOURCE_BUNDLE_PROPERTY, "menu.file.open");
    public static final ObservableStringValue FILE_CLOSE_NAME =
            LocaleManager.localizedString(RESOURCE_BUNDLE_PROPERTY, "menu.file.close");
    public static final ObservableStringValue FILE_SAVE_NAME =
            LocaleManager.localizedString(RESOURCE_BUNDLE_PROPERTY, "menu.file.save");
    public static final ObservableStringValue FILE_NEW_NAME =
            LocaleManager.localizedString(RESOURCE_BUNDLE_PROPERTY, "menu.file.new");
    public static final ObservableStringValue EDIT_UNDO_NAME =
            LocaleManager.localizedString(RESOURCE_BUNDLE_PROPERTY, "menu.edit.undo");
    public static final ObservableStringValue EDIT_REDO_NAME =
            LocaleManager.localizedString(RESOURCE_BUNDLE_PROPERTY, "menu.edit.redo");
    public static final ObservableStringValue EDIT_UNDO_ALL_NAME =
            LocaleManager.localizedString(RESOURCE_BUNDLE_PROPERTY, "menu.edit.undo_all");
    public static final ObservableStringValue EDIT_REDO_ALL_NAME =
            LocaleManager.localizedString(RESOURCE_BUNDLE_PROPERTY, "menu.edit.redo_all");
    public static final ObservableStringValue SOLVE_SOLVE_NAME =
            LocaleManager.localizedString(RESOURCE_BUNDLE_PROPERTY, "menu.solve.solve");
    public static final ObservableStringValue SOLVE_CONFIG_NAME =
            LocaleManager.localizedString(RESOURCE_BUNDLE_PROPERTY, "menu.solve.config");
    public static final ObservableStringValue ERROR_TITLE =
            LocaleManager.localizedString(RESOURCE_BUNDLE_PROPERTY, "error.title");
}
