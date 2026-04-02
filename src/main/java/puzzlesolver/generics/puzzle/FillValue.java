package puzzlesolver.generics.puzzle;

import org.jetbrains.annotations.NonNls;

import java.util.List;

@NonNls
public enum FillValue implements PuzzleValue {
    EMPTY, FILLED, CROSSED, BLOCKED;

    private static final FillValue[] values = values();
    private static final List<FillValue> ALLOWED_VALUES = List.of(FILLED, CROSSED);
    private static final List<FillValue> VALID_VALUES = List.of(EMPTY, FILLED, CROSSED);
    private static final List<FillValue> EMPTY_VALUES = List.of(EMPTY);
    private static final List<FillValue> BLOCKED_VALUES = List.of(BLOCKED);

    public char asChar() {
        return switch (this) {
            case EMPTY -> ' ';
            case FILLED -> 'O';
            case CROSSED -> 'X';
            case BLOCKED -> '-';
        };
    }

    public static final List<FillValue> nonEmptyValues = List.of(CROSSED, FILLED);

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
    public List<FillValue> getEmptyValues() {
        return EMPTY_VALUES;
    }

    @Override
    public List<FillValue> getBlockedValues() {
        return BLOCKED_VALUES;
    }

    @Override
    public List<FillValue> getValidValues() {
        return VALID_VALUES;
    }

    @Override
    public List<FillValue> getAllowedValues() {
        return ALLOWED_VALUES;
    }

    @Override
    public PuzzleValue getDefaultEmptyValue() {
        return EMPTY;
    }
}
