package puzzlesolver.commands;

import lombok.Getter;
import puzzlesolver.generics.puzzle.Cell;

import java.util.List;

public class CandidateCommand<T> implements Command {
    private final Cell<T> cell;
    private final List<T> removedCandidates;
    @Getter
    private boolean applied = false;

    public CandidateCommand(Cell<T> cell, List<T> remove) {
        this.cell = cell;
        remove.retainAll(cell.getCandidates());
        this.removedCandidates = remove;
    }

    @Override
    public void apply() {
        applied = true;
        cell.getCandidates().removeAll(removedCandidates);
    }

    @Override
    public void undo() {
        applied = false;
        cell.getCandidates().addAll(removedCandidates);
    }


}
