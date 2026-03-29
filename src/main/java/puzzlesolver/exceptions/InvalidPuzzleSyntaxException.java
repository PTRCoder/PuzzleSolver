package puzzlesolver.exceptions;

import puzzlesolver.generics.puzzle.Puzzle;
import puzzlesolver.loc.ExceptionStrings;

import java.text.MessageFormat;

public class InvalidPuzzleSyntaxException extends Exception {

    public InvalidPuzzleSyntaxException(Class<? extends Puzzle<?>> claz, String message) {
        super(MessageFormat.format("{0}: {1}", claz.getSimpleName(), message));
    }

    public InvalidPuzzleSyntaxException(Class<? extends Puzzle<?>> claz, String message, Throwable cause) {
        super(MessageFormat.format("{0}: {1}", claz.getSimpleName(), message), cause);
    }

    public static InvalidPuzzleSyntaxException notEnough(Class<? extends Puzzle<?>> claz, Throwable cause) {
        return new InvalidPuzzleSyntaxException(claz, ExceptionStrings.PUZZLE_SYNTAX_SHORT.get(), cause);
    }

    public static InvalidPuzzleSyntaxException tooMany(Class<? extends Puzzle<?>> claz) {
        return new InvalidPuzzleSyntaxException(claz, ExceptionStrings.PUZZLE_SYNTAX_LONG.get());
    }
}
