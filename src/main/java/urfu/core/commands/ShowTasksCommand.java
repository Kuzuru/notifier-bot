package urfu.core.commands;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import urfu.core.commands.init.HasSessionCommand;
import urfu.core.commands.init.ICommand;
import urfu.core.utils.HibernateUtil;
import urfu.entity.TasksEntity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

@Slf4j
public class ShowTasksCommand extends HasSessionCommand implements ICommand {
  public ShowTasksCommand(int minArgs, boolean isRootRequired) {
    super(minArgs, isRootRequired, "0");
  }

  @Override
  public void execute(Integer pLevel, String[] args, String chatID) {
    startNewSession();

    EntityManager entityManager = HibernateUtil.getSessionFactory().createEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<TasksEntity> criteriaQuery = criteriaBuilder.createQuery(TasksEntity.class);

    Root<TasksEntity> root = criteriaQuery.from(TasksEntity.class);
    criteriaQuery.select(root);

    Predicate condition = criteriaBuilder.equal(root.get("ownerId"), pLevel);
    criteriaQuery.where(condition);

    TypedQuery<TasksEntity> query = entityManager.createQuery(criteriaQuery);
    List<TasksEntity> entities = query.getResultList();

    entityManager.close();
    session.close();

    if (entities.isEmpty()) {
      System.out.println("У вас ещё нет задач :(");
    }

    for (TasksEntity entity : entities) {
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy // HH:mm");
      dateFormat.setTimeZone(TimeZone.getTimeZone(System.getProperty("TIMEZONE")));
      String formattedTimestamp = dateFormat.format(entity.getUpdatedAt());

      System.out.println("ID: " + entity.getId());
      System.out.println("Описание: " + entity.getDescription());
      System.out.println("Последний раз изменена: " + formattedTimestamp + "\n");
    }
  }

  @Override
  public String getUsageFormat() {
    return "showTasks";
  }

  @Override
  public String getInfo() {
    StringBuilder sb = new StringBuilder();

    sb.append("\n").append("Показывает текущие задачи").append("\n");

    return sb.toString();
  }
}
