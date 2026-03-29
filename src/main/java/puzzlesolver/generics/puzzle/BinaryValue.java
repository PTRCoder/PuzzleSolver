package puzzlesolver.generics.puzzle;

import org.jetbrains.annotations.NonNls;

import java.util.List;

@NonNls
public enum BinaryValue {
    EMPTY, BLACK, WHITE;

    public char asChar() {
        return switch (this) {
            case EMPTY -> ' ';
            case BLACK -> 'B';
            case WHITE -> 'W';
        };
    }

    public static BinaryValue fromChar(char c) throws IllegalArgumentException {
        return switch (c) {
            case '0', 'W' -> WHITE;
            case '1', 'B' -> BLACK;
            case 'E', '-' -> EMPTY;
            default -> throw new IllegalArgumentException();
        };
    }

    public int asInt() {
        return switch (this) {
            case EMPTY -> -1;
            case BLACK -> 1;
            case WHITE -> 0;
        };
    }

    public BinaryValue getComplement() {
        return switch (this) {
            case EMPTY -> EMPTY;
            case BLACK -> WHITE;
            case WHITE -> BLACK;
        };
    }

    public static final List<BinaryValue> nonEmptyValues = List.of(BLACK, WHITE);
}
