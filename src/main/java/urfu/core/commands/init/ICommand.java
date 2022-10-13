package urfu.core.commands.init;

public interface ICommand {
  void execute(String[] args);

  String getUsageFormat();

  String getInfo();
}
