package urfu.core.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import urfu.core.commands.init.CommandInitializer;
import urfu.core.commands.init.ICommand;

import java.util.HashMap;

import static urfu.core.Constants.ROOT_ID;

public class HelpCommandTest {
  @Test
  void isCorrectUsageFormat() {
    HashMap<String, ICommand> commands = CommandInitializer.getAvailableCommands(ROOT_ID);

    ICommand command = commands.get("help");

    if (command == null) Assertions.fail("Could not find help command");

    Assertions.assertEquals("help [command]", command.getUsageFormat());
  }

  @Test
  void isCorrectGetInfo() {
    HashMap<String, ICommand> commands = CommandInitializer.getAvailableCommands(ROOT_ID);

    ICommand command = commands.get("help");

    if (command == null) Assertions.fail("Could not find help command");

    StringBuilder sb = new StringBuilder();

    sb.append("\n")
        .append("Выводит информацию обо всех доступных командах\n")
        .append("При использовании опционального ввода конкретной команды\n")
        .append("можно получить информацию о ней")
        .append("\n");

    Assertions.assertEquals(sb.toString(), command.getInfo());
  }
}
