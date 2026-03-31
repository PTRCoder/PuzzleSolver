package puzzlesolver.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lombok.experimental.UtilityClass;
import puzzlesolver.exceptions.InvalidPuzzleNameException;
import puzzlesolver.exceptions.InvalidPuzzleSyntaxException;
import puzzlesolver.loc.GUIStrings;

import java.io.FileNotFoundException;

@UtilityClass
public class ExceptionAlertFactory {
    public static Alert getInstance(Throwable e) {
        return switch (e) {
            case InvalidPuzzleNameException ignored -> new Alert(
                    Alert.AlertType.ERROR, GUIStrings.ERROR_PUZZLE_NAME_MESSAGE.get(), ButtonType.CLOSE
            );
            case InvalidPuzzleSyntaxException ignored -> new Alert(
                    Alert.AlertType.ERROR, GUIStrings.ERROR_PUZZLE_SYNTAX_MESSAGE.get(), ButtonType.CLOSE
            );
            case FileNotFoundException ignored -> new Alert(
                    Alert.AlertType.ERROR, GUIStrings.ERROR_PUZZLE_FILE_MESSAGE.get(), ButtonType.CLOSE
            );
            default -> throw new UnsupportedOperationException();
        };
    }
}
