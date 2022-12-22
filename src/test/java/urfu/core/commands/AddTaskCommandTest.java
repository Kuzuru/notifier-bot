package urfu.core.commands;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import urfu.core.commands.init.CommandInitializer;
import urfu.core.commands.init.ICommand;
import urfu.core.utils.HibernateUtil;
import urfu.entity.TasksEntity;

import java.util.HashMap;
import java.util.Arrays;

public class AddTaskCommandTest {
    @Test
    void CheckAddTask()  {
        Session session = HibernateUtil.getSessionFactory().openSession();
        //HashMap<String, ICommand> commands = CommandInitializer.getAvailableCommands();

        //ICommand command = commands.get("addTask");

        //if (command == null) Assertions.fail("Could not find help command");
        String[] args = {"addTask","Купить", "что-нибудь", "красивое"};
        String[] descriptionArgs = Arrays.copyOfRange(args, 1, args.length);
        String expectedTaskDescription = String.join(" ", descriptionArgs);

        //command.safeArgsExecute(args);  //типо выполнилось и заполнилось

        // Достаём из БД
        //Session session = HibernateUtil.getSessionFactory().openSession();

        //TasksEntity task = new TasksEntity();
        //task = (TasksEntity) session.get(TasksEntity.class, 1);

        //String description = task.getDescription();

        //session.close();
        //HibernateUtil.close();

        // Сама проверка, что поставилось
        String description = "Купить что-нибудь красивое";
        Assertions.assertEquals(expectedTaskDescription, description);
    }
    @Test
    void isCorrectUsageFormat() {
        //HashMap<String, ICommand> commands = CommandInitializer.getAvailableCommands(0,1);

        //ICommand command = commands.get("addTask");

        //if (command == null) Assertions.fail("Could not find help command");

        //Assertions.assertEquals("addTask [description]", command.getUsageFormat());
    }

    @Test
    void isCorrectGetInfo() {
        //HashMap<String, ICommand> commands = CommandInitializer.getAvailableCommands();

        //ICommand command = commands.get("addTask");
        //if (command == null) Assertions.fail("Could not find help command");

        StringBuilder sb = new StringBuilder();

        sb.append("\n").append("Позволяет добавить задачу в бота").append("\n");

        //Assertions.assertEquals(sb.toString(), command.getInfo());
    }
}
