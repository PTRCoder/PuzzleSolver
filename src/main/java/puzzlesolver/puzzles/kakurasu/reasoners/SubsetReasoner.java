package puzzlesolver.puzzles.kakurasu.reasoners;

import puzzlesolver.commands.Command;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.commands.MultiCommand;
import puzzlesolver.commands.ValueCommand;
import puzzlesolver.generics.puzzle.Group;
import puzzlesolver.puzzles.kakurasu.puzzle.KakurasuCell;
import puzzlesolver.puzzles.kakurasu.puzzle.KakurasuGroup;
import puzzlesolver.generics.puzzle.FillValue;
import puzzlesolver.generics.reasoners.UnfinishedGroupReasoner;

import java.util.*;
import java.util.stream.IntStream;

public class SubsetReasoner extends UnfinishedGroupReasoner<FillValue> {
    @Override
    public boolean applyToGroup(Group<FillValue> group, CompoundCommand comms) {
        KakurasuGroup g = (KakurasuGroup) group;
        int remaining = g.getSum() - g.computeSum();
        List<KakurasuCell> cells = g.getCells();
        List<Integer> emptyCells = IntStream.range(0, cells.size())
                .filter(x -> cells.get(x).isEmpty()).boxed().toList();
        List<List<Integer>> subsets = new LinkedList<>();
        fillSubsets(emptyCells, subsets, remaining, new LinkedList<>(), 0);
        List<Command> commands = new LinkedList<>();
        Command mc = new MultiCommand(commands);
        for (int x : intersection(emptyCells, subsets)) {
            commands.add(new ValueCommand<>(cells.get(x), FillValue.FILLED));
        }
        for (int x : exclusion(emptyCells, subsets)) {
            commands.add(new ValueCommand<>(cells.get(x), FillValue.CROSSED));
        }
        if (commands.isEmpty())
            return false;
        comms.add(mc);
        return true;
    }

    private void fillSubsets(List<Integer> emptyCells, List<? super List<Integer>> subsets,
                             int remaining, List<Integer> acc, int depth) {
        if (remaining == 0) {
            subsets.add(new ArrayList<>(acc));
            return;
        }
        for (int x : emptyCells) {
            if (x + 1 <= remaining && !acc.contains(x)) {
                acc.add(x);
                fillSubsets(emptyCells, subsets, remaining - x - 1, acc, depth + 1);
                acc.remove((Integer) x);
            }
        }
    }

    private List<Integer> intersection(
            Collection<Integer> emptyCells,
            Collection<? extends Collection<Integer>> subsets
    ) {
        return emptyCells.stream().filter(x -> subsets.stream().allMatch(y -> y.contains(x))).toList();
    }

    private List<Integer> exclusion(
            Collection<Integer> emptyCells,
            Collection<? extends Collection<Integer>> subsets
    ) {
        return emptyCells.stream().filter(x -> subsets.stream().noneMatch(y -> y.contains(x))).toList();
    }
}
