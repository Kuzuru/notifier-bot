package urfu.core.commands;

import urfu.core.commands.init.ICommand;

public class VersionCommand implements ICommand
{
    public void execute(String[] args)
    {
        // TODO: Сделать вставку значений из .env
        System.out.println("v1.0-SNAPSHOT\n");
    }

    public String getUsageFormat()
    {
        return "version";
    }

    public String getInfo()
    {
        return """
                                
                Выводит информацию о текущей версии Notifier Bot
                """;
    }
}
