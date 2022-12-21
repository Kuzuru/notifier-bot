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
import urfu.entity.NotifiersEntity;
import urfu.entity.TasksEntity;

import java.util.List;
import java.util.Objects;

@Slf4j
public class DeleteTaskCommand extends HasSessionCommand implements ICommand {
  public DeleteTaskCommand(int minArgs, boolean isRootRequired) {
    super(minArgs, isRootRequired);
  }

  @Override
  public void execute(Integer pLevel, String[] args) {
    int taskID;

    try {
      taskID = Integer.parseInt(args[1]);
    } catch (Exception e) {
      System.out.println("Неверный ID задачи");
      return;
    }

    startNewSession();
    session.getTransaction().begin();

    try {
      TasksEntity task = session.get(TasksEntity.class, taskID);

      if (Objects.equals(task.getOwnerId(), pLevel)) {
        EntityManager entityManager = HibernateUtil.getSessionFactory().createEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<NotifiersEntity> criteriaQuery = criteriaBuilder.createQuery(NotifiersEntity.class);

        Root<NotifiersEntity> root = criteriaQuery.from(NotifiersEntity.class);
        criteriaQuery.select(root);

        Predicate condition = criteriaBuilder.equal(root.get("taskId"), task.getId());
        criteriaQuery.where(condition);

        TypedQuery<NotifiersEntity> query = entityManager.createQuery(criteriaQuery);
        List<NotifiersEntity> entities = query.getResultList();

        entityManager.close();

        if (entities.isEmpty()) {
          System.out.println("У данной задачи нет напоминаний\n");
        } else {
          System.out.println("Удаляются напоминания о задаче...\n");

          for (NotifiersEntity entity : entities) {
            session.delete(entity);
          }
        }

        session.delete(task);

        System.out.printf("Задача (#%d) удалена", task.getId());
      } else {
        System.out.println("Данная задача не пренадлежит вам");
      }

      session.getTransaction().commit();
    } catch (Exception e) {
      System.err.println("Такой задачи не существует \r\nERRMSG: %s" + e.getMessage() + "\n");
    }

    session.close();
  }

  @Override
  public String getUsageFormat() {
    return "deleteTask [TaskID]";
  }

  @Override
  public String getInfo() {
    StringBuilder sb = new StringBuilder();

    sb.append("\n").append("Удаляет задачу пользователя").append("\n");

    return sb.toString();
  }
}
