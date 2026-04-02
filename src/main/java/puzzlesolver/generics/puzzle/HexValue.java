package puzzlesolver.generics.puzzle;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public enum HexValue implements PuzzleValue {
    EMPTY, V_1, V_2, V_3, V_4, V_5, V_6, V_7, V_8, V_9, V_A, V_B, V_C, V_D, V_E, V_F, BLOCKED;

    private static final HexValue[] values = values();
    public static final Set<HexValue> ALLOWED_VALUES = Collections.unmodifiableSet
            (EnumSet.of(V_1, V_2, V_3, V_4, V_5, V_6, V_7, V_8, V_9, V_A, V_B, V_C, V_D, V_E));
    public static final Set<HexValue> VALID_VALUES = Collections.unmodifiableSet
            (EnumSet.of(EMPTY, V_1, V_2, V_3, V_4, V_5, V_6, V_7, V_8, V_9, V_A, V_B, V_C, V_D, V_E));
    public static final Set<HexValue> EMPTY_VALUES = Collections.unmodifiableSet(EnumSet.of(EMPTY));
    public static final Set<HexValue> BLOCKED_VALUES = Collections.unmodifiableSet(EnumSet.of(BLOCKED));
    public static final int RADIX = 16;

    @Override
    public char toChar() {
        return Integer.toHexString(this.ordinal()).charAt(0);
    }

    public static HexValue decode(char c) throws IllegalArgumentException {
        try {
            return valueOf(Integer.parseInt(String.valueOf(c), RADIX));
        }
        catch (NumberFormatException e) {
            if (c == '.')
                return BLOCKED;
            else {
                throw new IllegalArgumentException();
            }
        }
    }

    public static char encode(HexValue v) {
        int x = v.ordinal();
        if (x == RADIX) {
            return '.';
        }
        return Integer.toHexString(x).charAt(0);
    }

    public static HexValue valueOf(int x) {
        return values[x];
    }

    @Override
    public Set<HexValue> getAllowedValues() {
        return ALLOWED_VALUES;
    }

    @Override
    public Set<HexValue> getBlockedValues() {
        return BLOCKED_VALUES;
    }

    @Override
    public Set<HexValue> getEmptyValues() {
        return EMPTY_VALUES;
    }

    @Override
    public Set<HexValue> getValidValues() {
        return VALID_VALUES;
    }

    @Override
    public HexValue getDefaultEmptyValue() {
        return EMPTY;
    }
}
