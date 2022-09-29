package urfu.core.commands;

import urfu.core.commands.init.ICommand;

public class QuitCommand implements ICommand
{
    public void execute(String[] args)
    {
        System.out.println("[LOG] Shutting down...");
        System.exit(0);
    }

    public String getUsageFormat()
    {
        return "quit";
    }

    public String getInfo()
    {
        return """
                
                Завершает работу бота
                """;
    }
}
