package urfu.core.commands;

import urfu.core.commands.init.DefaultCommand;
import urfu.core.commands.init.ICommand;

public class VersionCommand extends DefaultCommand implements ICommand {
  public VersionCommand(int minArgs, boolean isRootRequired) {
    super(minArgs, isRootRequired, "0");
  }

  @Override
  public void execute(Integer pLevel, String[] args) {
    System.out.println(System.getProperty("VERSION") + "\n");
  }

  @Override
  public String getUsageFormat() {
    return "version";
  }

  @Override
  public String getInfo() {
    StringBuilder sb = new StringBuilder();

    sb.append("\n").append("Выводит информацию о текущей версии Notifier Bot").append("\n");

    return sb.toString();
  }
}
