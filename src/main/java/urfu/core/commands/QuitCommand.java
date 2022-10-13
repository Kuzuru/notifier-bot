package urfu.core.commands;

import urfu.core.commands.init.ICommand;

public class QuitCommand implements ICommand {
  @Override
  public void execute(String[] args) {
    System.out.println("[LOG] Shutting down...");
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
