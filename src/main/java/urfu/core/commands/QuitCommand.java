package urfu.core.commands;

import lombok.extern.slf4j.Slf4j;
import urfu.core.commands.init.DefaultCommand;
import urfu.core.commands.init.ICommand;
import urfu.core.utils.HibernateUtil;

@Slf4j
public class QuitCommand extends DefaultCommand implements ICommand {
  public QuitCommand(int minArgs, boolean isRootRequired) {
    super(minArgs, isRootRequired);
  }

  @Override
  public void execute(Integer pLevel, String[] args) {
    log.atInfo().log("Выключение бота...");
    HibernateUtil.close();
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
