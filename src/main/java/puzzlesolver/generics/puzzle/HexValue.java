package puzzlesolver.generics.puzzle;

//public record HexValue(int value) implements PuzzleValue {
//
//    @Override
//    public char toChar() {
//        if (value < 10)
//            return (char) value;
//        else if (value > 10) {
//            return 0;
//        }
//        return 0;
//    }
//}

public enum HexValue implements PuzzleValue {
    EMPTY, V_1, V_2, V_3, V_4, V_5, V_6, V_7, V_8, V_9, V_A, V_B, V_C, V_D, V_E;
    private static final HexValue[] values = values();

    @Override
    public char toChar() {
        return Integer.toHexString(this.ordinal()).charAt(0);
    }

    public static HexValue valueOf(int x) {
        return values[x];
    }
}
