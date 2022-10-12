package urfu.core.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import urfu.core.commands.init.CommandInitializer;
import urfu.core.commands.init.ICommand;

import java.util.HashMap;

public class QuitCommandTest {
    @Test
    void CheckVersion() {
        HashMap<String, ICommand> COMMANDS = CommandInitializer.getAvailableCommands();

        ICommand command = COMMANDS.get("quit");

        if (command == null)
            Assertions.fail("Could not find help command");

        // TODO: добавить тест метода Execute()
    }

    @Test
    void isCorrectUsageFormat() {
        HashMap<String, ICommand> COMMANDS = CommandInitializer.getAvailableCommands();

        ICommand command = COMMANDS.get("quit");
        if (command == null)
            Assertions.fail("Could not find help command");

        Assertions.assertEquals("quit", command.getUsageFormat());
    }

    @Test
    void isCorrectGetInfo() {
        HashMap<String, ICommand> COMMANDS = CommandInitializer.getAvailableCommands();

        ICommand command = COMMANDS.get("quit");
        if (command == null)
            Assertions.fail("Could not find help command");

        String s = """
                                
                Завершает работу бота
                """;

        Assertions.assertEquals(s, command.getInfo());
    }
}
