package urfu.core.commands;

import lombok.extern.slf4j.Slf4j;
import urfu.core.commands.init.HasSessionCommand;
import urfu.core.commands.init.ICommand;
import urfu.entity.TasksEntity;

import java.util.Arrays;

import static urfu.core.Constants.ROOT_ID;

@Slf4j
public class AddTaskCommand extends HasSessionCommand implements ICommand {
  public AddTaskCommand(int minArgs) {
    super(minArgs);
  }

  @Override
  public void execute(String[] args) {
    String[] descriptionArgs = Arrays.copyOfRange(args, 1, args.length);
    String taskDescription = String.join(" ", descriptionArgs);

    if (taskDescription.length() > 2048) taskDescription = taskDescription.substring(0, 2047);

    startNewSession();
    session.getTransaction().begin();

    TasksEntity task = new TasksEntity();

    task.setOwnerId(ROOT_ID);
    task.setDescription(taskDescription);

    session.save(task);
    session.getTransaction().commit();

    log.atDebug().log("Создана задача #{} от ID:{}, с описанием: {}", task.getId(), task.getOwnerId(), task.getDescription());
    log.atDebug().log("Статус транзакции: {}", session.getTransaction().getStatus());

    session.close();
  }

  @Override
  public String getUsageFormat() {
    return "addTask [description]";
  }

  @Override
  public String getInfo() {
    StringBuilder sb = new StringBuilder();

    sb.append("\n").append("Позволяет добавить задачу в бота").append("\n");

    return sb.toString();
  }
}
