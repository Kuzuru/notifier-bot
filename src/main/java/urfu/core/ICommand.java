package urfu.core;

public interface ICommand
{
    void execute();

    String getUsageFormat();

    String getInfo();
}