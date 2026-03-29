package puzzlesolver.commands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@RequiredArgsConstructor
@ToString
public class MultiCommand implements Command {
    private final List<Command> commands;
    @Getter
    private boolean applied;

    @Override
    public void apply() {
        applied = true;
        for (Command command : commands) {
            command.apply();
        }
    }

    @Override
    public void undo() {
        applied = false;
        for (Command command : commands.reversed()) {
            command.undo();
        }
    }
}
