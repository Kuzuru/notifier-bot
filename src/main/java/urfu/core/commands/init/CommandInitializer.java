package urfu.core.commands.init;

import urfu.core.commands.*;

import java.util.LinkedHashMap;

public class CommandInitializer {
  public static LinkedHashMap<String, ICommand> getAvailableCommands() {
    LinkedHashMap<String, ICommand> commands = new LinkedHashMap<>();

    commands.put("help", new HelpCommand(0));
    commands.put("addTask", new AddTaskCommand(1));
    commands.put("version", new VersionCommand(0));
    commands.put("clear", new ClearCommand(0));
    commands.put("quit", new QuitCommand(0));

    return commands;
  }
}
