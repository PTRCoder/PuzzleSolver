package puzzlesolver.exceptions;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import puzzlesolver.generics.puzzle.Puzzle;

import java.text.MessageFormat;

public class InvalidPuzzleSyntaxException extends Exception {
    private static final ObservableStringValue NOT_ENOUGH = new SimpleStringProperty("Not enough values");
    private static final ObservableStringValue TOO_MANY = new SimpleStringProperty("Too many values");

    public InvalidPuzzleSyntaxException(Class<? extends Puzzle<?>> claz, String message) {
        super(MessageFormat.format("{0}: {1}", claz.getSimpleName(), message));
    }

    public InvalidPuzzleSyntaxException(Class<? extends Puzzle<?>> claz, String message, Throwable cause) {
        super(MessageFormat.format("{0}: {1}", claz.getSimpleName(), message), cause);
    }

    public static InvalidPuzzleSyntaxException notEnough(Class<? extends Puzzle<?>> claz, Throwable cause) {
        return new InvalidPuzzleSyntaxException(claz, NOT_ENOUGH.get(), cause);
    }

    public static InvalidPuzzleSyntaxException tooMany(Class<? extends Puzzle<?>> claz) {
        return new InvalidPuzzleSyntaxException(claz, TOO_MANY.get());
    }
}
