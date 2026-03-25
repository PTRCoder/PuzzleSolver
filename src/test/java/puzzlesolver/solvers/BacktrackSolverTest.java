package puzzlesolver.solvers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.exceptions.InvalidPuzzleSyntaxException;
import puzzlesolver.puzzles.sudoku.puzzle.SudokuPuzzle;

import java.util.Scanner;

public class BacktrackSolverTest {

    @Test
    void solveSudoku() throws InvalidPuzzleSyntaxException {
        Scanner sc = new Scanner("""
                9
                7 0 0 0 0 0 2 0 0
                4 0 2 0 0 0 0 0 3
                0 0 0 2 0 1 0 0 0
                3 0 0 1 8 0 0 9 7
                0 0 9 0 7 0 6 0 0
                6 5 0 0 3 2 0 0 1
                0 0 0 4 0 9 0 0 0
                5 0 0 0 0 0 1 0 6
                0 0 6 0 0 0 0 0 8
                """);
        SudokuPuzzle s = new SudokuPuzzle(sc);
        Solver solver = new BacktrackSolver<>(s);
        CompoundCommand commands = new CompoundCommand();
        solver.solve(commands);
        assertTrue(s.isFinished());
        assertTrue(s.getGrid().validate());
    }

}
