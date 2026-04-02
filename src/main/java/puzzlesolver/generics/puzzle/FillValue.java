package puzzlesolver.generics.puzzle;

import org.jetbrains.annotations.NonNls;

import java.util.EnumSet;

@NonNls
public enum FillValue implements PuzzleValue {
    EMPTY, FILLED, CROSSED, BLOCKED;

    private static final FillValue[] values = values();
    private static final EnumSet<FillValue> ALLOWED_VALUES = EnumSet.of(FILLED, CROSSED);
    private static final EnumSet<FillValue> VALID_VALUES = EnumSet.of(EMPTY, FILLED, CROSSED);
    private static final EnumSet<FillValue> EMPTY_VALUES = EnumSet.of(EMPTY);
    private static final EnumSet<FillValue> BLOCKED_VALUES = EnumSet.of(BLOCKED);

    public char asChar() {
        return switch (this) {
            case EMPTY -> ' ';
            case FILLED -> 'O';
            case CROSSED -> 'X';
            case BLOCKED -> '-';
        };
    }

    public static final EnumSet<FillValue> nonEmptyValues = EnumSet.of(CROSSED, FILLED);

    @Override
    public char toChar() {
        return switch (this) {
            case EMPTY -> ' ';
            case FILLED -> 'O';
            case CROSSED -> 'X';
            case BLOCKED -> '-';
        };
    }

    @Override
    public EnumSet<FillValue> getEmptyValues() {
        return EMPTY_VALUES;
    }

    @Override
    public EnumSet<FillValue> getBlockedValues() {
        return BLOCKED_VALUES;
    }

    @Override
    public EnumSet<FillValue> getValidValues() {
        return VALID_VALUES;
    }

    @Override
    public EnumSet<FillValue> getAllowedValues() {
        return ALLOWED_VALUES;
    }

    @Override
    public FillValue getDefaultEmptyValue() {
        return EMPTY;
    }
}
