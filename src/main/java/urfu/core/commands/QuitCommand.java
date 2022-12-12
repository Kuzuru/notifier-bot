package urfu.core.commands;

import lombok.extern.slf4j.Slf4j;
import urfu.core.commands.init.DefaultCommand;
import urfu.core.commands.init.ICommand;

@Slf4j
public class QuitCommand extends DefaultCommand implements ICommand {
  public QuitCommand(int minArgs) {
    super(minArgs);
  }

  @Override
  public void execute(String[] args) {
    log.atInfo().log("Shutting down...");
    System.exit(0);
  }

  @Override
  public String getUsageFormat() {
    return "quit";
  }

  @Override
  public String getInfo() {
    StringBuilder sb = new StringBuilder();

    sb.append("\n").append("Завершает работу бота").append("\n");

    return sb.toString();
  }
}
