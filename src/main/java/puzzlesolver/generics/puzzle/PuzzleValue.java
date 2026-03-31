package puzzlesolver.generics.puzzle;

public interface PuzzleValue {
    default String toText() {
        return Character.toString(toChar());
    }

    char toChar();
}
