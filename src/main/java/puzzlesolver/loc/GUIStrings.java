package puzzlesolver.loc;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NonNls;

import java.util.ResourceBundle;

@UtilityClass
public final class GUIStrings {
    @NonNls
    private static final String BUNDLE_NAME = "gui";
    private static final Property<ResourceBundle> RESOURCE_BUNDLE_PROPERTY = new SimpleObjectProperty<>();

    static {
        RESOURCE_BUNDLE_PROPERTY.bind(
                LocaleManager.LOCALE_PROPERTY.map(l -> ResourceBundle.getBundle(BUNDLE_NAME, l))
        );
    }

    public static final ObservableStringValue TITLE = StringProperty.stringExpression(
            RESOURCE_BUNDLE_PROPERTY.map(x -> x.getString("title"))
    );
    public static final ObservableStringValue FILE_MENU_NAME = StringProperty.stringExpression(
            RESOURCE_BUNDLE_PROPERTY.map(x -> x.getString("menu.file"))
    );
    public static final ObservableStringValue EDIT_MENU_NAME = StringProperty.stringExpression(
            RESOURCE_BUNDLE_PROPERTY.map(x -> x.getString("menu.edit"))
    );
    public static final ObservableStringValue PUZZLE_MENU_NAME = StringProperty.stringExpression(
            RESOURCE_BUNDLE_PROPERTY.map(x -> x.getString("menu.puzzle"))
    );
    public static final ObservableStringValue SETTINGS_MENU_NAME = StringProperty.stringExpression(
            RESOURCE_BUNDLE_PROPERTY.map(x -> x.getString("menu.settings"))
    );
}
