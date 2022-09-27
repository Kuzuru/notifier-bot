package urfu.core.commands;

import urfu.core.ICommand;

public class VersionCommand implements ICommand
{
    public void execute()
    {
        System.out.println(this.getInfo());
    }

    public String getCommand()
    {
        return "version";
    }

    public String getFormat()
    {
        return "version";
    }

    public String getInfo()
    {
        // TODO: Сделать вставку значений из .env

        return """
                
                Java version: ${java.version}
                Build info: ${java.build}
                Build date: ${java.date}
                """;
    }
}
