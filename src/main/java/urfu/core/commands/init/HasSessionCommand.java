package urfu.core.commands.init;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import urfu.core.utils.HibernateUtil;

@Slf4j
public class HasSessionCommand extends DefaultCommand {
  public Session session;

  public HasSessionCommand(int minArgs, boolean isRootRequired) {
    super(minArgs, isRootRequired);
    startNewSession();
  }

  public void startNewSession() {
    log.atDebug().log("Команда {} запросила запуск новой сессии", this.getClass().getSimpleName());

    if (session == null || !session.isOpen()) {
      this.session = HibernateUtil.getSessionFactory().openSession();
    } else {
      this.session.getSession();
    }
  }
}
