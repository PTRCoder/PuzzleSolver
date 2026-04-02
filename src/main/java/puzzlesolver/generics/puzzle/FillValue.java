package puzzlesolver.generics.puzzle;

import org.jetbrains.annotations.NonNls;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

@NonNls
public enum FillValue implements PuzzleValue {
    EMPTY, FILLED, CROSSED, BLOCKED;

    public static final Set<FillValue> ALLOWED_VALUES = Collections.unmodifiableSet(EnumSet.of(FILLED, CROSSED));
    public static final Set<FillValue> VALID_VALUES = Collections.unmodifiableSet(EnumSet.of(EMPTY, FILLED, CROSSED));
    public static final Set<FillValue> EMPTY_VALUES = Collections.unmodifiableSet(EnumSet.of(EMPTY));
    public static final Set<FillValue> BLOCKED_VALUES = Collections.unmodifiableSet(EnumSet.of(BLOCKED));

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
    public Set<FillValue> getEmptyValues() {
        return EMPTY_VALUES;
    }

    @Override
    public Set<FillValue> getBlockedValues() {
        return BLOCKED_VALUES;
    }

    @Override
    public Set<FillValue> getValidValues() {
        return VALID_VALUES;
    }

    @Override
    public Set<FillValue> getAllowedValues() {
        return ALLOWED_VALUES;
    }

    @Override
    public FillValue getDefaultEmptyValue() {
        return EMPTY;
    }
}
