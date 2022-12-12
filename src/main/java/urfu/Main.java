package urfu;

import lombok.extern.slf4j.Slf4j;
import urfu.core.commands.init.CommandInitializer;
import urfu.core.commands.init.ICommand;
import urfu.core.config.ConfigInitializer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

@Slf4j
public class Main {
  @SuppressWarnings("InfiniteLoopStatement")
  public static void main(String[] args) {
    log.atInfo().log("Бот запускается...");

    log.atInfo().log("Загружаются переменные окружения...");
    ConfigInitializer.load();

    log.atInfo().log("Загружается список доступных команд...");
    HashMap<String, ICommand> commands = CommandInitializer.getAvailableCommands();

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

        if (command != null) command.safeArgsExecute(userInputArgs);
      }
    } catch (Exception e) {
      log.atError().log("Произошла ошибка в ожидании ввода команды :(");
      log.atError().log("ERRMSG: " + e.getMessage());
    }
  }
}
