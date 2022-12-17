package urfu.core.commands;

import urfu.core.commands.init.HasSessionCommand;
import urfu.core.commands.init.ICommand;
import urfu.entity.TasksEntity;

import java.util.Arrays;

public class EditTaskCommand extends HasSessionCommand implements ICommand {
    public EditTaskCommand(int minArgs) {
        super(minArgs);
    }

    @Override
    public void execute(String[] args) {
        String[] descriptionArgs = Arrays.copyOfRange(args, 2, args.length);
        String taskDescription = String.join(" ", descriptionArgs);
        int taskID = Integer.parseInt(args[1]);

        // Проверка полученного описания
        if (taskDescription.length() > 2048) taskDescription = taskDescription.substring(0, 2047);

        // Проверка на пустую таску
        if (taskDescription.length() == 0){
            System.out.println("Введите новое описание");
            return;
        }

        // Добавление нового описания
        startNewSession();
        session.getTransaction().begin();
        try{
            TasksEntity task = new TasksEntity();
            task = (TasksEntity) session.get(TasksEntity.class, taskID);

            // Установка нового описания команды
            task.setDescription(taskDescription);

            session.save(task);
            session.getTransaction().commit();

            session.close();
        } catch (Exception e){
            System.err.println("Такой задачи не существует \r\nERRMSG: %s" + e.getMessage() + "\n");
        }
    }

    @Override
    public String getUsageFormat() {
        return "editTask [Task_ID] [description]";
    }

    @Override
    public String getInfo() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n").append("Позволяет редактировать уже добавленную задачу").append("\n");

        return sb.toString();
    }
}
