package puzzlesolver.commands;

import lombok.Value;

import java.util.List;

@Value
public class MultiCommand implements Command {
    List<Command> commands;

    @Override
    public void apply() {
        for (Command command : commands) {
            command.apply();
        }
    }

    @Override
    public void undo() {
        for (Command command : commands.reversed()) {
            command.undo();
        }
    }
}
