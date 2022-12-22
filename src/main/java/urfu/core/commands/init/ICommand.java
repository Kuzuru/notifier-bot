package urfu.core.commands.init;

import org.quartz.SchedulerException;

public interface ICommand {
  int getMinArgs();

  boolean safeArgsExecute(Integer pLevel, String[] args);

  void execute(Integer pLevel, String[] args) throws SchedulerException;

  String getUsageFormat();

  String getInfo();
}
