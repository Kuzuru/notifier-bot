package urfu.core.commands.init;

import org.hibernate.Session;
import urfu.core.utils.HibernateUtil;

public class HasSessionCommand extends DefaultCommand {
    public Session session;

    public void startNewSession() {
        if (session == null || !session.isOpen()) {
            this.session = HibernateUtil.getSessionFactory().openSession();
        }else{
            this.session.getSession();
        }
    }

    public HasSessionCommand(int minArgs) {
        super(minArgs);
        startNewSession();
    }
}
