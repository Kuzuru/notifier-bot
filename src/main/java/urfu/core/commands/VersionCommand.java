package urfu.core.commands;

import urfu.core.commands.init.DefaultCommand;
import urfu.core.commands.init.ICommand;

public class VersionCommand extends DefaultCommand implements ICommand
{
    public VersionCommand(int minArgs)
    {
        super(minArgs);
    }

    @Override
    public void execute(String[] args)
    {
        // TODO: Сделать вставку значений из .env
        System.out.println("v1.0-SNAPSHOT\n");
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
