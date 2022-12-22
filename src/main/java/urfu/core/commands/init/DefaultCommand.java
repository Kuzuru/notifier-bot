package urfu.core.commands.init;

import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;

import static urfu.core.Constants.ROOT_ID;

@Slf4j
public class DefaultCommand implements ICommand {
  final int minArgs;
  final boolean isRootRequired;

  public DefaultCommand(int minArgs, boolean isRootRequired) {
    this.minArgs = minArgs;
    this.isRootRequired = isRootRequired;
  }

  public final int getMinArgs() {
    return this.minArgs;
  }

  public final boolean safeArgsExecute(Integer pLevel, String[] args) {
    if (isRootRequired && pLevel != ROOT_ID) {
      return false;
    }

    String commandName = args[0];

    if (args.length - 1 < this.minArgs) {
      System.out.printf(
          "Команде %s требуется минимум %d аргумент(-а)\n", commandName, getMinArgs());
      System.out.printf("Для получения справки используйте: help %s\n\n", commandName);

      return false;
    }

    this.execute(pLevel, args);

    return true;
  }

  public void execute(Integer pLevel, String[] args) {
    System.out.printf("Вызван метод execute() класса %s\n", this.getClass().getSimpleName());
  }

  public String getUsageFormat() {
    return "exampleCommand [args]";
  }

  public String getInfo() {
    StringBuilder sb = new StringBuilder();

    sb.append("\n").append("Описание команды").append("\n");

    return sb.toString();
  }
}
