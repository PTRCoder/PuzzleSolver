package puzzlesolver.exceptions;

public class InvalidPuzzleNameException extends Exception {
    private static final String DEFAULT_MESSAGE = "This name cannot be resolved";
    public InvalidPuzzleNameException(String message) {
        super(message);
    }

    public InvalidPuzzleNameException() {
        super(DEFAULT_MESSAGE);
    }
}
