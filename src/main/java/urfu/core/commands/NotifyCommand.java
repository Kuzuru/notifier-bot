package urfu.core.commands;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import urfu.core.commands.init.HasSessionCommand;
import urfu.core.commands.init.ICommand;
import urfu.core.utils.DateTimeValidator;
import urfu.core.utils.HibernateUtil;
import urfu.core.utils.NotifierTask;
import urfu.entity.NotifiersEntity;
import urfu.entity.TasksEntity;
import urfu.entity.UsersEntity;

import java.util.Calendar;
import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class NotifyCommand extends HasSessionCommand implements ICommand {
  public NotifyCommand(int minArgs, boolean isRootRequired) {
    super(minArgs, isRootRequired);
  }

  @Override
  public void execute(Integer pLevel, String[] args) {
    String taskIdString = args[1];
    String[] date = args[2].split("\\.");
    String[] time = args[3].split(":");

    Integer taskID = Integer.parseInt(taskIdString);

    // Проверка на корректность ввода даты
    int day = Integer.parseInt(date[0]);
    int month = Integer.parseInt(date[1]);
    int year = Integer.parseInt(date[2]);
    int hours = Integer.parseInt(time[0]);
    int minutes = Integer.parseInt(time[1]);

    DateTimeValidator dateTimeValidator = new DateTimeValidator();
    String dateFormatted = String.format("%d.%d.%d", day, month, year);
    String timeFormatted = String.format("%d:%d", hours, minutes);

    if (dateTimeValidator.isTimeValid(timeFormatted)
        && dateTimeValidator.isDateValid(dateFormatted)) {
      System.out.println("Неверный ввод даты. Проверьте справку по команде");
      return;
    }

    // Перенести на пакет java.time
    // + Более удобное и продвинутое API
    Calendar calendar = new GregorianCalendar(year, month - 1, day, hours, minutes);

    String tz = System.getProperty("TIMEZONE");
    Calendar cSchedStartCal = Calendar.getInstance(TimeZone.getTimeZone(tz));
    long gmtTime = cSchedStartCal.getTime().getTime(); // Зачем ты получаешь unix метку?

    Calendar calendarNow = Calendar.getInstance(TimeZone.getTimeZone(tz));
    calendarNow.setTimeInMillis(gmtTime);

    // Проверка установки даты
    if (calendar.getTimeInMillis() < calendarNow.getTimeInMillis()) {
      System.out.println("Нельзя установить дату более раннюю, чем текущее время");
      return;
    }

    Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());

    //получение даты для кварц
    Date dateForNotifier = calendar.getTime();

    // Добавление напоминания
    startNewSession();
    session.getTransaction().begin();

    int notifierID = 0;

    try {
      NotifiersEntity notify = new NotifiersEntity();

      notify.setTaskId(taskID);
      notify.setNotifyAt(timestamp);

      TasksEntity task = session.get(TasksEntity.class, taskID);

      EntityManager entityManager = HibernateUtil.getSessionFactory().createEntityManager();

      CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
      CriteriaQuery<UsersEntity> criteriaQuery =
              criteriaBuilder.createQuery(UsersEntity.class);

      Root<UsersEntity> root = criteriaQuery.from(UsersEntity.class);
      criteriaQuery.select(root);

      Predicate condition = criteriaBuilder.equal(root.get("id"), task.getOwnerId());
      criteriaQuery.where(condition);

      TypedQuery<UsersEntity> query = entityManager.createQuery(criteriaQuery);

      UsersEntity user = query.getSingleResult();

      System.out.println("USER:\n" + "a: " + user.getTgId() + "\nb: " + user.getChatId() + "\n");
//      chatID = String.valueOf(user.getChatId());

      session.save(notify);
      session.getTransaction().commit();

      notifierID = notify.getId();    //получили ID напоминания

      session.close();
    } catch (Exception e) {
      System.err.printf("Такой задачи не существует \r\nERRMSG: %s", e.getMessage());
    }

    // Навешивание Qwartz
    try {
      //новый триггер
      TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();

      triggerBuilder.withIdentity(taskIdString, "tasks")
              .startAt(dateForNotifier)
              .withSchedule(
                      SimpleScheduleBuilder
                      .simpleSchedule()
                      .withIntervalInMinutes(1)
                      .withRepeatCount(0)
              );

      // Создание мапы для передачи taskID
      JobDataMap dataMap = new JobDataMap();
      dataMap.put("taskIDKey", String.valueOf(taskID));
      dataMap.put("notifierID", String.valueOf(notifierID));
//      dataMap.put("chatID", chatID);

      //что сделать (описано в файле notifierTask)
      JobDetail job = JobBuilder.newJob(NotifierTask.class)
              .withIdentity(taskIdString, "tasks")
              .usingJobData(dataMap)
              .build();

      SchedulerFactory schedulerFactory = new StdSchedulerFactory();
      Scheduler scheduler = schedulerFactory.getScheduler();
      // Постоянная проверка времени
      scheduler.start();
      // Выполнение задания
      scheduler.scheduleJob(job, triggerBuilder.build());

    } catch (SchedulerException e){
      System.out.println("ERROR: Нельзя навесить более одного напоминания" + e.getMessage());
    }

  }

  @Override
  public String getUsageFormat() {
    return "notify [TaskID] [DD.MM.YYYY] [HH:MM]";
  }

  @Override
  public String getInfo() {
    StringBuilder sb = new StringBuilder();

    sb.append("\n")
        .append("Позволяет установить время напоминания для задачи\n")
        .append("Ввод даты в формате: DD.MM.YYYY\n")
        .append("Ввод времени в формате: HH:mm")
        .append("\n");

    return sb.toString();
  }
}
