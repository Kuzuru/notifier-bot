package urfu.core.commands;

import urfu.core.commands.init.ICommand;

public class VersionCommand implements ICommand
{
    @Override
    public void execute(String[] args)
    {
        System.out.println("Notifier Bot v0.7.3\n");
    }

    @Override
    public String getUsageFormat()
    {
        return "version";
    }

    @Override
    public String getInfo()
    {
        return """
                                
                Выводит информацию о текущей версии Notifier Bot
                """;
    }
}
