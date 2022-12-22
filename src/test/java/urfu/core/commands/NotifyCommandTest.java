package urfu.core.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import urfu.core.commands.init.CommandInitializer;
import urfu.core.commands.init.ICommand;

import java.util.HashMap;

public class NotifyCommandTest {
  @Test
  void isCorrectUsageFormat() {
    HashMap<String, ICommand> commands = CommandInitializer.getAvailableCommands();

    ICommand command = commands.get("notify");

    if (command == null) Assertions.fail("Could not find help command");

    Assertions.assertEquals(
        "notify [ID_Task] [day.mounth.year] [hours:minutes]", command.getUsageFormat());
  }

  @Test
  void isCorrectGetInfo() {
    HashMap<String, ICommand> commands = CommandInitializer.getAvailableCommands();

    ICommand command = commands.get("notify");

    if (command == null) Assertions.fail("Could not find help command");

    StringBuilder sb = new StringBuilder();

    sb.append("\n")
        .append("Позволяет установить время напоминания для задачи\n")
        .append("Ввод даты в формате: DD.MM.YYYY\n")
        .append("Ввод времени в формате: HH:mm")
        .append("\n");

    Assertions.assertEquals(sb.toString(), command.getInfo());
  }
}
