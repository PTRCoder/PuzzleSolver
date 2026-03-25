package puzzlesolver.generics.puzzle;

import java.util.List;

public enum FillValue {
    EMPTY, FILLED, CROSSED;

    public char asChar() {
        return switch (this) {
            case EMPTY -> ' ';
            case FILLED -> 'O';
            case CROSSED -> 'X';
        };
    }

    public static final List<FillValue> nonEmptyValues = List.of(CROSSED, FILLED);
}
