package br.com.alura.screenmatch.menu.command;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CreatListCommand {

  private final List<Command> commands;

  public CreatListCommand(List<Command> commands) {
    this.commands = commands;
  }

  public Map<String, Command> create() {
    return commands.stream().collect(Collectors.toMap(Command::getOperationId, c -> c));
  }
}
