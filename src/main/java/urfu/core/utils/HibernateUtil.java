package urfu.core.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;

public class HibernateUtil
{
    private static final SessionFactory sessionFactory = initSessionFactory();

    private static SessionFactory initSessionFactory()
    {
        try {
            return new Configuration().configure(new File("src/main/resources/hibernate.cfg.xml")).buildSessionFactory();
        } catch (Throwable e) {
            System.err.printf("ERR: Ошибка инициализации SessionFactory \r\nERRMSG: %s", e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory()
    {
        if (sessionFactory == null) {
            initSessionFactory();
        }

        return sessionFactory;
    }

    public static void close()
    {
        getSessionFactory().close();
    }
}
