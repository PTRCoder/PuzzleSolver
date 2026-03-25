package puzzlesolver.exceptions;

import puzzlesolver.generics.puzzle.Puzzle;

import java.text.MessageFormat;

public class InvalidPuzzleSyntaxException extends Exception {
    public InvalidPuzzleSyntaxException(Class<? extends Puzzle<?>> claz, String message) {
        super(MessageFormat.format("{0}: {1}", claz.getSimpleName(), message));
    }

    public InvalidPuzzleSyntaxException(Class<? extends Puzzle<?>> claz, String message, Throwable cause) {
        super(MessageFormat.format("{0}: {1}", claz.getSimpleName(), message), cause);
    }
}
