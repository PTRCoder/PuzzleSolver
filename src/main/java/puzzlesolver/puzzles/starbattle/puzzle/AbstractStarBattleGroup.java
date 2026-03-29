package puzzlesolver.puzzles.starbattle.puzzle;

import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.IntegerProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import puzzlesolver.generics.puzzle.FillValue;
import puzzlesolver.generics.puzzle.Group;

import java.util.List;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
public sealed abstract class AbstractStarBattleGroup
        implements Group<FillValue>
        permits StarBattleGroup, StarBattleSquare {
    List<StarBattleCell> cells;

    public abstract IntegerProperty starCountProperty();

    public abstract BooleanExpression allowsStarProperty();

    public abstract BooleanExpression allowsCrossProperty();
}
