package urfu.core.commands;

import urfu.core.commands.init.DefaultCommand;
import urfu.core.commands.init.ICommand;

public class QuitCommand extends DefaultCommand implements ICommand
{
    public QuitCommand(int minArgs)
    {
        super(minArgs);
    }

    @Override
    public void execute(String[] args)
    {
        System.out.println("[LOG] Shutting down...");
        System.exit(0);
    }

    @Override
    public String getUsageFormat()
    {
        return "quit";
    }

    @Override
    public String getInfo()
    {
        return """
                
                Завершает работу бота
                """;
    }
}
