package urfu.bots.telegram;

import urfu.core.commands.init.HasSessionCommand;
import urfu.core.commands.init.ICommand;
import urfu.entity.UsersEntity;

public class UserRegisterCommand extends HasSessionCommand implements ICommand {
  public UserRegisterCommand(int minArgs, boolean isRootRequired) {
    super(minArgs, isRootRequired);
  }


  private void createUser(Integer pLevel, UsersEntity user) throws java.lang.NullPointerException {
    user.setTgId(pLevel);
//    user.setChatId(Integer.valueOf(chatID));
    session.save(user);
    session.getTransaction().commit();
  }

  @Override
  public void execute(Integer pLevel, String[] args) {
    startNewSession();
    session.getTransaction().begin();

    try {
      UsersEntity user = session.get(UsersEntity.class, pLevel);
      createUser(pLevel, user);
    } catch (Exception e) {
      UsersEntity user = new UsersEntity();
      createUser(pLevel, user);
    }

    session.close();
  }
}
