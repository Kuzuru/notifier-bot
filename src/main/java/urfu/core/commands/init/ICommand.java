package urfu.core.commands.init;

public interface ICommand {
  int getMinArgs();

  boolean safeArgsExecute(String[] args);

  void execute(String[] args);

  String getUsageFormat();

  String getInfo();
}
