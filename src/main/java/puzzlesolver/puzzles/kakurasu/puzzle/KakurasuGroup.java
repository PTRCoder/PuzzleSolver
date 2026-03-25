package puzzlesolver.puzzles.kakurasu.puzzle;

import lombok.Value;
import puzzlesolver.generics.puzzle.FillValue;
import puzzlesolver.generics.puzzle.Group;

import java.util.List;
import java.util.stream.IntStream;

@Value
public class KakurasuGroup implements Group<FillValue> {
    int sum;
    List<KakurasuCell> cells;

    @Override
    public boolean validate() {
        return computeSum() <= sum && sum <= computeMaxSum();
    }

    public int computeSum() {
        return IntStream.range(0, cells.size())
                .map(k -> cells.get(k).getValue() == FillValue.FILLED ? k + 1 : 0)
                .sum();
    }

    public int computeMaxSum() {
        return IntStream.range(0, cells.size())
                .map(k -> cells.get(k).getValue() != FillValue.CROSSED ? k + 1 : 0)
                .sum();
    }

    @Override
    public List<FillValue> getAllowedValues() {
        return List.of(FillValue.FILLED, FillValue.CROSSED);
    }
}
