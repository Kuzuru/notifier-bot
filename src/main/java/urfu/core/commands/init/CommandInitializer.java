package urfu.core.commands.init;

import urfu.core.commands.ClearCommand;
import urfu.core.commands.HelpCommand;
import urfu.core.commands.QuitCommand;
import urfu.core.commands.VersionCommand;

import java.util.LinkedHashMap;

public class CommandInitializer
{
    public static LinkedHashMap<String, ICommand> getAvailableCommands()
    {
        LinkedHashMap<String, ICommand> commands = new LinkedHashMap<>();

        commands.put("help", new HelpCommand());
        commands.put("version", new VersionCommand());
        commands.put("clear", new ClearCommand());
        commands.put("quit", new QuitCommand());

        return commands;
    }
}
