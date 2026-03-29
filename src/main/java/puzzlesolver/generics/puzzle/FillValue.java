package puzzlesolver.generics.puzzle;

import org.jetbrains.annotations.NonNls;

import java.util.List;

@NonNls
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
