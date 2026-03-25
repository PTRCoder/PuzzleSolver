package puzzlesolver.commands;

/**
 * A command performed on a puzzle. Any implementation of this must change the puzzle in a meaningful way. 
 * Furthermore, any action must be reversible.
 */
public interface Command {
    void apply();

    void undo();
}
