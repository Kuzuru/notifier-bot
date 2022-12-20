package urfu.core.commands;

import urfu.core.commands.init.HasSessionCommand;
import urfu.core.commands.init.ICommand;
import urfu.core.utils.DateTimeValidator;
import urfu.entity.NotifiersEntity;

import java.util.Calendar;
import java.sql.Timestamp;
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

    Calendar cSchedStartCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    long gmtTime = cSchedStartCal.getTime().getTime();

    long timezoneAlteredTime = gmtTime + TimeZone.getTimeZone("Asia/Yekaterinburg").getRawOffset();
    Calendar calendarNow = Calendar.getInstance(TimeZone.getTimeZone("Asia/Yekaterinburg"));
    calendarNow.setTimeInMillis(timezoneAlteredTime);

    // Проверка установки даты
    if (calendar.getTimeInMillis() < calendarNow.getTimeInMillis()) {
      System.out.println("Нельзя установить дату более раннюю, чем текущее время");
      return;
    }

    Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());

    // Добавление напоминания
    startNewSession();
    session.getTransaction().begin();

    try {
      NotifiersEntity notify = new NotifiersEntity();

      notify.setTaskId(taskID);
      notify.setNotifyAt(timestamp);

      session.save(notify);
      session.getTransaction().commit();

      session.close();
    } catch (Exception e) {
      System.err.printf("Такой задачи не существует \r\nERRMSG: %s", e.getMessage());
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
