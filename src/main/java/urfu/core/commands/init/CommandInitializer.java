package urfu.core.commands.init;

import urfu.core.commands.HelpCommand;
import urfu.core.commands.QuitCommand;
import urfu.core.commands.VersionCommand;

import java.util.HashMap;

public class CommandInitializer
{
    public static HashMap<String, ICommand> getAvailableCommands()
    {
        HashMap<String, ICommand> COMMANDS = new HashMap<>();

        COMMANDS.put("help", new HelpCommand(0));
        COMMANDS.put("version", new VersionCommand(0));
        COMMANDS.put("quit", new QuitCommand(0));

        return COMMANDS;
    }
}
