package puzzlesolver.ui;

import javafx.scene.control.ChoiceDialog;
import org.jetbrains.annotations.NonNls;
import puzzlesolver.loc.LocaleManager;

import java.util.List;
import java.util.Locale;

@NonNls
public class LocaleSelector extends ChoiceDialog<Locale> {
    private static final Locale US = Locale.US;
    private static final Locale UK = Locale.UK;
    private static final Locale NL = Locale.of("nl", "NL");
    private static final List<Locale> CHOICES = List.of(US, UK, NL);

    public LocaleSelector() {
        super(
                LocaleManager.LOCALE_PROPERTY.get(),
                CHOICES
        );
    }

}
