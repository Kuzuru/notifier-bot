package urfu.core.commands;

import urfu.core.CommandInitializer;
import urfu.core.ICommand;

import java.util.HashMap;

public class HelpCommand implements ICommand
{
    public void execute()
    {
        HashMap<String, ICommand> COMMANDS = CommandInitializer.getAvailableCommands();

        for (ICommand command : COMMANDS.values()) {
            System.out.println(command.getUsageFormat() + command.getInfo());
        }
    }

    public String getUsageFormat()
    {
        return "help [command]";
    }

    public String getInfo()
    {

        return """
                                
                Выводит информацию обо всех доступных командах
                При использовании опцианального ввода конкретной команды
                можно получить информацию о ней
                """;
    }
}
