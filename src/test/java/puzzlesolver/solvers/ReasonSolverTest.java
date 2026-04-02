package puzzlesolver.solvers;

import org.junit.jupiter.api.Test;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.exceptions.InvalidPuzzleSyntaxException;
import puzzlesolver.puzzles.kakurasu.puzzle.KakurasuPuzzle;
import puzzlesolver.puzzles.sudoku.puzzle.SudokuPuzzle;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReasonSolverTest {
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
        Solver solver = new ReasonSolver<>(s);
        CompoundCommand commands = new CompoundCommand();
        solver.solve(commands);
        assertTrue(s.isFinished());
        assertTrue(s.getGrid().validate());
    }

    @Test
    void solveKakurasu() throws InvalidPuzzleSyntaxException {
        Scanner sc = new Scanner("""
                10 10
                31 16 20 36 26 37 33 45 36 14
                48 20 23 42 29 12 45 6 31 49
                """);
        KakurasuPuzzle k = new KakurasuPuzzle(sc);
        Solver solver = new ReasonSolver<>(k);
        CompoundCommand commands = new CompoundCommand();
        solver.solve(commands);
        assertTrue(k.isFinished());
        assertTrue(k.getGrid().validate());
    }
}
