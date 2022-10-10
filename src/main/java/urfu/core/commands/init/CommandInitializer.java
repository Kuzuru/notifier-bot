package urfu.core.commands.init;

import urfu.core.commands.ClearCommand;
import urfu.core.commands.HelpCommand;
import urfu.core.commands.QuitCommand;
import urfu.core.commands.VersionCommand;

import java.util.HashMap;

public class CommandInitializer
{
    public static HashMap<String, ICommand> getAvailableCommands()
    {
        HashMap<String, ICommand> COMMANDS = new HashMap<>();

        COMMANDS.put("help", new HelpCommand());
        COMMANDS.put("version", new VersionCommand());
        COMMANDS.put("clear", new ClearCommand());
        COMMANDS.put("quit", new QuitCommand());

        return COMMANDS;
    }
}
