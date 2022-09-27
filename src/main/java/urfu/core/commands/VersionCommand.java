package urfu.core.commands;

import urfu.core.ICommand;

public class VersionCommand implements ICommand
{
    public void execute()
    {
        // TODO: Сделать вставку значений из .env
        System.out.println("v1.0.0\n");
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
