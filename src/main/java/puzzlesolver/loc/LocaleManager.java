package puzzlesolver.loc;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.experimental.UtilityClass;

import java.util.Locale;

@UtilityClass
public final class LocaleManager {
    public final ObjectProperty<Locale> LOCALE_PROPERTY = new SimpleObjectProperty<>(Locale.getDefault());
}
