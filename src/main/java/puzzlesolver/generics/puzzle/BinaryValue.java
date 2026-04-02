package puzzlesolver.generics.puzzle;

import org.jetbrains.annotations.NonNls;

import java.util.List;

@NonNls
public enum BinaryValue implements PuzzleValue {
    EMPTY, BLACK, WHITE, BLOCKED;

    private static final BinaryValue[] values = values();
    private static final List<BinaryValue> ALLOWED_VALUES = List.of(BLACK, WHITE);
    private static final List<BinaryValue> VALID_VALUES = List.of(EMPTY, BLACK, WHITE);
    private static final List<BinaryValue> EMPTY_VALUES = List.of(EMPTY);
    private static final List<BinaryValue> BLOCKED_VALUES = List.of(BLOCKED);

    public char asChar() {
        return switch (this) {
            case EMPTY -> ' ';
            case BLACK -> 'B';
            case WHITE -> 'W';
            case BLOCKED -> '-';
        };
    }

    public char toChar() {
        return switch (this) {
            case EMPTY -> ' ';
            case BLACK -> 'B';
            case WHITE -> 'W';
            case BLOCKED -> '-';
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

    public static BinaryValue decode(char c) {
        return switch (c) {
            case '0', 'W' -> WHITE;
            case '1', 'B' -> BLACK;
            case '~', '-', 'E' -> EMPTY;
            case '.' -> BLOCKED;
            default -> throw new IllegalArgumentException();
        };
    }

    public static char encode(BinaryValue v) {
        return switch (v) {
            case WHITE -> '0';
            case BLACK -> '1';
            case EMPTY -> '-';
            case BLOCKED -> '.';
        };
    }

    public BinaryValue getComplement() {
        return switch (this) {
            case EMPTY -> EMPTY;
            case BLACK -> WHITE;
            case WHITE -> BLACK;
            case BLOCKED -> BLOCKED;
        };
    }

    @Override
    public List<BinaryValue> getEmptyValues() {
        return EMPTY_VALUES;
    }

    @Override
    public List<BinaryValue> getAllowedValues() {
        return ALLOWED_VALUES;
    }

    @Override
    public List<BinaryValue> getBlockedValues() {
        return BLOCKED_VALUES;
    }

    @Override
    public List<BinaryValue> getValidValues() {
        return VALID_VALUES;
    }

    @Override
    public BinaryValue getDefaultEmptyValue() {
        return EMPTY;
    }
}
