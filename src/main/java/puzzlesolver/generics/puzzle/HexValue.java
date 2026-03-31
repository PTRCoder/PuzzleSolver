package puzzlesolver.generics.puzzle;

public record HexValue(int value) implements PuzzleValue {

    @Override
    public char toChar() {
        if (value < 10)
            return (char) value;
        else if (value > 10) {
            return 0;
        }
        return 0;
    }
}
