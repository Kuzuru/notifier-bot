package urfu.core.commands.init;

public interface ICommand {
  int getMinArgs();

  boolean safeArgsExecute(Integer pLevel, String[] args);

  void execute(Integer pLevel, String[] args);

  String getUsageFormat();

  String getInfo();
}
