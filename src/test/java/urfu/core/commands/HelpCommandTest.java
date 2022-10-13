package urfu.core.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import urfu.core.commands.init.CommandInitializer;
import urfu.core.commands.init.ICommand;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

public class HelpCommandTest {
  @Test
  void shouldNotShowHelpIfMoreThan2Args() {
    HashMap<String, ICommand> COMMANDS = CommandInitializer.getAvailableCommands();

    ICommand command = COMMANDS.get("help");

    if (command == null) Assertions.fail("Could not find help command");

    String[] args = new String[3];

    args[0] = "help";
    args[1] = "help";
    args[2] = "abra";

    ByteArrayOutputStream CatchOut = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(CatchOut);
    PrintStream old = System.out;
    System.setOut(ps);

    command.execute(args);

    System.out.flush();
    System.setOut(old);

    StringBuilder expectedOutput = new StringBuilder();

    for (ICommand commandExpected : COMMANDS.values()) {
      expectedOutput
          .append("> ")
          .append(commandExpected.getUsageFormat())
          .append(commandExpected.getInfo())
          .append("\r\n");
    }

    Assertions.assertEquals(expectedOutput.toString(), CatchOut.toString());
  }

  @Test
  void isCorrectUsageFormat() {
    HashMap<String, ICommand> COMMANDS = CommandInitializer.getAvailableCommands();

    ICommand command = COMMANDS.get("help");

    if (command == null) Assertions.fail("Could not find help command");

    Assertions.assertEquals("help [command]", command.getUsageFormat());
  }

  @Test
  void isCorrectGetInfo() {
    HashMap<String, ICommand> COMMANDS = CommandInitializer.getAvailableCommands();

    ICommand command = COMMANDS.get("help");

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
