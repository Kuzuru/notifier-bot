package urfu.core;

public interface ICommand
{
    void execute();

    String getCommand();
    String getFormat();
    String getInfo();
}