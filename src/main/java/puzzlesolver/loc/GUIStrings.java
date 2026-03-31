package puzzlesolver.loc;

import javafx.beans.property.Property;
import javafx.beans.value.ObservableStringValue;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NonNls;

import java.util.ResourceBundle;

@UtilityClass
public final class GUIStrings {
    @NonNls
    private final String BUNDLE_NAME = "gui";
    private final Property<ResourceBundle> RESOURCE_BUNDLE_PROPERTY = LocaleManager.bundleProperty(BUNDLE_NAME);

    private ObservableStringValue get(String key) {
        return LocaleManager.localizedString(RESOURCE_BUNDLE_PROPERTY, key);
    }

    public final ObservableStringValue TITLE = get("title");
    public final ObservableStringValue FILE_MENU_NAME = get("menu.file");
    public final ObservableStringValue EDIT_MENU_NAME = get("menu.edit");
    public final ObservableStringValue PUZZLE_MENU_NAME = get("menu.puzzle");
    public final ObservableStringValue SETTINGS_MENU_NAME = get("menu.settings");
    public final ObservableStringValue FILE_OPEN_NAME = get("menu.file.open");
    public final ObservableStringValue FILE_CLOSE_NAME = get("menu.file.close");
    public final ObservableStringValue FILE_SAVE_NAME = get("menu.file.save");
    public final ObservableStringValue FILE_NEW_NAME = get("menu.file.new");
    public final ObservableStringValue EDIT_UNDO_NAME = get("menu.edit.undo");
    public final ObservableStringValue EDIT_REDO_NAME = get("menu.edit.redo");
    public final ObservableStringValue EDIT_UNDO_ALL_NAME = get("menu.edit.undo_all");
    public final ObservableStringValue EDIT_REDO_ALL_NAME = get("menu.edit.redo_all");
    public final ObservableStringValue SOLVE_SOLVE_NAME = get("menu.solve.solve");
    public final ObservableStringValue SOLVE_CONFIG_NAME = get("menu.solve.config");
    public final ObservableStringValue FC_LOAD_PUZZLE_TITLE = get("fc.puzzle.load.title");
    public final ObservableStringValue ERROR_TITLE = get("error.title");
    public final ObservableStringValue ERROR_PUZZLE_SYNTAX_MESSAGE = get("error.puzzle.syntax.message");
    public final ObservableStringValue ERROR_PUZZLE_NAME_MESSAGE = get("error.puzzle.name.message");
    public final ObservableStringValue ERROR_PUZZLE_FILE_MESSAGE = get("error.puzzle.nsf.message");
}
