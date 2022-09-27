package urfu.core.commands;

import urfu.core.ICommand;

public class HelpCommand implements ICommand
{
    public void execute()
    {
        System.out.println(this.getInfo());
    }

    public String getCommand()
    {
        return "help";
    }

    public String getFormat()
    {

        return "help [command]";
    }

    public String getInfo()
    {

        return """
                
                Выводит информацию обо всех доступных командах
                При использовании опцианального ввода конкретной команды
                можно получить информацию о ней
                """;
    }
}
