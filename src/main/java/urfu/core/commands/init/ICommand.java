package urfu.core.commands.init;

public interface ICommand
{
  int getMinArgs();

  void safeArgsExecute(String[] args);

  void execute(String[] args);

  String getUsageFormat();

  String getInfo();
}
