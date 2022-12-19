package urfu.core.commands.init;

import urfu.core.commands.*;

import java.util.LinkedHashMap;

import static urfu.core.Constants.ROOT_ID;

public class CommandInitializer {
  public static LinkedHashMap<String, ICommand> getAvailableCommands(int pLevel) {
    LinkedHashMap<String, ICommand> commands = new LinkedHashMap<>();

    commands.put("help", new HelpCommand(0, false));

    if (pLevel == ROOT_ID) {
      commands.put("clear", new ClearCommand(0, true));
      commands.put("quit", new QuitCommand(0, true));
    }

    commands.put("addTask", new AddTaskCommand(1, false));
    commands.put("notify", new NotifyCommand(3, false));
    commands.put("showTasks", new ShowTasksCommand(0, false));
    commands.put("version", new VersionCommand(0, false));

    return commands;
  }
}
