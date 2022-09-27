package urfu.core;

public interface ICommand
{
    void execute(String[] args);

    String getUsageFormat();

    String getInfo();
}