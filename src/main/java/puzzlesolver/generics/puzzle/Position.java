package puzzlesolver.generics.puzzle;

import org.jetbrains.annotations.NonNls;

@NonNls
public record Position(int x, int y) {
    @Override
    public String toString() {
        return "[%d,%d]".formatted(x, y);
    }
}
