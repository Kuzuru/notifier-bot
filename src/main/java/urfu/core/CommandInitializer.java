package urfu.core;

import urfu.core.commands.HelpCommand;
import urfu.core.commands.VersionCommand;

import java.util.ArrayList;
import java.util.HashMap;

public class CommandInitializer
{
    public static HashMap<String, ICommand> getAvailableCommands() {
        HashMap<String, ICommand> COMMANDS = new HashMap<>();

        COMMANDS.put("help", new HelpCommand());
        COMMANDS.put("version", new VersionCommand());

        return COMMANDS;
    }
}
