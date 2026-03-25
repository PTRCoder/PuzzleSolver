package puzzlesolver.loc;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import lombok.experimental.UtilityClass;

import java.util.Locale;
import java.util.ResourceBundle;

@UtilityClass
public final class LocaleManager {
    public final ObjectProperty<Locale> LOCALE_PROPERTY = new SimpleObjectProperty<>(Locale.getDefault());

    public Property<ResourceBundle> bundleProperty(String bundle) {
        Property<ResourceBundle> property = new SimpleObjectProperty<>();
        property.bind(
                LOCALE_PROPERTY.map(l -> ResourceBundle.getBundle(bundle, l))
        );
        return property;
    }

    public ObservableStringValue localizedString(ObservableValue<? extends ResourceBundle> bundle, String key) {
        return StringProperty.stringExpression(bundle.map(x -> x.getString(key)));
    }
}
