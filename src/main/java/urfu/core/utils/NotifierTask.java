package urfu.core.utils;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import urfu.bots.telegram.Run;
import urfu.core.commands.init.HasSessionCommand;
import urfu.entity.NotifiersEntity;
import urfu.entity.TasksEntity;

import static urfu.core.Constants.ROOT_ID;

public class NotifierTask implements Job {
    public void execute(JobExecutionContext context) throws JobExecutionException {

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String taskIdString = dataMap.getString("taskIDKey");
        String notifierIdString = dataMap.getString("notifierID");
        String chatID = dataMap.getString("chatID");

        int taskID;
        int notifierID;

        try {
            taskID = Integer.parseInt(taskIdString);
            notifierID = Integer.parseInt(notifierIdString);
        } catch (Exception e) {
            System.out.println("Неверный ID задачи");
            return;
        }

        HasSessionCommand hsc = new HasSessionCommand(0, false);
        hsc.startNewSession();

        String description;        //Тут будет лежать описание
        TasksEntity task = hsc.session.get(TasksEntity.class, taskID);
        description = task.getDescription();

        // Удаление напоминания
        hsc.session.getTransaction().begin();
        NotifiersEntity notify = hsc.session.get(NotifiersEntity.class, notifierID); //получили нужный объект по ID
        hsc.session.delete(notify);
        hsc.session.getTransaction().commit();

        String output = StdoutLocker.lockStdout(() -> {
            System.out.println("Напоминаю: " + description + "\n");
        });

        //System.out.println("Напоминаю: " + description + "\n"); //Вывод описания
        if (Integer.parseInt(chatID) == ROOT_ID) {
            System.out.println(output);
        } else {
            SendMessage response = new SendMessage();

            response.setText(output);
            response.setChatId(chatID);

            Run r = new Run();
            r.sendMessage(response);
        }

        hsc.session.close();
    }
}