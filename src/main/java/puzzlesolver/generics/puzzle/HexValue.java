package puzzlesolver.generics.puzzle;

import java.util.List;

public enum HexValue implements PuzzleValue {
    EMPTY, V_1, V_2, V_3, V_4, V_5, V_6, V_7, V_8, V_9, V_A, V_B, V_C, V_D, V_E, V_F, BLOCKED;

    private static final HexValue[] values = values();
    private static final List<HexValue> ALLOWED_VALUES =
            List.of(V_1, V_2, V_3, V_4, V_5, V_6, V_7, V_8, V_9, V_A, V_B, V_C, V_D, V_E);
    private static final List<HexValue> VALID_VALUES =
            List.of(EMPTY, V_1, V_2, V_3, V_4, V_5, V_6, V_7, V_8, V_9, V_A, V_B, V_C, V_D, V_E);
    private static final List<HexValue> EMPTY_VALUES = List.of(EMPTY);
    private static final List<HexValue> BLOCKED_VALUES = List.of(BLOCKED);
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
    public List<HexValue> getAllowedValues() {
        return ALLOWED_VALUES;
    }

    @Override
    public List<HexValue> getBlockedValues() {
        return BLOCKED_VALUES;
    }

    @Override
    public List<HexValue> getEmptyValues() {
        return EMPTY_VALUES;
    }

    @Override
    public List<HexValue> getValidValues() {
        return VALID_VALUES;
    }

    @Override
    public PuzzleValue getDefaultEmptyValue() {
        return EMPTY;
    }
}
