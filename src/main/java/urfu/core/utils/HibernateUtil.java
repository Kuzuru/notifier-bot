package urfu.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;

@Slf4j
public class HibernateUtil {
  private static final SessionFactory sessionFactory = initSessionFactory();

  private static SessionFactory initSessionFactory() {
    try {
      return new Configuration()
          .configure(new File("src/main/resources/hibernate.cfg.xml"))
          .buildSessionFactory();
    } catch (Throwable e) {
      log.atError().log("ERR: Ошибка инициализации SessionFactory");
      log.atError().log("ERRMSG: {}", e.getMessage());
      throw new ExceptionInInitializerError(e);
    }
  }

  public static SessionFactory getSessionFactory() {
    if (sessionFactory == null) {
      initSessionFactory();
    }

    return sessionFactory;
  }

  public static void close() {
    log.atDebug().log("Завершение соединения HibernateUtil с Hibernate...");
    getSessionFactory().close();
  }
}
