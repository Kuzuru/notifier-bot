package urfu;

import ch.qos.logback.classic.Level;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import urfu.bots.telegram.Run;
import urfu.core.commands.init.CommandInitializer;
import urfu.core.commands.init.ICommand;
import urfu.core.config.ConfigInitializer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Objects;

@Slf4j
public class Main {
  private static final Logger logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

  @SuppressWarnings("InfiniteLoopStatement")
  public static void main(String[] args) throws TelegramApiException
  {
    if (Objects.equals(System.getProperty("APP_DEBUG"), "false")) {
      ((ch.qos.logback.classic.Logger) logger).setLevel(Level.ERROR);
    }

    log.atInfo().log("Бот запускается...");

    log.atInfo().log("Загружаются переменные окружения...");
    ConfigInitializer.load();

    log.atInfo().log("Загружается список доступных команд...");
    HashMap<String, ICommand> commands = CommandInitializer.getAvailableCommands();

    /////////////////
    TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

    try {
      telegramBotsApi.registerBot(new Run());
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }

    System.out.println("[BOT] Started and ready to get messages!");
    /////////////////

    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    log.atInfo().log("Запуск успешен! Happy hacking :)");
    try {
      while (true) {
        System.out.print("notifier@bot: ");

        String userInput = input.readLine();
        if (userInput == null) continue;

        // Обработка лишних пробелов во входной строке
        userInput = userInput.trim().replaceAll(" +", " ");
        String[] userInputArgs = userInput.split(" ");

        ICommand command = commands.get(userInputArgs[0]);

        if (command != null)
          command.safeArgsExecute(userInputArgs);
      }
    } catch (Exception e) {
      log.atError().log("Произошла ошибка в ожидании ввода команды :(");
      log.atError().log("ERRMSG: " + e.getMessage());
    }
  }
}
