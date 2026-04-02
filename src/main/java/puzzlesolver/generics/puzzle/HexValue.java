package puzzlesolver.generics.puzzle;

import java.util.EnumSet;

public enum HexValue implements PuzzleValue {
    EMPTY, V_1, V_2, V_3, V_4, V_5, V_6, V_7, V_8, V_9, V_A, V_B, V_C, V_D, V_E, V_F, BLOCKED;

    private static final HexValue[] values = values();
    private static final EnumSet<HexValue> ALLOWED_VALUES =
            EnumSet.of(V_1, V_2, V_3, V_4, V_5, V_6, V_7, V_8, V_9, V_A, V_B, V_C, V_D, V_E);
    private static final EnumSet<HexValue> VALID_VALUES =
            EnumSet.of(EMPTY, V_1, V_2, V_3, V_4, V_5, V_6, V_7, V_8, V_9, V_A, V_B, V_C, V_D, V_E);
    private static final EnumSet<HexValue> EMPTY_VALUES = EnumSet.of(EMPTY);
    private static final EnumSet<HexValue> BLOCKED_VALUES = EnumSet.of(BLOCKED);
    private static final int RADIX = 16;

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
    public EnumSet<HexValue> getAllowedValues() {
        return ALLOWED_VALUES;
    }

    @Override
    public EnumSet<HexValue> getBlockedValues() {
        return BLOCKED_VALUES;
    }

    @Override
    public EnumSet<HexValue> getEmptyValues() {
        return EMPTY_VALUES;
    }

    @Override
    public EnumSet<HexValue> getValidValues() {
        return VALID_VALUES;
    }

    @Override
    public HexValue getDefaultEmptyValue() {
        return EMPTY;
    }
}
