package urfu.core.commands;

import urfu.core.commands.init.CommandInitializer;
import urfu.core.commands.init.DefaultCommand;
import urfu.core.commands.init.ICommand;

import java.util.HashMap;

public class HelpCommand extends DefaultCommand implements ICommand {
  public HelpCommand(int minArgs) {
    super(minArgs);
  }

  @Override
  public void execute(String[] args) {
    HashMap<String, ICommand> commands = CommandInitializer.getAvailableCommands();

    if (args.length != 2 || !commands.containsKey(args[1])) {
      for (ICommand command : commands.values()) {
        System.out.println("> " + command.getUsageFormat() + command.getInfo());
      }

      return;
    }

    ICommand selectedCommand = commands.get(args[1]);

    System.out.println("> " + selectedCommand.getUsageFormat() + selectedCommand.getInfo());
  }

  @Override
  public String getUsageFormat() {
    return "help [command]";
  }

  @Override
  public String getInfo() {
    StringBuilder sb = new StringBuilder();

    sb.append("\n")
        .append("Выводит информацию обо всех доступных командах\n")
        .append("При использовании опционального ввода конкретной команды\n")
        .append("можно получить информацию о ней")
        .append("\n");

    return sb.toString();
  }
}
