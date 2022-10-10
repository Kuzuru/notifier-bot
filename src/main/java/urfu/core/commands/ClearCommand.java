package urfu.core.commands;

import urfu.core.commands.init.ICommand;

public class ClearCommand implements ICommand
{
    @Override
    public void execute(String[] args)
    {
        // 033    -> ESC
        // [H     -> Передвижение курсора
        // 033[2J -> В самое начало, затирая всё на пути
        System.out.print("\033[H\033[2J");

        System.out.flush();
    }

    @Override
    public String getUsageFormat()
    {
        return "clear";
    }

    @Override
    public String getInfo()
    {
        return """
                                
                Очищает консоль
                """;
    }
}
