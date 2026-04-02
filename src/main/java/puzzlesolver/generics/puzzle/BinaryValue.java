package puzzlesolver.generics.puzzle;

import org.jetbrains.annotations.NonNls;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

@NonNls
public enum BinaryValue implements PuzzleValue {
    EMPTY, BLACK, WHITE, BLOCKED;

    public static final Set<BinaryValue> ALLOWED_VALUES = Collections.unmodifiableSet(EnumSet.of(BLACK, WHITE));
    public static final Set<BinaryValue> VALID_VALUES = Collections.unmodifiableSet(EnumSet.of(EMPTY, BLACK, WHITE));
    public static final Set<BinaryValue> EMPTY_VALUES = Collections.unmodifiableSet(EnumSet.of(EMPTY));
    public static final Set<BinaryValue> BLOCKED_VALUES = Collections.unmodifiableSet(EnumSet.of(BLOCKED));

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
    public Set<BinaryValue> getEmptyValues() {
        return EMPTY_VALUES;
    }

    @Override
    public Set<BinaryValue> getAllowedValues() {
        return ALLOWED_VALUES;
    }

    @Override
    public Set<BinaryValue> getBlockedValues() {
        return BLOCKED_VALUES;
    }

    @Override
    public Set<BinaryValue> getValidValues() {
        return VALID_VALUES;
    }

    @Override
    public BinaryValue getDefaultEmptyValue() {
        return EMPTY;
    }
}
