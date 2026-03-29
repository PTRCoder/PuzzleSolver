package puzzlesolver.exceptions;

import puzzlesolver.loc.ExceptionStrings;

public class InvalidPuzzleNameException extends Exception {

    public InvalidPuzzleNameException(String message) {
        super(message);
    }

    public InvalidPuzzleNameException() {
        super(ExceptionStrings.PUZZLE_NAME_DEFAULT.get());
    }
}
