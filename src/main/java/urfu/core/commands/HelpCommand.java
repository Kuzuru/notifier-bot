package urfu.core.commands;

import urfu.core.CommandInitializer;
import urfu.core.ICommand;

import java.util.HashMap;

public class HelpCommand implements ICommand
{
    public void execute(String[] args)
    {
        HashMap<String, ICommand> COMMANDS = CommandInitializer.getAvailableCommands();

        if (args.length != 2) {
            for (ICommand command : COMMANDS.values()) {
                System.out.println("> " + command.getUsageFormat() + command.getInfo());
            }

            return;
        }

        ICommand selectedCommand = COMMANDS.get(args[1]);

        System.out.println("> " + selectedCommand.getUsageFormat() + selectedCommand.getInfo());
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
